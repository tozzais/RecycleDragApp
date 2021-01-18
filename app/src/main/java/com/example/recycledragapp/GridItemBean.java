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
    private ArrayList<GridItemBean> Item;
    //已选中的菜单
    private ArrayList<GridItemBean> selectItems = new ArrayList<>();


    public GridItemBean() {

    }

    public GridItemBean(String name){
        this.Name = name;
    }

    /**
     * 区分标题
     * @param name
     * @param status
     */
    public GridItemBean(String name, int status){
        this.Name = name;
        this.status = status;
    }


    /**
     * 添加本地图片使用
     * @param name
     * @param imageUrl
     */
    public GridItemBean(String name, String imageUrl) {
        this.Name = name;
        this.imageUrl = imageUrl;
        this.status = 1;
    }


    /**
     * 模拟数据使用
     * @param tabName
     * @param functionItems
     */
    public GridItemBean(String tabName, ArrayList<GridItemBean> functionItems) {
        this.Name = tabName;
        this.Item = functionItems;
    }


    public void setItem(ArrayList<GridItemBean> item) {
        this.Item = item;
    }

    public void setSelectItems(ArrayList<GridItemBean> selectItems) {
        this.selectItems = selectItems;
    }


    public String getName() {
        return Name;
    }


    public ArrayList<GridItemBean> getItem() {
        return Item;
    }


    public ArrayList<GridItemBean> getSelectItems() {
        return selectItems;
    }

    public boolean isSelectMenu(){
        return selectItems != null && selectItems.size()>0;
    }


}
