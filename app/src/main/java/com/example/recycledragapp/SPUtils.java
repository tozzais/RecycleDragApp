package com.example.recycledragapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by xulc on 2018/6/29.
 */

public class SPUtils {
    private final String menu_name = "sp_menu";
    private final String data_cache = "allData";

    private SharedPreferences sp;
    public SPUtils(Context context) {
        sp = context.getSharedPreferences(menu_name, Context.MODE_PRIVATE);
    }

    public List<GridItemBean> getAllFunctionWithState() {
        String allData = sp.getString("allData", "");
        List<GridItemBean> functionItems = new ArrayList<>();
        List<GridItemBean> tabItems = getNetData();
        if("".equals(allData)){
        for (int i = 0; i < tabItems.size(); i++) {
            GridItemBean functionItem1 = tabItems.get(i);
            //添加一级标题
            GridItemBean functionItem = new GridItemBean(functionItem1.getName(), 0);
            functionItems.add(functionItem);
            for (GridItemBean item : functionItem1.getItem() ) {
                if (item.getItem() != null){
                    //有三级 添加二级标题和数据
                    functionItems.add(new GridItemBean(item.getName(),2));
                    functionItems.addAll(item.getItem());
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
        String allData = sp.getString(data_cache, "");
        Log.e("存储的数据",allData);
        List<GridItemBean> functionItems = new ArrayList<>();
        if("".equals(allData)){
            functionItems = getNetData();
        }else{
            Gson gson = new Gson();
            functionItems =  gson.fromJson(allData,new TypeToken<List<GridItemBean>>(){}.getType());
        }
        return functionItems;
    }



    public List<GridItemBean> getShowData(){
        String allData = sp.getString(data_cache, "");
        Log.e("存储的数据",allData);
        List<GridItemBean> localDates;
        //如果本地没有 整理数据
        List<GridItemBean> netDates = getNetData();
        if("".equals(allData)){
            return netDates;
        }else{
            //有数据的话 解析
            Gson gson = new Gson();
            localDates =  gson.fromJson(allData,new TypeToken<List<GridItemBean>>(){}.getType());
            // 比对
            String s1 = getLabel(localDates);
            String s2 = getLabel(netDates);
            Log.e("存储的数据","s1="+s1+"\ns2="+s2);
            if (s1.equals(s2)){
                return localDates;
            }else {
                return netDates;
            }
        }
    }

    private String getLabel(List<GridItemBean> mDates){
        String s1 = "";
        Queue<GridItemBean> queue = new LinkedList<>();
        for (GridItemBean gridItemBean:mDates){
            queue.add(gridItemBean);
        }
        while (!queue.isEmpty()){
            GridItemBean poll = queue.poll();
            s1 = s1+poll.getName();
            ArrayList<GridItemBean> item = poll.getItem();
            if (item != null && item.size()>0){
                for (GridItemBean gridItemBean:item){
                    queue.add(gridItemBean);
                }
            }
        }
        return s1;
    }


    public List<GridItemBean> getNetData() {
        List<GridItemBean> tabItems = new ArrayList<>();
        ArrayList<GridItemBean> list1 = new ArrayList<>();
        ArrayList<GridItemBean> list11 = new ArrayList<>();
        list11.add(new GridItemBean("事件管理"));
        list11.add(new GridItemBean("问题管理"));
        list11.add(new GridItemBean("变更管理"));
        list11.add(new GridItemBean("交付管理"));
        list11.add(new GridItemBean("发布管理"));
        list11.add(new GridItemBean("安全管理"));
        list11.add(new GridItemBean("服务报告"));
        list1.add(new GridItemBean("运维管理",list11));
        //添加运维管理
        tabItems.add(new GridItemBean("运维管理子系统", list1));
        ArrayList<GridItemBean> list2 = new ArrayList<>();
        list2.add(new GridItemBean("堤防监控"));
        list2.add(new GridItemBean("网络监测"));
        list2.add(new GridItemBean("水文监测"));
        list2.add(new GridItemBean("视频监控"));
        list2.add(new GridItemBean("信息消息"));
        list2.add(new GridItemBean("统计报表"));
        tabItems.add(new GridItemBean("信息监控子系统", list2));
        ArrayList<GridItemBean> list3 = new ArrayList<>();
        list3.add(new GridItemBean("计划任务"));
        tabItems.add(new GridItemBean("巡查巡检子系统", list3));
        ArrayList<GridItemBean> list4 = new ArrayList<>();
        list4.add(new GridItemBean("设施设备"));
        list4.add(new GridItemBean("沿线单位"));
        tabItems.add(new GridItemBean("设施设备子系统", list4));
        ArrayList<GridItemBean> list5 = new ArrayList<>();
        list5.add(new GridItemBean("工程项目"));
        list5.add(new GridItemBean("改造岸段"));
        list5.add(new GridItemBean("实施单位"));
        list5.add(new GridItemBean("堤防改造"));
        list5.add(new GridItemBean("项目监督"));
        list5.add(new GridItemBean("统计报表"));
        tabItems.add(new GridItemBean("工程档案子系统", list5));
        ArrayList<GridItemBean> list6 = new ArrayList<>();
        list6.add(new GridItemBean("应急预案"));
        list6.add(new GridItemBean("监测预警"));
        list6.add(new GridItemBean("潮位预报"));
        list6.add(new GridItemBean("台风气象"));
        list6.add(new GridItemBean("险情上报"));
        list6.add(new GridItemBean("发布预警"));
        list6.add(new GridItemBean("防险确认"));
        list6.add(new GridItemBean("险情受理"));
        list6.add(new GridItemBean("组织抢险"));
        list6.add(new GridItemBean("险情处置"));
        list6.add(new GridItemBean("险情结案"));
        list6.add(new GridItemBean("防汛演练"));
        list6.add(new GridItemBean("抢险查询"));
        list6.add(new GridItemBean("统计报表"));
        tabItems.add(new GridItemBean("防汛抢险子系统", list6));
        //数据清洗
        for (GridItemBean list01:tabItems) {
            //标题
            list01.status = 0;
            for (GridItemBean list02 : list01.getItem() ) {

                ArrayList<GridItemBean> list03 = list02.getItem();
                if (list03 != null && list03.size()>0){
                    //三级菜单
                    list02.status = 2;

                    for (GridItemBean g:list03) {
                        //功能页
                        g.imageUrl = "icon_home_selected";
                        g.status = 1;
                    }
                    list01.getItem().addAll(list03);
                }else {
                    //两级菜单
                    list02.imageUrl = "icon_home_selected";
                    list02.status = 1;
                }
            }
        }
        return tabItems;
    }


    public void saveAllFunctionWithState(List<GridItemBean> allData) {
        Gson gson = new Gson();
        Log.e("TAG", gson.toJson(allData));
        sp.edit().putString("allData", gson.toJson(allData)).apply();
    }

}
