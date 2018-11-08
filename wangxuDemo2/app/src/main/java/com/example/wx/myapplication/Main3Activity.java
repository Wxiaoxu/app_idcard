package com.example.wx.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.wx.myapplication.adapter.TestStackAdapter;
import com.example.wx.myapplication.view.UpAndDownSlidinglayout;
import com.loopeer.cardstack.CardStackView;

import java.util.Arrays;

public class Main3Activity extends AppCompatActivity {

    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };
    CardStackView card_view;//当成listView使用
    TestStackAdapter adapter;

    private UpAndDownSlidinglayout slidinglayout;
    private LinearLayout linearLayout;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    /**
     * 用于填充contentListAdapter的数据源。
     */
    private String[] contentItems = { "Content Item 1", "Content Item 2",
            "Content Item 3", "Content Item 4", "Content Item 5",
            "Content Item 6", "Content Item 7", "Content Item 8",
            "Content Item 9", "Content Item 10", "Content Item 11",
            "Content Item 12", "Content Item 13", "Content Item 14",
            "Content Item 15", "Content Item 16" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initData();
        initListner();
    }

    private void initListner() {


    }

    private void initData() {
        card_view
                = findViewById(R.id.card_view);
        adapter = new TestStackAdapter(this);
        card_view.setAdapter(adapter);
        adapter.updateData(Arrays.asList(TEST_DATAS));

        //浮动的View
        linearLayout = findViewById(R.id.content);
        slidinglayout= findViewById(R.id.up_sliding_layout);
        listView = findViewById(R.id.contentList);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,contentItems);
        listView.setAdapter(arrayAdapter);
        slidinglayout.setScrollEvent(linearLayout);


    }

}
