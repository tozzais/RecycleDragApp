package com.example.recycledragapp;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_select;


    private List<GridItemBean> allData;
    private SPUtils sfUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_select =  findViewById(R.id.rv_select);
        init();
        addListener();
    }




    public void init() {
        getSupportActionBar().hide();

        sfUtils  = new SPUtils(this);
        allData = sfUtils.getShowData();

        final EditAdapter myAdapter = new EditAdapter(this, allData);
        rv_select.setLayoutManager(new LinearLayoutManager(this));
        rv_select.setAdapter(myAdapter);

    }

    public void addListener() {
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfUtils.saveMenuData(allData);
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
