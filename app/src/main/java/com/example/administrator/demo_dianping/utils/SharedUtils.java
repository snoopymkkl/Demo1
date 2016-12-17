package com.example.administrator.demo_dianping.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.ConnectException;

/**
 * 保存应用的数据
 *
 *      是否是第一次进入应用
 *      保存导航获取的城市名称
 *
 * 实现标记的写入与读取, 判断是否是第一次进入应用
 * 第一次进入时, 调用getWelcomeBoolean(), 判断是不是true, 默认返回false, 即不是第一次进入
 *               继续调用putWelcomeBoolean(), 之后启动导航页面, 将SharedPreferences value改为true 以后不再启动
 */

public class SharedUtils {
    private static final String FILE_NAME = "dianping" ;
    private static final String MODE_NAME = "welcome";
    //获取Boolean类型的值
    public static boolean getWelcomeBoolean(Context context){
        //从Context中获取SharedPreferences对象
        //SharedPreferences对象value设置默认为false(标记)
       return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, false);
    }
    //写入Boolean类型的值
    public static void putWelcomeBoolean(Context context, boolean isFirst){
        //获取到Editor对象
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putBoolean(MODE_NAME, isFirst);
        editor.commit();
    }

    //写入一个String类型的数据
    public static void putCityName(Context context, String cityName){
        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
        editor.putString("cityName", cityName);
        editor.commit();
        Log.e("SharedUtils", "putCityName" + cityName);

    }
    //获取String类型值
    public static String getCityName(Context context){
        Log.e("SharedUtils", "getCityName");
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("cityName", "选择城市");
    }


}
