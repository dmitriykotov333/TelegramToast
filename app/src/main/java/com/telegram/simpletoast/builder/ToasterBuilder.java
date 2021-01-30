package com.telegram.simpletoast.builder;

import android.content.Context;

import com.telegram.simpletoast.User;

public class ToasterBuilder implements Disposable {

    private String message;
    private String buttonText;
    private Context context;
    private int timeProgress;
    private int position = 0;
    private Toaster.Callback<User> callback;

    public ToasterBuilder(String message, String buttonText, Context context, int timeProgress, int position, Toaster.Callback<User> callback) {
        this.message = message;
        this.buttonText = buttonText;
        this.context = context;
        this.timeProgress = timeProgress;
        this.position = position;
        this.callback = callback;
    }

    @Override
    public void dispose() {
        message = null;
        buttonText = null;
        context = null;
        timeProgress = 0;
        position = 0;
        callback = null;
    }
}
