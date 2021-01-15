package com.example.recycledragapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_select, rv_all;

    private MenuSelectAdapter menuSelectAdapter;
    private MenuAdapter menuAdapter;

    private List<GridItemBean> allData;
    private List<GridItemBean> selectData;

    private SPUtils sfUtils;
    private static final int MAX_COUNT = 12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addListener();
    }




    public void init() {
        getSupportActionBar().hide();
        rv_select = (RecyclerView) findViewById(R.id.rv_select);
        rv_all = (RecyclerView) findViewById(R.id.rv_all);
        sfUtils  = new SPUtils(this);
        allData = sfUtils.getAllFunctionWithState();
        selectData = sfUtils.getSelectFunctionItem();

        menuSelectAdapter = new MenuSelectAdapter(this, selectData);
        rv_select.setLayoutManager(new GridLayoutManager(this, 4));
        rv_select.setAdapter(menuSelectAdapter);
        rv_select.addItemDecoration(new SpaceDecoration(4, dip2px(this, 1)));

        DefaultItemCallback callback = new DefaultItemCallback(menuSelectAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_select);

        GridLayoutManager gridManager = new GridLayoutManager(this, 4);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                GridItemBean fi = allData.get(position);
                return fi.status == 1 ? 1 : 4;
            }
        });

        menuAdapter = new MenuAdapter(this, allData);
        rv_all.setLayoutManager(gridManager);
        rv_all.setAdapter(menuAdapter);
        SpaceDecoration spaceDecoration = new SpaceDecoration(4, dip2px(this, 1));
        rv_all.addItemDecoration(spaceDecoration);

    }

    public void addListener() {
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfUtils.saveSelectFunctionItem(selectData);
                sfUtils.saveAllFunctionWithState(allData);
            }
        });
        menuAdapter.setOnItemAddListener(new MenuAdapter.OnItemAddListener() {
            @Override
            public boolean add(GridItemBean item) {
                if (selectData != null && selectData.size() < MAX_COUNT) {   // 更新选择列表，所有列表已在内部进行更新
                    try {
                        selectData.add(item);
                        menuSelectAdapter.notifyDataSetChanged();
                        item.isSelect = true;
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this,"选中的模块不能超过"+MAX_COUNT+"个",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        menuSelectAdapter.setOnItemRemoveListener(new MenuSelectAdapter.OnItemRemoveListener() {
            @Override
            public void remove(GridItemBean item) {
                // 更新所有列表，选择列表已在内部进行更新
                try {
                    if (item != null && item.Name != null) {
                        for (int i = 0; i < allData.size(); i++) {
                            GridItemBean data = allData.get(i);
                            if (data != null && data.Name != null) {
                                if (item.Name.equals(data.Name)) {
                                    data.isSelect = false;
                                    break;
                                }
                            }
                        }
                        menuAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
    }


    public  int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
