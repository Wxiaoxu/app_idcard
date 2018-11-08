package com.example.wx.myapplication;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 文本两端对齐
 *
 * 类型 1                 1
 *
 * 续
 */

public class GroupTextTwoLayout extends RelativeLayout {

    private SideJustifyTextView firstTextView;
    private TextView secondTextView;

    public GroupTextTwoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        inflate(context, R.layout.group_two_text_layout, this);
        firstTextView = findViewById(R.id.group_left_TextView);
        secondTextView = findViewById(R.id.group_right_TextView);


        int layoutOrientation;
        boolean isLinkage;
        int innerMargin = -1;


        TextConfigEntity firstConfigEntity = new TextConfigEntity();
        TextConfigEntity secondConfigEntity;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GroupTwoTextLayout);
        firstConfigEntity.textColorState = a.getColorStateList(R.styleable.GroupTwoTextLayout_app_first_text_color);
        firstConfigEntity.text = a.getString(R.styleable.GroupTwoTextLayout_app_first_text);
        firstConfigEntity.textSize = a.getDimensionPixelSize(R.styleable.GroupTwoTextLayout_app_first_text_size, 14);
        firstConfigEntity.width = a.getDimensionPixelOffset(R.styleable.GroupTwoTextLayout_app_first_text_width, 0);
        firstConfigEntity.height = a.getDimensionPixelOffset(R.styleable.GroupTwoTextLayout_app_first_text_height, 0);

        innerMargin = a.getDimensionPixelOffset(R.styleable.GroupTwoTextLayout_app_inner_margin_size, 0);
        layoutOrientation = a.getInt(R.styleable.GroupTwoTextLayout_app_layout_orientation, 0);
        isLinkage = a.getBoolean(R.styleable.GroupTwoTextLayout_app_is_linkage, true);

        if (isLinkage) {
            secondConfigEntity = firstConfigEntity.copyThisEntity();
        } else {
            secondConfigEntity = new TextConfigEntity();
            secondConfigEntity.textColorState = a.getColorStateList(R.styleable.GroupTwoTextLayout_app_second_text_color);
            secondConfigEntity.textSize = a.getDimensionPixelSize(R.styleable.GroupTwoTextLayout_app_second_text_size, -1);
        }

        secondConfigEntity.text = a.getString(R.styleable.GroupTwoTextLayout_app_second_text);
        secondConfigEntity.width = a.getDimensionPixelOffset(R.styleable.GroupTwoTextLayout_app_second_text_width, -9);
        secondConfigEntity.height = a.getDimensionPixelOffset(R.styleable.GroupTwoTextLayout_app_second_text_height, -9);

        a.recycle();

        if (!TextUtils.isEmpty(firstConfigEntity.text)) {
            firstTextView.setText(firstConfigEntity.text);
        }

        if(firstConfigEntity.textSize != -1){
            firstTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, firstConfigEntity.textSize);
        }
        if (firstConfigEntity.textColorState != null) {
            firstTextView.setTextColor(firstConfigEntity.textColorState);
        }

        LayoutParams firstParams = (LayoutParams) firstTextView.getLayoutParams();
        if (firstConfigEntity.width > 0){
            firstParams.width = firstConfigEntity.width;
        }
        if (firstConfigEntity.height > 0){
            firstParams.height = firstConfigEntity.height;
        }
        firstParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        LayoutParams secondParams = (LayoutParams) secondTextView.getLayoutParams();
        if (secondConfigEntity.width == -9) {
            secondParams.width = LayoutParams.WRAP_CONTENT;
        } else {
            secondParams.width = secondConfigEntity.width;
        }

        if (secondConfigEntity.height == -9) {
            secondParams.height = LayoutParams.WRAP_CONTENT;
        } else {
            secondParams.height = secondConfigEntity.height;
        }

        if (layoutOrientation == 0) {
            secondParams.addRule(RelativeLayout.RIGHT_OF, R.id.group_left_TextView);
            secondParams.addRule(RelativeLayout.CENTER_VERTICAL);
            secondParams.leftMargin = firstParams.rightMargin = innerMargin / 2;
            firstTextView.setGravity(Gravity.CENTER_VERTICAL);
            secondTextView.setGravity(Gravity.CENTER_VERTICAL);

        } else {
            secondParams.addRule(RelativeLayout.BELOW, R.id.group_left_TextView);
            secondParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            secondParams.topMargin = firstParams.bottomMargin = innerMargin / 2;
            firstTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            secondTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        secondTextView.setText(secondConfigEntity.text);

        if (secondConfigEntity.textColorState != null) {
            secondTextView.setTextColor(secondConfigEntity.textColorState);
        }
        if (secondConfigEntity.textSize > 0){
            secondTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, secondConfigEntity.textSize);
        }

        firstTextView.setLayoutParams(firstParams);
        secondTextView.setLayoutParams(secondParams);

    }


    public void setLeftText(@StringRes int textID){
        firstTextView.setText(textID);
    }

    public void setLeftText(CharSequence text){
        firstTextView.setText(text);
    }

    public void setContentText(CharSequence text){
        secondTextView.setText(text);
    }

    public void setContentText(@StringRes int textId){
        secondTextView.setText(textId);
    }


    private static class TextConfigEntity {
        ColorStateList textColorState;
        int textSize;
        String text;
        int width, height;

        public TextConfigEntity copyThisEntity() {
            TextConfigEntity entity = new TextConfigEntity();
            entity.textColorState = textColorState;
            entity.textSize = textSize;
            return entity;
        }

    }


}
