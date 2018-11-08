package com.example.wx.myapplication.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.wx.myapplication.R;

/**
 * 带红点的ImageView
 */
public class DotImageView extends AppCompatImageView {

    private Paint paint;
    private int dotRadius = 5;
    private boolean isShowDot = false;

    public DotImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        initializeAttr(context, attrs);
    }

    private void initializeAttr(Context context, @Nullable AttributeSet attrs) {
        int dotColor;

        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.DotImageView);
        dotRadius = t.getDimensionPixelOffset(R.styleable.DotImageView_app_dot_radius, 5);
        dotColor = t.getColor(R.styleable.DotImageView_app_dot_color, Color.RED);
        isShowDot = t.getBoolean(R.styleable.DotImageView_app_is_show_dot, false);
        t.recycle();

        paint.setColor(dotColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowDot) {
            float cx = getWidth() - getPaddingRight();
            float cy = getPaddingTop();
            canvas.drawCircle(cx, cy, dotRadius, paint);
        }
    }

    public void setColorRes(@ColorRes int colorRes) {
        paint.setColor(getResources().getColor(colorRes));
        invalidate();
    }


    public void setDotColor(int dotColor) {
        paint.setColor(dotColor);
        invalidate();
    }

    public void setShowDot(boolean isShowDot) {
        this.isShowDot = isShowDot;
        invalidate();
    }
}
