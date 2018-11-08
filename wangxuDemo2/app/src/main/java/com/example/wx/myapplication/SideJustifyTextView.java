package com.example.wx.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class SideJustifyTextView extends TextView {

    private Paint textPaint;

    public SideJustifyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textPaint = getPaint();
        textPaint.setAntiAlias(true);
    }

    public SideJustifyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SideJustifyTextView(Context context) {
        this(context, null, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        textPaint.setColor(getTextColors().getDefaultColor());

        int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int singleWordWidth = (int) textPaint.measureText("树");
        String str = getText().toString();
        int realWidth = viewWidth;
        int length = getText().length();
        boolean hasContainerDot = false;

        int dotWidth = (int) (textPaint.measureText(":"));
        if (str.endsWith("：") || str.endsWith(":")) {
            realWidth -= dotWidth * 2;
            length -= 1;
            hasContainerDot = true;
        }


        int everyWordWidth = 0;
        if (length > 2) {
            everyWordWidth = (realWidth - singleWordWidth) / (length - 1);

            char[] words = getText().toString().toCharArray();
            for (int i = 0; i < length; i++) {
                canvas.drawText(String.valueOf(words[i]), getPaddingLeft() + i * (everyWordWidth), (getHeight() + singleWordWidth * 2 / 3) / 2, textPaint);
            }
        } else if (length == 2) {
            char[] words = getText().toString().toCharArray();
            for (int i = 0; i < length; i++) {
                canvas.drawText(String.valueOf(words[i]), getPaddingLeft() + i * (realWidth - singleWordWidth), (getHeight() + singleWordWidth * 2 / 3) / 2, textPaint);
            }
        }

        if (hasContainerDot) {
            canvas.drawText(":", (getWidth() - dotWidth * 2 / 3 - getPaddingRight()), (getHeight() + singleWordWidth * 2 / 3) / 2, textPaint);
        }

    }
}
