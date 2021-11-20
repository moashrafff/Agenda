package com.ashraff.agenda.Model;

public class TaskModel {

    private int id,status;
    private String taskText;

    public TaskModel() {
    }

    public TaskModel(int id, int status, String taskText) {
        this.id = id;
        this.status = status;
        this.taskText = taskText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }


}
