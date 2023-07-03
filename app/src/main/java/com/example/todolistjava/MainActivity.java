package com.example.todolistjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//public class MainActivity extends AppCompatActivity implements MyAdapter.RefreshListener {
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonAdd;

    private EditText editInput;

    private MyAdapter adapter;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        List<TaskModel> dataList = dbHelper.getAllTask();
        adapter = new MyAdapter(dataList, dbHelper);
        buttonAdd = findViewById(R.id.btnAdd);
        editInput = findViewById(R.id.edtInput);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Button Add", "onClick: " + editInput.getText().toString());


//                String text = editInput.getText().toString().trim();
                String text = editInput.getText().toString();
//                For check the field is empty or not
                if (text.isEmpty()) {
                    // Display an error message or provide feedback to the user
                    Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
                } else {
//                    Set the data before send to database
                    TaskModel newItem = new TaskModel(-1, text);
//                    Add data to SQLite
                    boolean success = dbHelper.addOne(newItem);

                    if (success) {
                        adapter.addItem(newItem); // Add the new task to the adapter

                        Toast.makeText(getApplicationContext(), "Add new task Success", Toast.LENGTH_SHORT).show();
                        Log.e("ADD", "onClick Success Add: " + newItem);

                    } else {
                        Toast.makeText(getApplicationContext(), "Add new task Unsuccessful " , Toast.LENGTH_SHORT).show();
                        Log.e("ADD", "onClick Unsuccessful Add: " + newItem);
                    }

                }

//                Set to hide keyboard and clear the field
                editInput.getText().clear();
                hideKeyboard();
//                Make the Text input unfocused after clicked the button
                editInput.setFocusable(false);
                editInput.setFocusableInTouchMode(false);
//                Set the field to can usable for next time
                editInput.requestFocus();
                editInput.setFocusable(true);
                editInput.setFocusableInTouchMode(true);
//                To refresh the recyclerView
//                Get the collection after add new data to database
                List<TaskModel> updatedDataList = dbHelper.getAllTask(); // Retrieve updated data
                adapter.setDataList(updatedDataList); // Update the adapter's data list
                adapter.notifyDataSetChanged(); // Notify the adapter of the data change
            }
        });


    }



    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }


}