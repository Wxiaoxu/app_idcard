package com.example.wx.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupSideAlignLayout extends RelativeLayout {
    private TextView leftTextView;
    private TextView rightTextView;

    public GroupSideAlignLayout(Context context) {
        super(context);
        initView(context);

    }
    public GroupSideAlignLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context, attrs);
    }
    private void initView(Context context) {
        inflate(context, R.layout.group_algin_side_layout, this);
        leftTextView = findViewById(R.id.group_left_TextView);
        rightTextView = findViewById(R.id.group_right_TextView);
    }


    private void initUI(Context context, AttributeSet attrs) {
        initView(context);

        String leftText;
        ColorStateList leftTextColor;
        int leftTextSize;
        int leftMaxWidth;

        String rightText;
        ColorStateList rightTextColor;
        int rightTextSize;
        int rightMaxWidth;

        int leftGravity, rightGravity;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupSideAlignLayout);
        leftText = a.getString(R.styleable.GroupSideAlignLayout_app_left_text);
        leftTextSize = a.getDimensionPixelSize(R.styleable.GroupSideAlignLayout_app_left_text_size, -1);
        leftTextColor = a.getColorStateList(R.styleable.GroupSideAlignLayout_app_left_text_color);
        leftMaxWidth = a.getDimensionPixelOffset(R.styleable.GroupSideAlignLayout_app_left_max_width, -1);
        leftGravity = a.getInt(R.styleable.GroupSideAlignLayout_app_left_layout_gravity, 0);

        rightGravity = a.getInt(R.styleable.GroupSideAlignLayout_app_right_layout_gravity, 0);

        rightText = a.getString(R.styleable.GroupSideAlignLayout_app_right_text);
        rightTextSize = a.getDimensionPixelSize(R.styleable.GroupSideAlignLayout_app_right_text_size, -1);
        rightTextColor = a.getColorStateList(R.styleable.GroupSideAlignLayout_app_right_text_color);
        rightMaxWidth = a.getDimensionPixelOffset(R.styleable.GroupSideAlignLayout_app_right_max_width, -1);
        a.recycle();

        if (leftTextColor != null) {
            leftTextView.setTextColor(leftTextColor);
        }
        if (leftTextSize > 0) {
            leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);
        }

        if (leftMaxWidth != -1) {
//            LayoutParams params = (LayoutParams) leftTextView.getLayoutParams();
//            params.width = leftMaxWidth;
//            leftTextView.setLayoutParams(params);
            leftTextView.setMaxWidth(leftMaxWidth);
        }

        setLeftText(leftText);

        setViewLayoutGravity(leftTextView, leftGravity);
        setViewLayoutGravity(rightTextView, rightGravity);

        setRightText(rightText);
        if (rightMaxWidth != -1) {
//            LayoutParams params = (LayoutParams) rightTextView.getLayoutParams();
//            params.width = rightMaxWidth;
//            rightTextView.setLayoutParams(params);
            rightTextView.setMaxWidth(rightMaxWidth);
        }

        if (rightTextSize > 0) {
            rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        }
        if (rightTextColor != null) {
            rightTextView.setTextColor(rightTextColor);
        }

    }



    private void setViewLayoutGravity(TextView textView, int gravity) {
        LayoutParams params = (LayoutParams) textView.getLayoutParams();
        switch (gravity) {
            case 0:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                break;
            case 1:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            case 2:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
        }
        textView.setLayoutParams(params);

    }

    public void setInfoText(CharSequence leftText, CharSequence rightText) {
        setLeftText(leftText);
        setRightText(rightText);
    }

    public void setRightText(CharSequence text) {
        if (text != null) {
            rightTextView.setText(text);
        }
    }

    public void setLeftText(CharSequence text) {
        if (text != null) {
            leftTextView.setText(text);
        }
    }

    public void setRightText(@StringRes int id) {
        rightTextView.setText(id);
    }

    public void setLeftText(@StringRes int id) {
        leftTextView.setText(id);
    }
}
