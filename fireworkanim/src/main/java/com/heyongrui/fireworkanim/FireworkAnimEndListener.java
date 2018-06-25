package com.heyongrui.fireworkanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lambert on 2018/6/22.
 */

public class FireworkAnimEndListener extends AnimatorListenerAdapter {
    private View mFireworkItemView;
    private ViewGroup mRootView;

    public FireworkAnimEndListener(View fireworkItemView, ViewGroup rootView) {
        this.mFireworkItemView = fireworkItemView;
        this.mRootView = rootView;
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        mRootView.removeView((mFireworkItemView));
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        super.onAnimationCancel(animation);
        mRootView.removeView((mFireworkItemView));
    }
}