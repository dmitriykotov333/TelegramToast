package com.telegram.simpletoast;

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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


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
     *
     * @param context      class
     * @param message      text
     * @param buttonText   button
     * @param timeProgress int
     * @param position     int
     */
    public Toaster(RecyclerViewAdapter adapter, Context context, String message, String buttonText, int timeProgress, int position) {
        this.adapter = adapter;
        this.context = context;
        this.message = message;
        this.buttonText = buttonText;
        this.timeProgress = timeProgress;
        this.position = position;
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
        button.setOnClickListener(v -> {
            close = -1;
            dialog.dismiss();
        });
    }

    public void run() {
        final Dialog dialog = new Dialog(context, R.style.ThemeOverlay_AppCompat_Dialog_Alert_TestDialogTheme);
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
        User user = adapter.deleteItem(position);
        Thread thread = new Thread(() -> {
            for (int i = timeProgress; i >= 1; i--) {
                int finalI = i;
                if (close == -1) {
                    handler.post(() -> adapter.reestablish(user, position));
                    break;
                }
                handler.post(() -> textProgress.setText(String.valueOf(finalI)));
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