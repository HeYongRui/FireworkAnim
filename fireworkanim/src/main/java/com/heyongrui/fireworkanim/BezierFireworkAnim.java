package com.heyongrui.fireworkanim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lambert on 2018/6/22.
 */

public class BezierFireworkAnim {
    private Activity mActivity;
    private ViewGroup mRootView;//目标控件所在页面的顶级视图层
    private Random mRandom = new Random();
    private Drawable[] mDrawables;//烟花小图标资源
    private ArrayList<Animator> mAnimatorList;//动画集合管理器
    private int mTargetX;//目标控件在窗口的X坐标
    private int mTargetY;//目标控件在窗口的Y坐标

    public BezierFireworkAnim(Activity activity) {
        this.mActivity = activity;
        //获取当前Activity顶级视图
        mRootView = (ViewGroup) activity.getWindow().getDecorView();
        initIconRes(mActivity);
    }

    private void initIconRes(Context context) {
        //初始化烟花图标资源
        mDrawables = new Drawable[14];
        mDrawables[0] = context.getResources().getDrawable(R.drawable.emoji_1);
        mDrawables[1] = context.getResources().getDrawable(R.drawable.emoji_2);
        mDrawables[2] = context.getResources().getDrawable(R.drawable.emoji_3);
        mDrawables[3] = context.getResources().getDrawable(R.drawable.emoji_4);
        mDrawables[4] = context.getResources().getDrawable(R.drawable.emoji_5);
        mDrawables[5] = context.getResources().getDrawable(R.drawable.emoji_6);
        mDrawables[6] = context.getResources().getDrawable(R.drawable.emoji_7);
        mDrawables[7] = context.getResources().getDrawable(R.drawable.emoji_8);
        mDrawables[8] = context.getResources().getDrawable(R.drawable.emoji_9);
        mDrawables[9] = context.getResources().getDrawable(R.drawable.emoji_10);
        mDrawables[10] = context.getResources().getDrawable(R.drawable.emoji_11);
        mDrawables[11] = context.getResources().getDrawable(R.drawable.emoji_12);
        mDrawables[12] = context.getResources().getDrawable(R.drawable.emoji_13);
        mDrawables[13] = context.getResources().getDrawable(R.drawable.emoji_14);
    }

    public void startAnim(View targetView) {
        //获取目标控件的绝对中心坐标值
        int[] startXY = new int[2];
        targetView.getLocationInWindow(startXY);
        int height = targetView.getHeight();
        int width = targetView.getWidth();
        //防止初始位置误差，调整中心坐标
        this.mTargetX = startXY[0] + width / 2 - 20;
        this.mTargetY = startXY[1] + height / 2 - 10;
        //开始播放动画
        startFireworkAnim();
    }

    public void cancelAnim() {
        //停止取消动画
        if (mAnimatorList == null || mAnimatorList.isEmpty()) return;
        for (Animator animator : mAnimatorList) {
            boolean running = animator.isRunning();
            if (running) {
                animator.cancel();
            }
        }
    }

    private void startFireworkAnim() {//播放烟花动画
        int size = mRandom.nextInt(4) + 10;
        if (mAnimatorList == null) {
            mAnimatorList = new ArrayList<>();
        } else {
            mAnimatorList.clear();
        }
        for (int i = 0; i < size; i++) {
            //动态创建烟花效果小图标
            ImageView fireworkItemView = new ImageView(mActivity);
            fireworkItemView.setImageDrawable(mDrawables[mRandom.nextInt(14)]);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(50, 50);
            fireworkItemView.setLayoutParams(lp);
            mRootView.addView(fireworkItemView);
            //设置小图标的动画并播放
            Animator animator = getAnimator(fireworkItemView);
            animator.addListener(new FireworkAnimEndListener(fireworkItemView, mRootView));
            animator.start();
            mAnimatorList.add(animator);
        }
    }

    private Animator getAnimator(View target) {//创建生成烟花旋转动画
        //抛物线属性动画
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
        //旋转属性动画
        int rotationAngle = mRandom.nextInt(520) + 200;
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(target, "rotation", 0,
                rotationAngle % 2 == 0 ? rotationAngle : -rotationAngle);
        rotationAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rotationAnimator.setTarget(target);
        //组合两个动画一起播放
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.play(bezierValueAnimator).with(rotationAnimator);
        finalSet.setInterpolator(new AccelerateDecelerateInterpolator());
        finalSet.setDuration(1000);
        finalSet.setTarget(target);
        return finalSet;
    }

    private ValueAnimator getBezierValueAnimator(View target) {//创建贝塞尔烟花动画
        //构建贝塞尔曲线，实现抛物线动画效果
        float startX = mTargetX;
        float startY = mTargetY;
        int random = mRandom.nextInt(50);
        float endX = 0;
        float controlX = 0;
        float controlY = startY - new Random().nextInt(500) - 100;
        if (random % 2 == 0) {
            endX = mTargetX - random * 8;
            controlX = startX - random * 2;
        } else {
            endX = mTargetX + random * 8;
            controlX = startX + random * 2;
        }
        float endY = mTargetY + 400 + random;
        //贝塞尔二阶曲线控制点
        PointF controlP = new PointF(controlX, controlY);
        //构造贝塞尔估值器
        BezierEvaluator evaluator = new BezierEvaluator(controlP);
        //贝塞尔动画
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(startX, startY), new PointF(endX, endY));
        animator.setInterpolator(new AnticipateInterpolator());
        animator.addUpdateListener(new FireworkAnimUpdateListener(target));
        animator.setTarget(target);
        return animator;
    }
}
