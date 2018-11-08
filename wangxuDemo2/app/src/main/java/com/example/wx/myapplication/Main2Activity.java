package com.example.wx.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wx.myapplication.bean.RowModel;
import com.example.wx.myapplication.recycle.OnActivityTouchListener;
import com.example.wx.myapplication.recycle.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity  {

    RecyclerView recyclerView;
    Main2Adapter main2Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recycle_view);
        initData();
        initListener();

    }

    private void initListener() {

    }

    private void initData() {
        recyclerView=findViewById(R.id.recycle_view);
        main2Adapter= new Main2Adapter(getData(),this);
        recyclerView.setAdapter(main2Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



    public class Main2Adapter extends RecyclerView.Adapter<Main2Adapter.MainViewHolder> {
        List<RowModel> rowModels;
        LayoutInflater layoutInflaterl;
        Context mContext;

        public Main2Adapter(List<RowModel> rowModels, Context mContext) {
            this.rowModels = rowModels;
            this.mContext = mContext;
            layoutInflaterl = LayoutInflater.from(mContext);
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = layoutInflaterl.inflate(R.layout.recycler_row,viewGroup,false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
            mainViewHolder.bindData(rowModels.get(i));
        }

        @Override
        public int getItemCount() {
            return rowModels.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder {

            TextView mainText, subText;
            public MainViewHolder(View view) {
                super(view);
                mainText=view.findViewById(R.id.mainText);
                subText=view.findViewById(R.id.subText);
            }
            public void bindData(RowModel rowModel) {
                mainText.setText(rowModel.getMainText());
                subText.setText(rowModel.getSubText());
            }
        }
    }

    private List<RowModel> getData() {
        List<RowModel> list = new ArrayList<>(25);
        for (int i = 0; i < 25; i++) {
            list.add(new RowModel("Row " + (i + 1), "Some Text... "));
        }
        return list;
    }
}
