package com.example.recycledragapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<GridItemBean> getShowData(){
        String allData = sp.getString(data_cache, "");
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
            if (s1.equals(s2)){
                return localDates;
            }else {
                saveMenuData(netDates);
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
        char[] chars = s1.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
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
        list6.add(new GridItemBean("统计报表"));
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
                    list02.setItem(null);
                }else {
                    //两级菜单
                    list02.imageUrl = "icon_home_selected";
                }
            }
        }
        return tabItems;
    }


    public void saveMenuData(List<GridItemBean> allData) {
        Gson gson = new Gson();
        sp.edit().putString(data_cache, gson.toJson(allData)).apply();
    }

}
