package com.example.todolistjava;

public class TaskModel {
    private int id;
    private String todoTask;

    public TaskModel(int id, String todoTask) {
        this.id = id;
        this.todoTask = todoTask;
    }

//    To make the TaskModel readable when we want to log the variable
    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", todoTask='" + todoTask + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodoTask() {
        return todoTask;
    }

    public void setTodoTask(String todoTask) {
        this.todoTask = todoTask;
    }
}
