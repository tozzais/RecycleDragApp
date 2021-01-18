package com.example.recycledragapp;

import java.util.ArrayList;
import java.util.List;

/**
 * 既是 一级菜单 也是功能页
 */
public class GridItemBean {


    public boolean isSelect = false;

    //后台返回
    public String Name;

    public String imageUrl = "";
    //0  是标题 1 工程 2 小标题
    public int status = 1;

    //全部的菜单（目前只有两级 和三级）
    private List<GridItemBean> Item;
    //已选中的菜单
    private List<GridItemBean> selectItems = new ArrayList<>();


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


    public void setItem(List<GridItemBean> item) {
        this.Item = item;
    }

    public void setSelectItems(List<GridItemBean> selectItems) {
        this.selectItems = selectItems;
    }


    public String getName() {
        return Name;
    }


    public List<GridItemBean> getItem() {
        return Item;
    }


    public List<GridItemBean> getSelectItems() {
        return selectItems;
    }

    /**
     * 是否有选择的功能页
     * @return
     */
    public boolean isSelectMenu(){
        return selectItems != null && selectItems.size()>0;
    }


}
