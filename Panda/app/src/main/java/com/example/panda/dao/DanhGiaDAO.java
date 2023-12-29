package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;
import com.example.panda.model.DanhGia;

import java.util.ArrayList;

public class DanhGiaDAO {
    DbHelper dbHelper;
    public DanhGiaDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lay ds danh gia
    public ArrayList<DanhGia> getDSDanhGia(int mamon){
        ArrayList<DanhGia> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT dg.madg, nd.mand, nd.hoten, dg.ngay, mo.mamon, dg.danhgia FROM MONAN mo, DANHGIA dg, NGUOIDUNG nd WHERE dg.mamon = mo.mamon and nd.mand = dg.mand", null);

        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {
                int ma = cursor.getInt(4);
                if (ma == mamon) {
                    list.add(new DanhGia(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5)));
                }
            }while (cursor.moveToNext());
        }

        return list;
    }

    // them
    public boolean themDanhGia(DanhGia danhGia){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", danhGia.getMand());
        contentValues.put("mamon", danhGia.getMamon());
        contentValues.put("ngay", danhGia.getNgay());
        contentValues.put("danhgia", danhGia.getDanggia());
        long check = sqLiteDatabase.insert("DANHGIA", null, contentValues);

        if (check == -1 )
            return false;
        return true;
    }

    // cap nhat
    public boolean capNhatDanhGia(DanhGia danhGia){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", danhGia.getMand());
        contentValues.put("mamon", danhGia.getMamon());
        contentValues.put("ngay", danhGia.getNgay());
        contentValues.put("danhgia", danhGia.getDanggia());
        long check = sqLiteDatabase.update("DANHGIA", contentValues, "madg = ?", new String[]{String.valueOf(danhGia.getMadg())});

        if (check == -1 )
            return false;
        return true;
    }

    // xoa
    public boolean xoaDanhGia(String mand, int mamon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("DANHGIA", "mand = ? and mamon = ?", new String[]{mand,String.valueOf(mamon)});

        if (check == -1 )
            return false;
        return true;
    }

    //check danh gia
    public boolean checkDanhgia(String mand, int mamon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DANHGIA WHERE mand = ? and mamon = ?", new String[]{mand,String.valueOf(mamon)});
        if (cursor.getCount() != 0)
            return true;
        return false;

    }
}
