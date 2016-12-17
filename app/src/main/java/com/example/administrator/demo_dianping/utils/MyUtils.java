package com.example.administrator.demo_dianping.utils;

import com.example.administrator.demo_dianping.R;

/**
 * Created by Administrator on 2016/12/7.
 */

public class MyUtils {

    //返回值
    public static final int RequestCityCode = 2;
    //主页GrideView信息
    public static String[] navsSort = {"美食", "电影","酒店", "KTV", "自助餐", "休闲娱乐",
            "旅游", "购物", "都市丽人", "母婴", "女装", "美妆", "户外运动", "生活服务", "全部"};
    //图标文件id
    public static int[] navsSortImages = {R.drawable.icon_home_food_99,R.drawable.icon_home_movie_29,
            R.drawable.icon_home_hotel_300,R.drawable.icon_home_ktv_31,R.drawable.icon_home_self_189,
            R.drawable.icon_home_happy_2,R.drawable.icon_home_flight_400,R.drawable.icon_home_shopping_3,
            R.drawable.icon_home_liren_442,R.drawable.icon_home_child_13,R.drawable.icon_home_nvzhuang_84,
            R.drawable.icon_home_meizhuang_173,R.drawable.icon_home_yundong_20,R.drawable.icon_home_life_46,
            R.drawable.icon_home_all_0};
    //list视图图标文件
    public static String [] allCategory = {"全部分类", "今日新单", "美食", "休闲娱乐",
            "电影", "生活服务", "写真生活", "酒店", "旅游", "都市丽人", "教育培训", "抽奖公益", "购物"};
    public static int[] allCategrayImages = {R.drawable.ic_all,R.drawable.ic_newest,
            R.drawable.ic_food,R.drawable.ic_entertain,R.drawable.ic_movie,R.drawable.ic_life,
            R.drawable.ic_photo,R.drawable.ic_hotel,R.drawable.ic_travel,R.drawable.ic_beauty,
            R.drawable.ic_edu,R.drawable.ic_luck,R.drawable.ic_shopping};
    public static long[] allCategoryNumber = new long [allCategrayImages.length + 5];
}
