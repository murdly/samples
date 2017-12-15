package com.karbowy.overflowmenu;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public class ViewAnimationUtils {
    
    public static AnimatorSet createLinearReveal(final View viewToReveal, final int offsetWidth, final int offsetHeight, final int duration) {
        viewToReveal.clearAnimation();

        final int targetWidth = viewToReveal.getMeasuredWidth();
        final int targetHeight = viewToReveal.getMeasuredHeight();

        viewToReveal.getLayoutParams().height = offsetHeight;
        viewToReveal.requestLayout();

        final ValueAnimator heightAnimator = ValueAnimator
                .ofInt(offsetHeight, targetHeight)
                .setDuration(duration);
        heightAnimator.addUpdateListener(animation -> {
            viewToReveal.getLayoutParams().height = (int) animation.getAnimatedValue();
            viewToReveal.requestLayout();
        });

        final ValueAnimator widthAnimator = ValueAnimator
                .ofInt(offsetWidth, targetWidth)
                .setDuration(duration);
        widthAnimator.addUpdateListener(animation -> {
            viewToReveal.getLayoutParams().width = (int) animation.getAnimatedValue();
            viewToReveal.requestLayout();
        });

        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(widthAnimator, heightAnimator);
        set.setInterpolator(new AccelerateInterpolator());
        return set;
    }
}
