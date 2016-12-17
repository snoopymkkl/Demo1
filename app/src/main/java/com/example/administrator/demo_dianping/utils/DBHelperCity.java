package com.example.administrator.demo_dianping.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

/**
 * 本类用于创建City表, 储存城市信息
 * 包含字段
 *         city_id int
 *         city_name varchar
 *         city_sortkey varchar
 *
 */

public class DBHelperCity extends SQLiteOpenHelper{


    public static final String CREATEGOODSTABLE = "create table goods(" +
            "goods_id integer primary key autoincrement," +
            "title varchar," +
            "sortTitle varchar," +
            "value int," +
            "price int," +
            "soldOut int," +
            "detail varchar)";

    private static final String ADDCATEGORYTABLE =
            "create table category(" +
                    "categoryId integer primary key autoincrement," +
                    "categoryNumber varchar)";

    public DBHelperCity(Context context) {
        super(context, "dianping.db", null, 3);
    }

    /**注意,此方法只有在添加数据到表中时,才会调用!!!
     * 创建数据库city表, 保存城市信息*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table city (" +
                "city_id integer primary key autoincrement," +
                "cityName varchar," +
                "citySortkey varchar)";
        db.execSQL(sql);
        Log.e("Tag", "onCreate" );
    }

    /**
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){
            //添加category表
            case 1:
                db.execSQL(ADDCATEGORYTABLE);
            //添加goods表
            case 2:
                db.execSQL(CREATEGOODSTABLE);
                Log.e("Tag", "----------goods表创建了" );
        }
    }
}
