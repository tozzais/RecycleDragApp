package com.example.recycledragapp;

import java.util.ArrayList;

public class GridItemBean {

    //后台返回
    public String name;
    public boolean isSelect = false;
    public String imageUrl = "";
    //0  是标题 1 gridview 2 小标题
    public int status = 1;


    private ArrayList<GridItemBean> allItems;
    private ArrayList<GridItemBean> selectItems = new ArrayList<>();

    public void setAllItems(ArrayList<GridItemBean> allItems) {
        this.allItems = allItems;
    }

    public void setSelectItems(ArrayList<GridItemBean> selectItems) {
        this.selectItems = selectItems;
    }

    public GridItemBean(String tabName, ArrayList<GridItemBean> functionItems) {
        this.name = tabName;
        this.allItems = functionItems;
    }

    public GridItemBean() {
    }

    public GridItemBean(String name, ArrayList<GridItemBean> allItems, ArrayList<GridItemBean> selectItems) {
        this.name = name;
        this.allItems = allItems;
        this.selectItems = selectItems;
    }

    public String getName() {
        return name;
    }

    public ArrayList<GridItemBean> getAllItems() {
        return allItems;
    }


    public ArrayList<GridItemBean> getSelectItems() {
        return selectItems;
    }

    public GridItemBean(String name, boolean isSelect, String imageUrl) {
        this.name = name;
        this.isSelect = isSelect;
        this.imageUrl = imageUrl;
        this.status = 1;
    }


    public GridItemBean(String name, int status){
        this.name = name;
        this.status = status;
    }

}
