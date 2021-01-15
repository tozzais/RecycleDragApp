package com.example.recycledragapp;

import java.util.ArrayList;

public class GridItemBean {


    public boolean isSelect = false;

    //后台返回
    public String Name;

    public String imageUrl = "";
    //0  是标题 1 gridview 2 小标题
    public int status = 1;

    //全部的菜单（目前只有两级 和三级）
    private ArrayList<GridItemBean> allItems;
    //已选中的菜单
    private ArrayList<GridItemBean> selectItems = new ArrayList<>();


    public GridItemBean() {
    }

    public GridItemBean(String name){
        this.Name = name;
        this.status = 2;
    }

    public GridItemBean(String name, int status){
        this.Name = name;
        this.status = status;
    }


    public GridItemBean(String name, boolean isSelect, String imageUrl) {
        this.Name = name;
        this.isSelect = isSelect;
        this.imageUrl = imageUrl;
        this.status = 1;
    }


    public GridItemBean(String tabName, ArrayList<GridItemBean> functionItems) {
        this.Name = tabName;
        this.allItems = functionItems;
    }


    public void setAllItems(ArrayList<GridItemBean> allItems) {
        this.allItems = allItems;
    }

    public void setSelectItems(ArrayList<GridItemBean> selectItems) {
        this.selectItems = selectItems;
    }


    public String getName() {
        return Name;
    }


    public ArrayList<GridItemBean> getAllItems() {
        return allItems;
    }


    public ArrayList<GridItemBean> getSelectItems() {
        return selectItems;
    }


}
