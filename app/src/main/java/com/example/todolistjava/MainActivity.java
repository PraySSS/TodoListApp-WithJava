package com.example.todolistjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

public class MainActivity extends AppCompatActivity implements MyAdapter.RefreshListener {
//public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button buttonAdd;

    private EditText editInput;

    private MyAdapter adapter;

    DBHelper dbHelper;
//    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        List<TaskModel> dataList = dbHelper.getAllTask();
        adapter = new MyAdapter(dataList,dbHelper);
        buttonAdd = findViewById(R.id.btnAdd);
        editInput = findViewById(R.id.edtInput);


//        List<String> dataList = createTestData(); // Create test data

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskModel taskModel;

                Log.e("Button Add", "onClick: " + editInput.getText().toString());


//                String text = editInput.getText().toString().trim();
                String text = editInput.getText().toString();
//                For check the field is empty or not
                if (text.isEmpty()) {
                    // Display an error message or provide feedback to the user
                    Toast.makeText(getApplicationContext(), "Please enter some text", Toast.LENGTH_SHORT).show();
                } else {
                    TaskModel newItem = new TaskModel(-1, text);
//                    Add data to SQLite
                    boolean success = dbHelper.addOne(newItem);
                    if (success) {
                        adapter.addItem(newItem); // Add the new task to the adapter
//                        recyclerView.scrollToPosition(0); // Scroll to the top of the list

                        Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                        Log.e("ADD", "onClick: "+newItem );

                    }

//                    try {
//
////                        ArrayAdapter taskArrayAdapter = new ArrayAdapter<TaskModel>(MainActivity.this, android.R.layout.simple_list_item_1, allTask);
////                        recyclerView.setAdapter(taskArrayAdapter);
//                        taskModel = new TaskModel(-1, text);
//                        boolean success = dbHelper.addOne(taskModel);
//                        adapter.addItem(taskModel);
//                        dbHelper.getAllTask();
////                        int newPosition = adapter.getItemCount() - 1;
////                        adapter.notifyItemInserted(newPosition);
//                        Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
//
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), "Error creating task", Toast.LENGTH_SHORT).show();
//                        taskModel = new TaskModel(-1, "error");
//                    }


//                    adapter.notifyDataSetChanged();
                }

//                Set to hide keyboard and clear the field
                editInput.getText().clear();
                hideKeyboard();
                editInput.setFocusable(false);
                editInput.setFocusableInTouchMode(false);
//                Set the field for use next time
                editInput.requestFocus();
                editInput.setFocusable(true);
                editInput.setFocusableInTouchMode(true);

                startActivity(getIntent());
            }
        });


    }


    @Override
    public void onRefresh() {
        List<TaskModel> updatedDataList = dbHelper.getAllTask(); // Retrieve updated data
        adapter.setDataList(updatedDataList); // Update the adapter's data list
        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

//      Mock Data
//    private List<String> createTestData() {
//        List<String> testData = new ArrayList<>();
//        testData.add("Lorem ipsum dolor sit ametDonec sollicitudin velit ligula, vel placerat risus egestas non. Nulla diam risus, bibendum sit amet placerat in, pellentesque lacinia odio. Duis ut diam volutpat, pulvinar sapien at, consectetur est. Phasellus at malesuada libero. Proin sed luctus erat. Proin sapien purus, lobortis at lectus a, finibus vestibulum elit. Quisque ac nunc a metus vulputate hendrerit. Praesent a sem quam. Nulla imperdiet leo sit amet mi posuere blandit. Nam metus libero, tincidunt nec meo sapien, sit amet placerat m.");
//        testData.add("Item 2");
//        testData.add("Item 3");
//        testData.add("Item 4");
//        testData.add("Item 5");
//        testData.add("Item 6");
//        testData.add("Item 7");
//        testData.add("Item 8");
//        // Add more test data as needed
//
//        // Sort the testData list in descending order
//        Collections.sort(testData, Collections.reverseOrder());
//
//        return testData;
//    }

}