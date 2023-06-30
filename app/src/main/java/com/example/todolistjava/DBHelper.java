package com.example.todolistjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TODO_TASK = " TODO_TASK TEXT ";
    public static final String COLUMN_ID = "ID INTEGER";

    public DBHelper(@Nullable Context context) {
        super(context, "TaskTodo.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String createTableStatement = "CREATE TABLE " + TASK_TABLE + " (" + COLUMN_ID + " PRIMARY KEY AUTOINCREMENT," + COLUMN_TODO_TASK + ")";
        String createTableStatement = "CREATE TABLE TASK_TABLE(ID INTEGER PRIMARY KEY AUTOINCREMENT , TODO_TASK TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Content values stores data in pairs
        ContentValues cv = new ContentValues();
//               "name"    ,   value
        cv.put("TODO_TASK", taskModel.getTodoTask());

        long insert = db.insert(TASK_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public  List<TaskModel> getAllTask() {
        List<TaskModel> dataList = new ArrayList<>();

        String queryString = "SELECT * FROM TASK_TABLE ORDER BY ID DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
//      Loop through the cursor (result set ) and create new task. Put them in the return list
            do {
                int taskID = cursor.getInt(0);
                String taskTodo = cursor.getString(1);

                TaskModel newTask = new TaskModel(taskID, taskTodo);
                dataList.add(newTask);
            } while (cursor.moveToNext());
        } else {
//      Failure. Don't add anything to the list.
        }
//        Close cursor and db when done
        cursor.close();
        db.close();
        return dataList;
    }

    public boolean deleteOne(TaskModel taskModel){
        SQLiteDatabase db = this.getWritableDatabase();
//        Content values stores data in pairs
        String queryString = "DELETE FROM TASK_TABLE WHERE ID = "+taskModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }
}
