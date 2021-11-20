package com.ashraff.agenda.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashraff.agenda.AddNewTask;
import com.ashraff.agenda.MainActivity;
import com.ashraff.agenda.Model.TaskModel;
import com.ashraff.agenda.R;
import com.ashraff.agenda.Utils.DataBaseAccess;
import com.ashraff.agenda.databinding.CustomToDoBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TaskHolder> {

    private ArrayList<TaskModel> todoList;
    private DataBaseAccess db;
    private MainActivity activity;


    public RecyclerViewAdapter(ArrayList<TaskModel> tasks,MainActivity activity) {
        todoList = tasks;
        this.activity = activity;

    }

    public Context getContext (){
        return activity;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_to_do, parent, false);
        TaskHolder taskHolder = new TaskHolder(itemView);
        return taskHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
    db = DataBaseAccess.getInstance(getContext());
    db.open();
    TaskModel task = todoList.get(position);
    holder.checkBox.setText(task.getTaskText());
    holder.checkBox.setChecked((toBoolean(task.getStatus())));
    holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                db.updateTaskStatus(task.getId(),1);
            }else {
                db.updateTaskStatus(task.getId(),0);
            }
        }
    });
    }
    public void setTasks(ArrayList<TaskModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }
    public boolean toBoolean (int status) {

        return status != 0;
    }

    public void deleteItem (int position) {
        TaskModel item = todoList.get(position);
        db.deleteTask(item);
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem (int position){
        TaskModel task =  todoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",task.getId());
        bundle.putString("task",task.getTaskText());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewTask.TAG);
    }


    public static class TaskHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);

        }
    }

}
