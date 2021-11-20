package com.ashraff.agenda.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashraff.agenda.Model.TaskModel;

import java.util.ArrayList;

public class DataBaseAccess {
    private SQLiteDatabase myDataBase;
    private SQLiteOpenHelper helper;
    private static DataBaseAccess instance;

    private DataBaseAccess (Context context){
        this.helper = new DataBaseHandler(context);
    }

    public static DataBaseAccess getInstance(Context context){

        if (instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void open (){
        this.myDataBase = this.helper.getWritableDatabase();
    }

    public void close () {
        if (this.myDataBase!=null)
            this.myDataBase.close();
    }

    public boolean insertTask(TaskModel taskModel){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.TASK,taskModel.getTaskText());
        values.put(DataBaseHandler.STATUS,0);

        long result = myDataBase.insert(DataBaseHandler.TB_NAME,null,values);
        return result != -1;
    }

    public boolean updateTaskStatus (int id, int status){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.STATUS,status);
        String args [] = {String.valueOf(id)};
        long result = myDataBase.update(DataBaseHandler.TB_NAME,values ," id=?",args);
        return result > 0;
    }

    public boolean updateTaskText (TaskModel taskModel){
        ContentValues values = new ContentValues();
        values.put(DataBaseHandler.TASK,taskModel.getTaskText());
        String args [] = {String.valueOf(taskModel.getId())};
        long result = myDataBase.update(DataBaseHandler.TB_NAME,values ," id=?",args);
        return result > 0;
    }

    public boolean deleteTask (TaskModel taskModel) {
        String args [] = {String.valueOf(taskModel.getId())};
        int result = myDataBase.delete(DataBaseHandler.TB_NAME, " id=?",args);
        return result >0;
    }


    public ArrayList<TaskModel> getAllTasks () {
        ArrayList<TaskModel> Tasks = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM " + DataBaseHandler.TB_NAME,null);


        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHandler.ID));
                int status = cursor.getInt(cursor.getColumnIndex(DataBaseHandler.STATUS));
                String task = cursor.getString(cursor.getColumnIndex(DataBaseHandler.TASK));
                TaskModel taskModel = new TaskModel(id,status,task);
                Tasks.add(taskModel);
            }
            while (cursor.moveToNext());

            cursor.close();
        }
        return Tasks;
    }


}
