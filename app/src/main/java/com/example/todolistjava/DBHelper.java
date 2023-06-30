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
    // Database information
    public static final String TASK_TODO_DB = "TaskTodo.db";
    public static final int DATABASE_VERSION  = 1;
    // Table name and column names
    public static final String TABLE_TASK = "TASK_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TODO_TASK = "TODO_TASK";

    //Create table query
    public static final String CREATE_TABLE_STATEMENT = "CREATE TABLE " + TABLE_TASK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COLUMN_TODO_TASK + " TEXT)";



    public DBHelper(@Nullable Context context) {
        super(context, TASK_TODO_DB, null, DATABASE_VERSION );
    }


    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }
    // CRUD operations

    // Create operation
    public boolean addOne(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Content values stores data in pairs
        ContentValues cv = new ContentValues();
//               "name"    ,   value
        cv.put(COLUMN_TODO_TASK, taskModel.getTodoTask());

        long insert = db.insert(TABLE_TASK, null, cv);
        if (insert == -1 ) {
            return false;
        } else {
            return true;
        }
    }

    // Read operation
    public  List<TaskModel> getAllTask() {
        List<TaskModel> dataList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TABLE_TASK + " ORDER BY " + COLUMN_ID + " DESC ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
//      Loop through the cursor (result set ) and create new task. Put them in the return list
            int columnIndexId = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int columnIndexTodoTask = cursor.getColumnIndexOrThrow(COLUMN_TODO_TASK);
            do {
//                int taskID = cursor.getInt(0);
//                String taskTodo = cursor.getString(1 );
                int taskID = cursor.getInt(columnIndexId);
                String taskTodo = cursor.getString(columnIndexTodoTask);


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


    // Delete operation
    public boolean deleteOne(TaskModel taskModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = {String.valueOf(taskModel.getId())};
        int rowsDeleted = db.delete(TABLE_TASK, COLUMN_ID + " = ?", whereArgs);
        return rowsDeleted > 0;
    }
}
