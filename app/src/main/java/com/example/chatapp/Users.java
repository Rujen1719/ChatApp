package com.example.chatapp;

public class Users {

    public String name;
    public String image;
    public String status;
    public String id;
    public String thumb_image;

    public Users(){

    }

    public Users(String name, String image, String status, String id, String thumb_image) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.id = id;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
