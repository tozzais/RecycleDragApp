package com.example.recycledragapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_select;


    private List<GridItemBean> allData = new ArrayList<>();
    private SPUtils sfUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv_select =  findViewById(R.id.rv_select);

        initData();
        init();
        addListener();
    }

    private void initData() {
        Log.e("TAG","1234567");
        sfUtils  = new SPUtils(this);
//        allData = sfUtils.getShowData();
        List<GridItemBean> showData = sfUtils.getShowData();
        //一级
        for (GridItemBean item1:showData) {
            allData.add(new GridItemBean(item1.getName(),0));
            //二级
            List<GridItemBean> list2 = item1.getItem();
            for (GridItemBean item2 : list2) {
                List<GridItemBean> list3 = item2.getItem();
                if (list3 != null && list3.size()>0){
                    //三级菜单
                    allData.add(new GridItemBean(item2.getName(),2));
                    allData.addAll(list3);
                }else {
                    //两级菜单
                    allData.addAll(list2);
                    break;
                }
            }
        }
    }


    public void init() {
        getSupportActionBar().hide();

        final ShowAdapter myAdapter = new ShowAdapter(this, allData);
        GridLayoutManager gridManager = new GridLayoutManager(this, 4);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                GridItemBean fi = allData.get(position);
                // 因为分成了 4份。1代表1份  4代表4份。所有不是功能菜单的时候占4 返回一整行
                return fi.status == 1 ? 1 : 4;
            }
        });
        rv_select.setLayoutManager(gridManager);
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
