package com.telegram.simpletoast;


public class User {
    private String name;
    private String message;
    private String time;
    private String image;

    public User(String image, String name, String message, String time) {
        this.image = image;
        this.name = name;
        this.message = message;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

}
