package com.example.panda.model;

public class DanhGia {
    private int madg;
    private String mand;
    private String hoten;
    private String ngay;
    private int mamon;
    private String danggia;

    public DanhGia(int madg, String mand, String hoten, String ngay, int mamon, String danggia) {
        this.madg = madg;
        this.mand = mand;
        this.hoten = hoten;
        this.ngay = ngay;
        this.mamon = mamon;
        this.danggia = danggia;
    }

    public DanhGia(String mand, String ngay, int mamon, String danggia) {
        this.mand = mand;
        this.ngay = ngay;
        this.mamon = mamon;
        this.danggia = danggia;
    }

    public int getMadg() {
        return madg;
    }

    public void setMadg(int madg) {
        this.madg = madg;
    }

    public String getMand() {
        return mand;
    }

    public void setMand(String mand) {
        this.mand = mand;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getMamon() {
        return mamon;
    }

    public void setMamon(int mamon) {
        this.mamon = mamon;
    }

    public String getDanggia() {
        return danggia;
    }

    public void setDanggia(String danggia) {
        this.danggia = danggia;
    }
}
