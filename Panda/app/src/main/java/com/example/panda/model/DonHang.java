package com.example.panda.model;

public class DonHang {
    private int madh;
    private int makh;
    private String mand;
    private String hoten;
    private String sdt;
    private String diachi;
    private int tongsoluong;
    private String ngay;
    private int trangthai;
    private int tien;

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public DonHang(int madh, int makh, String mand, String hoten, String sdt, String diachi, int tongsoluong, String ngay, int trangthai, int tien) {
        this.madh = madh;
        this.makh = makh;
        this.mand = mand;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
        this.tongsoluong = tongsoluong;
        this.ngay = ngay;
        this.trangthai = trangthai;
        this.tien = tien;
    }

    public DonHang(String mand, int tongsoluong, int tien, String ngay, int trangthai) {
        this.mand = mand;
        this.tongsoluong = tongsoluong;
        this.tien = tien;
        this.ngay = ngay;
        this.trangthai = trangthai;
    }

    public DonHang(String ngay, int tien) {
        this.ngay = ngay;
        this.tien = tien;
    }

    public String getMand() {
        return mand;
    }

    public void setMand(String mand) {
        this.mand = mand;
    }

    public int getMadh() {
        return madh;
    }

    public void setMadh(int madh) {
        this.madh = madh;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getTongsoluong() {
        return tongsoluong;
    }

    public void setTongsoluong(int tongsoluong) {
        this.tongsoluong = tongsoluong;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
