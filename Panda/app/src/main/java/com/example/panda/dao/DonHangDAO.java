package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.http.SslCertificate;

import com.example.panda.database.DbHelper;
import com.example.panda.model.DonHang;

import java.util.ArrayList;

public class DonHangDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences ;
    public DonHangDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("PANDA", Context.MODE_PRIVATE);
    }

    //lay ds don hang
    public ArrayList<DonHang> getDSDonHang(){
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dh.madh, kh.makh, nd.mand, nd.hoten, kh.sdt, kh.diachi, dh.tongsoluong, dh.ngay, dh.trangthai, dh.tien FROM DONHANG dh, THONGTINKH kh, NGUOIDUNG nd WHERE dh.mand = nd.mand AND nd.mand = kh.mand", null);

        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new DonHang(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<DonHang> getDSDonHangTheokh(String mand, int trangthai){
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dh.madh, kh.makh, nd.mand, nd.hoten, kh.sdt, kh.diachi, dh.tongsoluong, dh.ngay, dh.trangthai, dh.tien FROM DONHANG dh, THONGTINKH kh, NGUOIDUNG nd WHERE dh.mand = nd.mand AND nd.mand = kh.mand", null);

        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                String ma = cursor.getString(2);
                int tt = cursor.getInt(8);
                if (ma.equals(mand) && tt != trangthai) {
                    list.add(new DonHang(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("madh", cursor.getInt(0));
                    editor.commit();
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<DonHang> getDSLichsu(String mand, int trangthai){
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dh.madh, kh.makh, nd.mand, nd.hoten, kh.sdt, kh.diachi, dh.tongsoluong, dh.ngay, dh.trangthai, dh.tien FROM DONHANG dh, THONGTINKH kh, NGUOIDUNG nd WHERE dh.mand = nd.mand AND nd.mand = kh.mand", null);

        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                String ma = cursor.getString(2);
                int tt = cursor.getInt(8);
                if (ma.equals(mand) && tt == trangthai) {
                    list.add(new DonHang(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    // them
    public boolean themDonnHang(DonHang donHang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", donHang.getMand());
        contentValues.put("tongsoluong", donHang.getTongsoluong());
        contentValues.put("ngay", donHang.getNgay());
        contentValues.put("tien",donHang.getTien());
        contentValues.put("trangthai", donHang.getTrangthai());
        long check = sqLiteDatabase.insert("DONHANG",null,contentValues);

        if (check == -1)
            return false;
        return true;
    }

    // doi trang thai
    public boolean trangThai(int madh, int index){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", index);
        long check = sqLiteDatabase.update("DONHANG", contentValues,"madh = ?", new String[]{String.valueOf(madh)});

        if (check == -1)
            return false;
        return true;
    }

    // hủy đơn hàng
    public boolean xoaDonHang(int madh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("DONHANG", "madh = ?", new String[]{String.valueOf(madh)});

        if (check == -1)
            return false;
        return true;
    }

    // doanh thu
    public int getDoanhThu(String ngaybd, String ngaykt, int trangthai){
        ngaybd = ngaybd.replace("/","");
        ngaykt = ngaykt.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT tien,trangthai FROM DONHANG WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) between ? and ?",new String[]{ngaybd,ngaykt});
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            int tien = 0;
            do {
                int tt = cursor.getInt(1);
                if (tt == trangthai){
                    tien = tien + cursor.getInt(0);
                }
            }while (cursor.moveToNext());
            return tien;
        }
        return 0;
    }

    public ArrayList<DonHang> getDoanhThuBieuDo(String ngaybd, String ngaykt, int trangthai){
        ngaybd = ngaybd.replace("/","");
        ngaykt = ngaykt.replace("/","");
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ngay , tien,trangthai FROM DONHANG WHERE substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) between ? and ?",new String[]{ngaybd,ngaykt});
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            int tien = 0;
            String ngay = cursor.getString(0);
            do {
                int tt = cursor.getInt(2);
                String ngay1 = cursor.getString(0);
                if (tt == trangthai){
                    if (ngay.equals(ngay1)){
                        tien = tien + cursor.getInt(1);
                    }else {
                        tien = cursor.getInt(1);
                    }
                    list.add(new DonHang(ngay1,tien));
                    ngay = cursor.getString(0);
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

}















