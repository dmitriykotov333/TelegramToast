package com.telegram.simpletoast;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {

    private ProgressBar progressBar;
    private int progressTo;
    private float rotationTo;
    private float rotationFrom;
    private long animationDuration;
    private boolean forceClockwiseRotation;
    private boolean forceCounterClockwiseRotation;

    /**
     * Deafault constructor
     * @param progressBar
     * @param animationDuration - time required to change progress
     */
    public ProgressBarAnimation(ProgressBar progressBar, long animationDuration) {
        super();
        this.progressBar = progressBar;
        this.animationDuration = animationDuration;
        forceClockwiseRotation = false;
        forceCounterClockwiseRotation = false;
    }

    private void setProgress(int progress, float rotation) {
        if (progressBar != null) {
            // new progress must be between 0 and max
            if (progress < 0) {
                progress = 0;
            }
            if (progress > progressBar.getMax()) {
                progress = progressBar.getMax();
            }
            progressTo = progress;
            // rotation value should be between 0 and 360
            rotationTo = rotation % 360;
            // current rotation value should be between 0 and 360
            if (progressBar.getRotation() < 0) {
                progressBar.setRotation(progressBar.getRotation() + 360);
            }
            progressBar.setRotation(progressBar.getRotation() % 360);

            rotationFrom = progressBar.getRotation();
            // check for clock wise rotation
            if (forceClockwiseRotation && rotationTo < rotationFrom) {
                rotationTo += 360;
            }
            // check for counter clock wise rotation
            if (forceCounterClockwiseRotation & rotationTo > rotationFrom) {
                rotationTo -= 360;
            }
            setDuration(animationDuration);
            progressBar.startAnimation(this);
        }
    }

    public void setProgressBar(int progress) {
        if (progressBar != null) {
            setProgress(progress, progressBar.getRotation());
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float progress = (100 - (progressTo) * interpolatedTime);
        float rotation = rotationFrom + (rotationTo - rotationFrom) * interpolatedTime;

        if (progressBar != null) {
            progressBar.setProgress((int)progress);
            progressBar.setRotation(rotation);
        }
    }
}
