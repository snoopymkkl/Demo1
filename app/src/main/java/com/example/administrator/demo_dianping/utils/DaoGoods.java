package com.example.administrator.demo_dianping.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.demo_dianping.entity.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class DaoGoods {

    private Context context;
    private SQLiteDatabase db;

    public DaoGoods(Context context) {
        this.context = context;
    }

    public List<Goods> queryGoods() {
        List<Goods> list = new ArrayList<>();
        DBHelperCity dbHelper = new DBHelperCity(context);
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from goods", null);
        while (cursor.moveToNext()) {
            Goods goods = new Goods();
            goods.setId(cursor.getInt(cursor.getColumnIndex("goods_id")));
            goods.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            goods.setSortTitle(cursor.getString(cursor.getColumnIndex("sortTitle")));
            goods.setValue(cursor.getInt(cursor.getColumnIndex("value")));
            goods.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            goods.setSoldOut(cursor.getInt(cursor.getColumnIndex("soldOut")));
            goods.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            list.add(goods);
        }
        dbHelper.close();
        db.close();
        cursor.close();
        return list;
    }

    public List<Goods> queryGoodsByPage(int page, int capacity){
        List<Goods> list = new ArrayList<>();
        DBHelperCity dbHelper = new DBHelperCity(context);
        db = dbHelper.getReadableDatabase();
        String sql = "select * from goods limit ?, ?";
        Cursor cursor = db.rawQuery(sql, new String[]{(page-1)*capacity+"", capacity + ""});
        while (cursor.moveToNext()) {
            Goods goods = new Goods();
            goods.setId(cursor.getInt(cursor.getColumnIndex("goods_id")));
            goods.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            goods.setSortTitle(cursor.getString(cursor.getColumnIndex("sortTitle")));
            goods.setValue(cursor.getInt(cursor.getColumnIndex("value")));
            goods.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            goods.setSoldOut(cursor.getInt(cursor.getColumnIndex("soldOut")));
            goods.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            list.add(goods);
        }
        dbHelper.close();
        db.close();
        cursor.close();
        return list;
    }
    //查询总共有多少条数据
    public long total(){
        DBHelperCity dbHelperCity = new DBHelperCity(context);
        SQLiteDatabase db = dbHelperCity.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from goods", null);
        long total = 0;
        while (cursor.moveToNext()){
            total = cursor.getLong(0);
        }
        dbHelperCity.close();
        cursor.close();
        db.close();
        return total;
    }

    public void addGoods(Goods goods) {
        DBHelperCity dbHelper = new DBHelperCity(context);
        db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", goods.getTitle());
        values.put("sortTitle", goods.getSortTitle());
        values.put("value", goods.getValue());
        values.put("price", goods.getPrice());
        values.put("soldOut", goods.getSoldOut());
        values.put("detail", goods.getDetail());
        db.insert("goods", null, values);
        dbHelper.close();
        db.close();
    }

    public void addDataToGoods() {
        for (int i = 0; i < 150; i++) {
            Goods good = new Goods();
            good.setTitle("团购商品" + i);
            good.setSortTitle("商品名称副标题" + i);
            good.setValue(i * 15);
            good.setPrice(i * 10);
            good.setSoldOut(i * 127);
            good.setDetail("这里是商品是详细描述, 记录了商品信息....................." + i);
            addGoods(good);
        }
    }
}
