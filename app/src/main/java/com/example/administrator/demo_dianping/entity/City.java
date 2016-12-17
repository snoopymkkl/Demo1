package com.example.administrator.demo_dianping.entity;

/**
 * City表的实例类
 */

public class City {

    private String _id;
    private String name;
    private String sortKey;

    public City() {
    }

    public City(String _id, String name, String sortKey) {
        this._id = _id;
        this.name = name;
        this.sortKey = sortKey;
    }

    public City(String name, String sortKey) {
        this.name = name;
        this.sortKey = sortKey;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }
}
