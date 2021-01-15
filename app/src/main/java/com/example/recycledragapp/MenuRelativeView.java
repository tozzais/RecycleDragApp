package com.example.recycledragapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuRelativeView extends RelativeLayout {

    private Context context;

    private RecyclerView rv_select, rv_all;
    private TextView tv_name;
    private MenuSelectAdapter menuSelectAdapter;
    private MenuAdapter menuAdapter;



    private static final int MAX_COUNT = 12;

    public MenuRelativeView(Context context) {
        super(context);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MenuRelativeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

//    private List<GridItemBean> allData = new ArrayList<>();
//    private List<GridItemBean> selectData = new ArrayList<>();

    private GridItemBean itemBean;

    public void setData(GridItemBean itemBean) {

        ArrayList<GridItemBean> allItems = this.itemBean.getAllItems();
        allItems.clear();
        allItems.addAll(itemBean.getAllItems());

        ArrayList<GridItemBean> selectItems = this.itemBean.getSelectItems();
        selectItems.clear();
        selectItems.addAll(itemBean.getSelectItems());

        tv_name.setText(itemBean.name);
        menuSelectAdapter.notifyDataSetChanged();
        menuAdapter.notifyDataSetChanged();

    }

    public  int dip2px(float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void initView(Context context) {
        this.context = context;
        itemBean = new GridItemBean();
        itemBean.setAllItems(new ArrayList<GridItemBean>());
        itemBean.setSelectItems(new ArrayList<GridItemBean>());

        View view = View.inflate(context, R.layout.view_menu, null);
        rv_select = (RecyclerView) view.findViewById(R.id.rv_select);
        rv_all = (RecyclerView) view.findViewById(R.id.rv_all);
        tv_name = (TextView) view.findViewById(R.id.tv_name);

        menuSelectAdapter = new MenuSelectAdapter(context, itemBean.getSelectItems());
        rv_select.setLayoutManager(new GridLayoutManager(context, 4));
        rv_select.setAdapter(menuSelectAdapter);
        rv_select.addItemDecoration(new SpaceDecoration(4, dip2px( 1)));

        DefaultItemCallback callback = new DefaultItemCallback(menuSelectAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_select);

        GridLayoutManager gridManager = new GridLayoutManager(context, 4);
        gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                GridItemBean fi = itemBean.getAllItems().get(position);
                return fi.status == 1 ? 1 : 4;
            }
        });

        menuAdapter = new MenuAdapter(context,  itemBean.getAllItems());
        rv_all.setLayoutManager(gridManager);
        rv_all.setAdapter(menuAdapter);
        SpaceDecoration spaceDecoration = new SpaceDecoration(4, dip2px( 1));
        rv_all.addItemDecoration(spaceDecoration);

        addListener();
        addView(view);
    }

    public void addListener() {
        menuAdapter.setOnItemAddListener(new MenuAdapter.OnItemAddListener() {
            @Override
            public boolean add(GridItemBean item) {
                if ( itemBean.getSelectItems() != null && itemBean.getSelectItems().size() < MAX_COUNT) {   // 更新选择列表，所有列表已在内部进行更新
                    try {
                        itemBean.getSelectItems().add(item);
                        menuSelectAdapter.notifyDataSetChanged();
                        item.isSelect = true;
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context,"最多选"+MAX_COUNT+"个",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        menuSelectAdapter.setOnItemRemoveListener(new MenuSelectAdapter.OnItemRemoveListener() {
            @Override
            public void remove(GridItemBean item) {
                // 更新所有列表，选择列表已在内部进行更新
                try {
                    if (item != null && item.name != null) {
                        for (int i = 0; i <  itemBean.getAllItems().size(); i++) {
                            GridItemBean data =  itemBean.getAllItems().get(i);
                            if (data != null && data.name != null) {
                                if (item.name.equals(data.name)) {
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

}
