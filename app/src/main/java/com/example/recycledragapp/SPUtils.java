package com.example.recycledragapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xulc on 2018/6/29.
 */

public class SPUtils {
    private Context context;
    private SharedPreferences sp = null;

    public SPUtils(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("ceshi", Context.MODE_PRIVATE);
    }

    public List<GridItemBean> getAllFunctionWithState() {
        String allData = sp.getString("allData", "");
        List<GridItemBean> functionItems = new ArrayList<>();
        List<GridItemBean> tabItems = getAllData();
        if("".equals(allData)){
        for (int i = 0; i < tabItems.size(); i++) {
            GridItemBean functionItem1 = tabItems.get(i);
            //添加一级标题
            GridItemBean functionItem = new GridItemBean(functionItem1.getName(), 0);
            functionItems.add(functionItem);
            for (GridItemBean item : functionItem1.getAllItems() ) {
                if (item.getAllItems() != null){
                    //有三级 添加二级标题和数据
                    functionItems.add(new GridItemBean(item.getName(),2));
                    functionItems.addAll(item.getAllItems());
                }else {
                    functionItems.add(item);
                }
            }
        }}else{
            Gson gson = new Gson();
            functionItems =  gson.fromJson(allData,new TypeToken<List<GridItemBean>>(){}.getType());
        }
        return functionItems;
    }

    public List<GridItemBean> getData() {
        String allData = sp.getString("allData", "");
        List<GridItemBean> functionItems = new ArrayList<>();
        if("".equals(allData)){
            functionItems = getAllData();
        }else{
            Gson gson = new Gson();
            functionItems =  gson.fromJson(allData,new TypeToken<List<GridItemBean>>(){}.getType());
        }
        return functionItems;
    }

    public List<GridItemBean> getSelectFunctionItem() {
        String selData = sp.getString("selData", "");
        List<GridItemBean> functionItems = null;
        if ("".equals(selData)) {
            functionItems = new ArrayList<>();
        } else {
            Gson gson = new Gson();
            functionItems = gson.fromJson(selData, new TypeToken<List<GridItemBean>>() {
            }.getType());
        }
        return functionItems;
    }

    public List<GridItemBean> getAllData() {
        List<GridItemBean> tabItems = new ArrayList<>();
        ArrayList<GridItemBean> list1 = new ArrayList<>();

        list1.add(new GridItemBean("工作台"));
        list1.add(new GridItemBean("代办事项", false, "icon_home_selected"));
        list1.add(new GridItemBean("服务请求", false, "icon_home_selected"));
        list1.add(new GridItemBean("我的客户", false, "icon_home_selected"));
        list1.add(new GridItemBean("服务台"));
        list1.add(new GridItemBean("服务请求管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("在线咨询", false, "icon_home_selected"));
        list1.add(new GridItemBean("留言管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("任务分配", false, "icon_home_selected"));
        list1.add(new GridItemBean("运维管理"));
        list1.add(new GridItemBean("事件管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("问题管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("变更管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("交付管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("发布管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("安全管理", false, "icon_home_selected"));
        list1.add(new GridItemBean("服务报告", false, "icon_home_selected"));

        //添加运维管理
        tabItems.add(new GridItemBean("运维管理", list1));

//        ArrayList<GridItemBean> list2 = new ArrayList<>();
//        list2.add(new GridItemBean("堤防监控", false, "icon_home_selected"));
//        list2.add(new GridItemBean("网络监测", false, "icon_home_selected"));
//        list2.add(new GridItemBean("水文监测", false, "icon_home_selected"));
//        list2.add(new GridItemBean("视频监控", false, "icon_home_selected"));
//        list2.add(new GridItemBean("信息消息", false, "icon_home_selected"));
//        list2.add(new GridItemBean("统计报表", false, "icon_home_selected"));
//        tabItems.add(new GridItemBean("信息监控", list2));
        ArrayList<GridItemBean> list3 = new ArrayList<>();
        list3.add(new GridItemBean("计划任务", false, "icon_home_selected"));
        tabItems.add(new GridItemBean("巡查巡检", list3));
        ArrayList<GridItemBean> list4 = new ArrayList<>();
        list4.add(new GridItemBean("设施设备", false, "icon_home_selected"));
        list4.add(new GridItemBean("沿线单位", false, "icon_home_selected"));
        tabItems.add(new GridItemBean("设施设备", list4));
//        ArrayList<GridItemBean> list5 = new ArrayList<>();
//        list5.add(new GridItemBean("工程项目", false, "icon_home_selected"));
//        list5.add(new GridItemBean("改造岸段", false, "icon_home_selected"));
//        list5.add(new GridItemBean("实施单位", false, "icon_home_selected"));
//        list5.add(new GridItemBean("堤防改造", false, "icon_home_selected"));
//        list5.add(new GridItemBean("项目监督", false, "icon_home_selected"));
//        list5.add(new GridItemBean("统计报表", false, "icon_home_selected"));
//        tabItems.add(new GridItemBean("工程档案", list5));
//        ArrayList<GridItemBean> list6 = new ArrayList<>();
//        list6.add(new GridItemBean("应急预案", false, "icon_home_selected"));
//        list6.add(new GridItemBean("监测预警", false, "icon_home_selected"));
//        list6.add(new GridItemBean("潮位预报", false, "icon_home_selected"));
//        list6.add(new GridItemBean("台风气象", false, "icon_home_selected"));
//        list6.add(new GridItemBean("险情上报", false, "icon_home_selected"));
//        list6.add(new GridItemBean("发布预警", false, "icon_home_selected"));
//        list6.add(new GridItemBean("防险确认", false, "icon_home_selected"));
//        list6.add(new GridItemBean("险情受理", false, "icon_home_selected"));
//        list6.add(new GridItemBean("组织抢险", false, "icon_home_selected"));
//        list6.add(new GridItemBean("险情处置", false, "icon_home_selected"));
//        list6.add(new GridItemBean("险情结案", false, "icon_home_selected"));
//        list6.add(new GridItemBean("防汛演练", false, "icon_home_selected"));
//        list6.add(new GridItemBean("抢险查询", false, "icon_home_selected"));
//        list6.add(new GridItemBean("统计报表", false, "icon_home_selected"));
//        tabItems.add(new GridItemBean("防汛抢险", list6));
        return tabItems;
    }


    public void saveSelectFunctionItem(List<GridItemBean> selData) {
        Gson gson = new Gson();
        sp.edit().putString("selData", gson.toJson(selData)).apply();
    }

    public void saveAllFunctionWithState(List<GridItemBean> allData) {
        Gson gson = new Gson();
        Log.e("TAG", gson.toJson(allData));
        sp.edit().putString("allData", gson.toJson(allData)).apply();
    }

}
