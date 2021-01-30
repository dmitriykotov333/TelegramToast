package com.telegram.simpletoast.builder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.telegram.simpletoast.ProgressBarAnimation;
import com.telegram.simpletoast.R;
import com.telegram.simpletoast.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Toaster {
    /**
     * @param message used to display text information in toast
     * @param buttonText text button
     * @param position needed to get a position from the list
     * @param timeProgress how much must be active toast, after which the action is performed
     */
    private String message = "Remove";
    private String buttonText = "Undo";
    private Context context;
    private int timeProgress = 10;
    private int close;
    private int position = 0;
    private Callback<User> callback;

    public interface Callback<T> {
        T delete(int position);

        void dismiss(int position, T user);
    }

    public Toaster setCallback(Callback<User> callback) {
        this.callback = callback;
        return this;
    }


    public Toaster setMessage(String message) {
        this.message = message;
        return this;
    }

    public Toaster setButtonText(String buttonText) {
        this.buttonText = buttonText;
        return this;
    }

    public Toaster setContext(Context context) {
        this.context = context;
        return this;
    }

    public Toaster setTimeProgress(int timeProgress) {
        this.timeProgress = timeProgress;
        return this;
    }

    public Toaster setPosition(int position) {
        this.position = position;
        return this;
    }

    public ToasterBuilder build() {
        return new ToasterBuilder(message, buttonText, context, timeProgress, position, callback);
    }

    @BindView(R.id.progress_circular)
    ProgressBar progressBar;
    @BindView(R.id.text_progress)
    TextView textProgress;
    @BindView(R.id.text_cancel)
    TextView button;
    @BindView(R.id.text_title)
    TextView textTitle;
    Handler handler;

    /**
     * Initialize view for dialog
     *
     * @param dialog init
     */
    private void init(final Dialog dialog) {
        ButterKnife.bind(this, dialog);
        button.setText(buttonText);
        textTitle.setText(message);
        handler = new Handler();
    }


    public Toaster run() {
        Dialog dialog = new Dialog(context, R.style.ThemeOverlay_AppCompat_Dialog_Alert_TestDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow(dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_toast);
        init(dialog);
        progressBar.setMax(100);
        progressBar.setRotation(90);
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 5000);
        animation.setProgressBar(100);
        progressBar.startAnimation(animation);
        User user = callback.delete(position);
        button.setOnClickListener(v -> {
            if (callback != null) {
                callback.dismiss(position, user);
            }
            close = -1;
            dialog.dismiss();
        });
        Thread thread = new Thread(() -> {
            for (int i = timeProgress; i >= 1; i--) {
                int finalI = i;
                if (close == -1) {
                    break;
                }
                handler.post(() -> {
                    textProgress.setText(String.valueOf(finalI));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dialog.dismiss();
        });
        thread.start();
        Objects.requireNonNull(dialog.getWindow()).setAttributes(getWindowsManager(dialog));
        dialog.show();
        return this;
    }


    /**
     * thanks to this method, we can arrange that where we deem necessary
     *
     * @param dialog class
     * @return WindowManager.LayoutParams
     */
    private WindowManager.LayoutParams getWindowsManager(Dialog dialog) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        return lp;
    }


    /**
     * This method added transparent background
     * and allows touch recycler view and other view
     *
     * @param dialog lcass
     */
    private void getWindow(Dialog dialog) {
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(0);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}