package com.karbowy.overflowmenu;


import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class OverflowMenu {

    private static final double ANIMATION_WIDTH_OFFSET_PERCENTAGE = 0.2;
    //how many items should be visible when animation starts
    private static final int ANIMATION_HEIGHT_OFFSET_ITEMS = 1;
    //value according to material design
    private static final int MENU_ITEM_HEIGHT_DP = 48;

    private final Context context;
    private final ViewGroup containerView;
    private final ViewGroup decorView;
    private LinearLayout menuView;
    private OnOverflowMenuItemSelectedListener menuItemSelectedListener;

    private boolean isShowing;

    private int menuHeight;
    private int menuWidth;
    private int animationDuration;


    public interface OnOverflowMenuItemSelectedListener {
        boolean onOverflowMenuItemSelected(int itemId);
    }

    public OverflowMenu(Context context, ViewGroup container) {
        this.context = context;
        this.containerView = container;
        this.decorView = createDecorView();
    }

    public void setMenuView(@LayoutRes int contentRes, int widthDp) {
        final View view = LayoutInflater.from(context).inflate(contentRes, decorView, false);

        if (view instanceof LinearLayout) {
            menuView = (LinearLayout) view;

            menuWidth = DimensionUtils.applyDpToPx(context, widthDp);

            menuView.measure(View.MeasureSpec.makeMeasureSpec(menuWidth, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(menuHeight, View.MeasureSpec.UNSPECIFIED));

            menuHeight = menuView.getMeasuredHeight();

            init();
        } else {
            throw new IllegalStateException("You must provide a view that has a LinearLayout at its root.");
        }
    }

    private void init() {
        decorView.addView(menuView);

        decorView.setOnClickListener(v -> startDismissAnimation());

        setClickListenersOnItems();
    }

    private void setClickListenersOnItems() {
        for (int index = 0; index < menuView.getChildCount(); index++) {
            final View view = menuView.getChildAt(index);
            view.setOnClickListener(v -> {
                if (menuItemSelectedListener != null) {

                    final boolean handled = menuItemSelectedListener.onOverflowMenuItemSelected(v.getId());

                    if (handled) {
                        startDismissAnimation();
                    }
                }
            });
        }
    }

    private void startDismissAnimation() {
        final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss(decorView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        menuView.startAnimation(animation);
    }

    private void dismiss(ViewGroup decorView) {
        if (isShowing) {
            isShowing = false;

            if (decorView.getParent() != null) {
                containerView.removeView(decorView);
            }
        }
    }

    public void setMenuItemSelectedListener(OnOverflowMenuItemSelectedListener menuItemSelectedListener) {
        this.menuItemSelectedListener = menuItemSelectedListener;
    }

    public void setAnimationDuration(int duration) {
        this.animationDuration = duration;
    }

    public void show() {
        if (!isShowing) {
            isShowing = true;

            if (containerView != null) {
                containerView.addView(decorView);
            }

            AnimatorSet animator = createAnimation(menuView);
            animator.start();
        }
    }

    public void dismiss() {
        startDismissAnimation();
    }

    private FrameLayout createDecorView() {
        final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);

        final FrameLayout decorView = new FrameLayout(context);
        decorView.setLayoutParams(params);
        return decorView;
    }

    private AnimatorSet createAnimation(View view) {
        final int offsetWidth = (int) (ANIMATION_WIDTH_OFFSET_PERCENTAGE * menuWidth);
        final int offsetHeight = ANIMATION_HEIGHT_OFFSET_ITEMS * DimensionUtils.applyDpToPx(context, MENU_ITEM_HEIGHT_DP);

        return ViewAnimationUtils.createLinearReveal(
                view,
                Math.min(offsetWidth, menuWidth),
                Math.min(offsetHeight, menuHeight),
                animationDuration);
    }

}
