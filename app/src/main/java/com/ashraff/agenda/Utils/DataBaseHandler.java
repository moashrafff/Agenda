package com.ashraff.agenda.Utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DataBaseHandler extends SQLiteAssetHelper {

    public static final int VERSION = 1;
    public static final String DB_NAME = "todoDataBase.db";
    public static final String TB_NAME = "todo";
    public static final String ID = "id";
    public static final String TASK = "task";
    public static final String STATUS = "status";


    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, VERSION);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
