package com.example.alhanoufaldawood.swe444;


public class Task {


    String taskId;
    String title;
    String description;
    String date;
    String time;




    public Task() {

    }


    public Task(String taskId, String title, String description, String date, String time) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;

    }

    public String getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }


    public String getDescription() {
        return description;
    }



}
