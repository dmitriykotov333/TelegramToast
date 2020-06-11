package com.telegram.simpletoast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

/**
 * Inner class
 */
class Toaster {
    /**
     * @param message used to display text information in toast
     * @param buttonText text button
     * @param position needed to get a position from the list
     * @param timeProgress how much must be active toast, after which the action is performed
     */
    private String message;
    private String buttonText;
    private Context context;
    private int position;
    private int timeProgress;
    private int close;
    private RecyclerViewAdapter adapter;
    /**
     * Constructor
     * @param context
     * @param message
     * @param buttonText
     * @param timeProgress
     * @param position
     */
    public Toaster(RecyclerViewAdapter adapter, Context context, String message, String buttonText, int timeProgress, int position) {
        this.adapter = adapter;
        this.context = context;
        this.message = message;
        this.buttonText = buttonText;
        this.timeProgress = timeProgress;
        this.position = position;
    }

    private ProgressBar progressBar;
    private TextView textProgress;
    private TextView button;
    private TextView textTitle;
    private Handler handler;

    /**
     * Initialize view for dialog
     * @param dialog
     */
    private void initView(final Dialog dialog) {
        progressBar = dialog.findViewById(R.id.progress_circular);
        textProgress = dialog.findViewById(R.id.text_progress);
        button = dialog.findViewById(R.id.text_cancel);
        button.setText(buttonText);
        textTitle = dialog.findViewById(R.id.text_title);
        textTitle.setText(message);
        handler = new Handler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close = -1;
                dialog.dismiss();
            }
        });
    }

    public void run() {
        final Dialog dialog = new Dialog(context, R.style.ThemeOverlay_AppCompat_Dialog_Alert_TestDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow(dialog);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_toast);
        initView(dialog);
        progressBar.setMax(100);
        progressBar.setRotation(90);
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 5000);
        animation.setProgressBar(100);
        progressBar.startAnimation(animation);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = timeProgress; i >= 1; i--) {
                    if (close == -1) {
                        break;
                    }
                    final int finalI = i;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (finalI == 1) {
                                adapter.deleteItem(position);
                            }
                            textProgress.setText(String.valueOf(finalI));
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
            }
        });
        thread.start();
        Objects.requireNonNull(dialog.getWindow()).setAttributes(getWindowsManager(dialog, Gravity.BOTTOM));
        dialog.show();
    }

    /**
     * thanks to this method, we can arrange that where we deem necessary
     * @param dialog
     * @param gravity
     * @return
     */
    private WindowManager.LayoutParams getWindowsManager(Dialog dialog, int gravity) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = gravity;
        return lp;
    }


    /**
     * This method added transparent background
     * and allows touch recycler view and other view
     * @param dialog
     */
    private void getWindow(Dialog dialog) {
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setDimAmount(0);
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

}