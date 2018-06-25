package com.heyongrui.firework;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heyongrui.fireworkanim.BezierFireworkAnim;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BezierFireworkAnim fireworkAnim;//贝塞尔抛物线动画
    private Drawable redHeartBg;
    private Drawable grayBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);
        ImageView iv = findViewById(R.id.iv);
        Button btn = findViewById(R.id.btn);

        tv.setOnClickListener(this);
        iv.setOnClickListener(this);
        btn.setOnClickListener(this);

        fireworkAnim = new BezierFireworkAnim(this);

        redHeartBg = colorDrawable(this, R.drawable.heart, R.color.red);
        grayBg = ContextCompat.getDrawable(this, R.drawable.heart);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv:
                boolean selected = view.isSelected();
                if (!selected) {
                    ((ImageView) view).setImageDrawable(redHeartBg);
                    fireworkAnim.startAnim(view);
                } else {
                    ((ImageView) view).setImageDrawable(grayBg);
                    fireworkAnim.cancelAnim();
                }
                view.setSelected(!selected);
                break;
            default:
                fireworkAnim.startAnim(view);
                break;
        }
    }

    public Drawable colorDrawable(Context context, @DrawableRes int drawableResId, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        int tint_color = ContextCompat.getColor(context, color);
        Drawable mutate = drawable.mutate();
        return colorDrawable(mutate, tint_color);
    }

    public Drawable colorDrawable(Drawable drawable, int color) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, ColorStateList.valueOf(color));
        return wrappedDrawable;
    }
}
