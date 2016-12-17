package com.example.administrator.demo_dianping.entity;

/**
 * 商品目录, home中获取数据的表
 */

public class Category {

    private int categoryId; //分类ID
    private long cateNumber; //具体的数量

    public Category(int categoryId, long cateNumber) {
        this.categoryId = categoryId;
        this.cateNumber = cateNumber;
    }

    public long getCateNumber() {
        return cateNumber;
    }

    public void setCateNumber(long cateNumber) {
        this.cateNumber = cateNumber;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return  categoryId+" -->" + cateNumber;
    }
}
