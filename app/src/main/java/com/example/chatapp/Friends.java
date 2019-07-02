package com.example.chatapp;

public class Friends {

    public String date;
    public String id;
    public String name;
    public String onlineStatus;

    public Friends() {

    }

    public Friends(String date) {
        this.date = date;
    }

    public Friends(String date, String id, String name, String onlineStatus) {
        this.date = date;
        this.id = id;
        this.name = name;
        this.onlineStatus = onlineStatus;
    }

    public Friends(String date, String id) {
        this.date = date;
        this.id = id;
    }

    public Friends(String date, String id, String name) {
        this.date = date;
        this.id = id;
        this.name = name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setId(String id) {
        this.id = id;
    }
}
