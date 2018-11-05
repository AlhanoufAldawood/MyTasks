package com.example.alhanoufaldawood.swe444;


import java.util.HashMap;

public class Child {

    String id;
    String name;
    String gender;
    String age;
    String user;
    String password;
    String parentId;
    //Task task;
    //String tasks;


    public Child(){

    }



    public Child(String id, String name, String gender, String age, String user, String password, String parentId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.user = user;
        this.password = password;
        this.parentId = parentId;
        //this.tasks = task;


    }
    public String getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }



    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

