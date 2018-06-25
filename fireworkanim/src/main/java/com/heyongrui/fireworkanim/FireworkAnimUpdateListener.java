package com.heyongrui.fireworkanim;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by lambert on 2018/6/22.
 * 贝塞尔烟花动画更新监听器
 */

public class FireworkAnimUpdateListener implements ValueAnimator.AnimatorUpdateListener {

    private View mFireworkItemView;//烟花小图标view

    public FireworkAnimUpdateListener(View fireworkItemView) {
        this.mFireworkItemView = fireworkItemView;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        PointF pointF = (PointF) animation.getAnimatedValue();
        //设置目标位置
        mFireworkItemView.setX(pointF.x);
        mFireworkItemView.setY(pointF.y);
        //设置透明度
        float animatedFraction = animation.getAnimatedFraction();
        float remainder = 1.0f - animatedFraction;
        mFireworkItemView.setAlpha(remainder);
    }
}