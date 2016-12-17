package com.example.administrator.demo_dianping.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.demo_dianping.entity.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * category表的操作类
 */

public class DaoCategory {

    private Context context;
    private SQLiteDatabase db;

    public DaoCategory(Context context){
        this.context = context;
    }

    //查询表中所有信息
    public List<Category> queryCategory(){
        List<Category> list = new ArrayList<>();
        DBHelperCity dbHelperCity = new DBHelperCity(context);
        SQLiteDatabase db = dbHelperCity.getReadableDatabase();
        String sql = "select * from category";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int categoryId = cursor.getInt(cursor.getColumnIndex("categoryId"));
            int categoryNumber = cursor.getInt(cursor.getColumnIndex("categoryNumber"));
            Category category = new Category(categoryId, categoryNumber);
            list.add(category);
        }
        dbHelperCity.close();
        db.close();
        cursor.close();
        return list;
    }

    //添加数据到catalog表中方法
    public void addCategory(Category category){
        DBHelperCity dbHelperCity = new DBHelperCity(context);
        db =  dbHelperCity.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryId", category.getCategoryId());
        contentValues.put("categoryNumber", category.getCateNumber());
        db.insert("category", null, contentValues);
        db.close();
        dbHelperCity.close();
    }
    //添加具体数据
    public void addDataToCatagory(){
        for (int i = 1; i < 150; i++) {
            Category category = new Category(i, i*100);
            addCategory(category);
        }
    }
}
