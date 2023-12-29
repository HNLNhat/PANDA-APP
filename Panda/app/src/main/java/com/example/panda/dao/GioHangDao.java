package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;
import com.example.panda.model.GioHang;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;

public class GioHangDao {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences ;
    public GioHangDao(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("PANDA", Context.MODE_PRIVATE);
    }

    // lấy danh sách giỏ hàng theo mand

    public ArrayList<GioHang> getDSGioHang(String mand){
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT gh.magh, mo.mamon, nd.mand, mo.tenmon, mo.anh, mo.gia, gh.soluong FROM GIOHANG gh,  MONAN mo, NGUOIDUNG nd WHERE gh.mamon = mo.mamon AND gh.mand = nd.mand", null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String ma = cursor.getString(2);
                if (ma.equals(mand)){
                    list.add(new GioHang(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6)));
                }

            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<GioHang> getDSGioHangData(){
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT gh.magh, mo.mamon, nd.mand, mo.tenmon, mo.anh, mo.gia, gh.soluong FROM GIOHANG gh,  MONAN mo, NGUOIDUNG nd WHERE gh.mamon = mo.mamon AND gh.mand = nd.mand", null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {

                    list.add(new GioHang(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6)));

            }while (cursor.moveToNext());
        }
        return list;
    }

    // kiểm tra giỏ hàng của kh có tồn tại

    public boolean checkGiohang(String mand){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GIOHANG WHERE mand = ?",new String[]{mand});

        if (cursor.getCount() != 0)
            return true;
        return false;
    }

    // thêm giỏ hàng
    public boolean themGioHang(GioHang gioHang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mamon", gioHang.getMamon());
        contentValues.put("mand", gioHang.getMand());
        contentValues.put("soluong", gioHang.getSoluong());
        long check = sqLiteDatabase.insert("GIOHANG",null,contentValues);

        if (check == -1)
            return false;
        return true;

    }

    // cập nhật số lượng
    public boolean capNhatSoL(int magh, int soluong){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("soluong", soluong);
        long check = sqLiteDatabase.update("GIOHANG", contentValues,"magh = ?", new String[]{String.valueOf(magh)});

        if (check == -1)
            return false;
        return true;
    }

    // lấy tổng số lượng
    public int tongSoLuong(String mand){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(soluong) FROM GIOHANG WHERE mand = ?", new String[]{mand});

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {
            return 0;
        }
    }

    // xóa

    public boolean xoaGioHang(int magh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GIOHANG", "magh = ?", new String[]{String.valueOf(magh)});

        if (check == -1)
            return false;
        return true;
    }

    // kiểm tra khi sp tồn tại trong giỏ hàng
    public boolean kiemTraSLtontai( int mamon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT magh, mamon, mand, soluong FROM GIOHANG ", null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int ma = cursor.getInt(1);
                if (ma == mamon) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("magh", cursor.getInt(0));
                    editor.putInt("soluong", cursor.getInt(3));
                    editor.commit();
                    return true;
                }
            }while (cursor.moveToNext());
        }
        return false;
    }

    // xóa theo mand
    public boolean xoaGioHangTheomand(String mand){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GIOHANG", "mand = ?", new String[]{String.valueOf(mand)});

        if (check == -1)
            return false;
        return true;
    }
}

















