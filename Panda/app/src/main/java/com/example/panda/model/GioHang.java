package com.example.panda.model;

public class GioHang {
    private int magh;
    private int mamon;
    private String mand;
    private String tenmon;
    private String hinh;
    private int gia;
    private int soluong;



    public GioHang(int magh, int mamon, String mand, String tenmon, String hinh, int gia, int soluong) {
        this.magh = magh;
        this.mamon = mamon;
        this.mand = mand;
        this.tenmon = tenmon;
        this.hinh = hinh;
        this.gia = gia;
        this.soluong = soluong;
    }

    public GioHang(int mamon, String mand, int soluong) {
        this.mamon = mamon;
        this.mand = mand;
        this.soluong = soluong;
    }

    public String getMand() {
        return mand;
    }

    public void setMand(String mand) {
        this.mand = mand;
    }

    public int getMagh() {
        return magh;
    }

    public void setMagh(int magh) {
        this.magh = magh;
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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
