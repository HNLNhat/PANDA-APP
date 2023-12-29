package com.example.panda.model;


public class ThongTinKH {
    private int makh;
    private String madn;
    private String hoten;
    private String sdt;
    private String diachi;

    public ThongTinKH(int makh, String madn, String hoten, String sdt, String diachi) {
        this.makh = makh;
        this.madn = madn;
        this.hoten = hoten;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public ThongTinKH(String madn, String sdt, String diachi) {
        this.madn = madn;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public ThongTinKH(int makh, String madn, String sdt, String diachi) {
        this.makh = makh;
        this.madn = madn;
        this.sdt = sdt;
        this.diachi = diachi;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getMadn() {
        return madn;
    }

    public void setMadn(String madn) {
        this.madn = madn;
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

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public ThongTinKH() {
    }
}
