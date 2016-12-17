package com.example.administrator.demo_dianping.entity;


/**
 * Created by Administrator on 2016/12/14.
 */

public class Goods {

    private static final long serialVersionUID = 1L;

    private int id;//商品ID
    private String title;//商品名称
    private String sortTitle;//商品描述信息
    private int value;//商品原价
    private int price;//商品销售价
    private int soldOut;  //售出
    private String detail;//描述详情

    public Goods() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortTitle() {
        return sortTitle;
    }

    public void setSortTitle(String sortTitle) {
        this.sortTitle = sortTitle;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(int soldOut) {
        this.soldOut = soldOut;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
