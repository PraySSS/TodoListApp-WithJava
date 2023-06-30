package com.example.todolistjava;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<TaskModel> dataList;
    private RefreshListener refreshListener;
    private DBHelper dbHelper;
    public MyAdapter(List<TaskModel> dataList, DBHelper dbHelper) {
        this.dataList = dataList;
        this.dbHelper = dbHelper;
    }

    public void setRefreshListener(RefreshListener listener) {
        this.refreshListener = listener;
    }

    public void setDataList(List<TaskModel> dataList) {
        this.dataList = dataList;
    }

    public void addItem(TaskModel newItem) {
        if (dataList.size() >= 5) {
            dataList.remove(dataList.size() - 1); // Remove the last item
            notifyItemRemoved(dataList.size());
        }
        dataList.add(0, newItem); // Add the new item at the beginning of the list
        notifyItemInserted(0); // Notify the adapter about the new item at position 0
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //    For inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //  assigning values to the views we created in the layout

        TaskModel task = dataList.get(position);
        //  based on the position of the recycler view
        holder.textItem.setText(task.getTodoTask());
    }

    @Override
    public int getItemCount() {
        // Return the minimum of dataList size and set max to 5
        return Math.min(dataList.size(), 5);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textItem;
        public ImageButton buttonDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            textItem = itemView.findViewById(R.id.txtItem);
            buttonDelete = itemView.findViewById(R.id.btnDelete);
            dbHelper = new DBHelper(itemView.getContext());
           buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {


                        TaskModel taskModel = dataList.get(position);
                        boolean success = dbHelper.deleteOne(taskModel);
                        if (success) {
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(itemView.getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
                            Log.e("DELETE", "onClick: "+position);
                        } else {
                            Toast.makeText(itemView.getContext(), "Failed to delete task", Toast.LENGTH_SHORT).show();
                            Log.e("DELETE", "onClick: "+position);
                        }
                    }
                }
            });
        }
    }

    public interface RefreshListener {
        void onRefresh();
    }
}

//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//    DBHelper dbHelper ;
//    private  List<TaskModel> dataList = dbHelper.getAllTask();
//
//
//    @NonNull
//
//
//
//    public MyAdapter(List<TaskModel> dataList) {
//        this.dataList = dataList;
//    }
//
//    @Override
//    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //    For inflate the layout
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
//        return new MyAdapter.ViewHolder(view, dataList);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
//
//        //  assigning values to the views we created in the layout
//        String data = String.valueOf(dataList.get(position));
//        //  based on the position of the recycler view
//        holder.textItem.setText(data);
//
//    }
//    public void addItem(TaskModel newItem) {
//
//        int position=dataList.size() + 1;
//        dataList.add(newItem);
//      notifyDataSetChanged();
//    }
//    @Override
//    public int getItemCount() {
//
//        // Return the minimum of dataList size and set max to 5
//
//        return Math.min(dataList.size(), 5);
//    }
//
//    public  class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView textItem;
//        public ImageButton buttonDelete;
//        private Button buttonAdd;
//        public ViewHolder(View itemView, final List<TaskModel> dataList) {
//            super(itemView);
//            textItem = itemView.findViewById(R.id.txtItem);
//            buttonDelete = itemView.findViewById(R.id.btnDelete);
//
//
//            buttonDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Log.e("Button", "onClick: Delete" + dataList.get(position));
//
//                        // Remove the item from the dataList using position
//                        dataList.remove(position);
//
//                        // Notify the adapter that an item is removed
//                        notifyItemRemoved(position);
//                    }
//                }
//            });
//
//        }
//    }
//
//
//
//
//}
