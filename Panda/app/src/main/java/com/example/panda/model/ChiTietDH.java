package com.example.panda.model;

public class ChiTietDH {
    private int mactdh;
    private int madh;
    private int mamon;
    private String tenmon;
    private int soluong;
    private String anh;
    private int tien;
    private int trangthai;

    public ChiTietDH(int mactdh, int madh, int mamon, String tenmon, int soluong, String anh, int tien, int trangthai) {
        this.mactdh = mactdh;
        this.madh = madh;
        this.mamon = mamon;
        this.tenmon = tenmon;
        this.soluong = soluong;
        this.anh = anh;
        this.tien = tien;
        this.trangthai =  trangthai;
    }

    public ChiTietDH(int madh, int mamon, int soluong, int tien) {
        this.madh = madh;
        this.mamon = mamon;
        this.soluong = soluong;
        this.tien = tien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getMactdh() {
        return mactdh;
    }

    public void setMactdh(int mactdh) {
        this.mactdh = mactdh;
    }

    public int getMadh() {
        return madh;
    }

    public void setMadh(int madh) {
        this.madh = madh;
    }

    public int getMamon() {
        return mamon;
    }

    public void setMamon(int mamon) {
        this.mamon = mamon;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
