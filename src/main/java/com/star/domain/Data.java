package com.star.domain;


import java.sql.Timestamp;
/**
 * 照片存储信息
 */

public class Data {
    private int id;
    private String filename;
    private String hdfspath;
    private String cachepath;
    private double size;
    private Timestamp data;
    private int userid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHdfspath() {
        return hdfspath;
    }

    public void setHdfspath(String hdfspath) {
        this.hdfspath = hdfspath;
    }

    public String getCachepath() {
        return cachepath;
    }

    public void setCachepath(String cachepath) {
        this.cachepath = cachepath;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", hdfspath='" + hdfspath + '\'' +
                ", cachepath='" + cachepath + '\'' +
                ", size=" + size +
                ", data='" + data + '\'' +
                ", userid=" + userid +
                '}';
    }
}
