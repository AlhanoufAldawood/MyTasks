package com.example.alhanoufaldawood.swe444;

public class Child {

    String id;
    String name;
    String gender;
    String age;
    String user;
    String password;

    public Child(){

    }

    public String getId() {
        return id;
    }

    public Child(String id, String name, String gender, String age, String user, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.user = user;
        this.password = password;
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

