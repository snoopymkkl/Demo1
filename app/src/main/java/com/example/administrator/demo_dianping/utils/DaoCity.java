package com.example.administrator.demo_dianping.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrator.demo_dianping.entity.City;

import java.util.ArrayList;
import java.util.List;

/**
 * 对city表中的数据进行操作
 */

public class DaoCity {

    private Context context;
    private SQLiteDatabase db;

    public DaoCity(Context context) {
        this.context = context;
    }

    /**
     * 获取City表中所有的数据
     */
    public List<City> queryCity() {
        DBHelperCity dbHelperCity = new DBHelperCity(context);
        db = dbHelperCity.getReadableDatabase();
        List<City> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from city", null);
        while (cursor.moveToNext()) {
            String cityName = cursor.getString(cursor.getColumnIndex("cityName"));
            String citySortKey = cursor.getString(cursor.getColumnIndex("citySortkey"));
            City city = new City(cityName, citySortKey);
            list.add(city);
        }
        //关闭数据库, 游标
        dbHelperCity.close();
        db.close();
        cursor.close();
        return list;
    }

    /**
     * 向city表中添加数据
     */
    public void add(City city) {
        DBHelperCity dbHelperCity = new DBHelperCity(context);
        db = dbHelperCity.getReadableDatabase();
        //通过contentValue放入字段
        ContentValues contentValues = new ContentValues();
        contentValues.put("cityName", city.getName());
        contentValues.put("citySortkey", city.getSortKey());

        db.insert("city", null, contentValues);
        dbHelperCity.close();
        db.close();
    }

//    public void delete(City city)

    /**此方法将具体数据添加到city表中, 只能调用一次, 否则会出现数据混乱*/
    public void addCities() {

        for (int i = 0; i < 10; i++) {
            String cityKey = "A";
            String cityName = "安阳" + i;
            City city = new City(cityName, cityKey);
            add(city);
        }

        for (int i = 0; i < 10; i++) {
            String cityKey = "B";
            String cityName = "北京" + i;
            City city = new City(cityName, cityKey);
            add(city);
        }
        for (int i = 0; i < 10; i++) {
            String cityKey = "C";
            String cityName = "崇州" + i;
            City city = new City(cityName, cityKey);
            add(city);
        }

        for (int i = 0; i < 10; i++) {
            String cityKey = "D";
            String cityName = "东京" + i;
            City city = new City(cityName, cityKey);
            add(city);
        }

    }
}

