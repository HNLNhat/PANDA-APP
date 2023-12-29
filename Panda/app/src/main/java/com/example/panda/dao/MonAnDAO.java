package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.panda.database.DbHelper;
import com.example.panda.model.MonAn;

import java.util.ArrayList;

public class MonAnDAO {
    DbHelper dbHelper;
    public MonAnDAO(Context context){
        dbHelper= new DbHelper(context);
    }

    // lay ds mon an
    public ArrayList<MonAn> getDSMonAn(){
        ArrayList<MonAn> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT mo.mamon, lo.maloai, mo.tenmon, lo.tenloai, mo.gia, mo.mota, mo.anh FROM MONAN mo, LOAIMON lo WHERE mo.maloai = lo.maloai", null);

        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {
                list.add(new MonAn(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //lấy ds theo loại
    public ArrayList<MonAn> getDSTheoLoaiMonAn(int maloai){
        ArrayList<MonAn> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT mo.mamon, lo.maloai, mo.tenmon, lo.tenloai, mo.gia, mo.mota, mo.anh FROM MONAN mo, LOAIMON lo WHERE mo.maloai = lo.maloai", null);

        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {
                int loai = cursor.getInt(1);
                if (loai == maloai) {
                    list.add(new MonAn(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }



    // them
    public boolean themMonAn(int maloai, String tenmon, int gia, String mota, String anh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maloai", maloai);
        contentValues.put("tenmon", tenmon);
        contentValues.put("gia", gia);
        contentValues.put("mota", mota);
        contentValues.put("anh", anh);
        long check = sqLiteDatabase.insert("MONAN", null, contentValues);

        if (check == -1)
            return false;
        return true;
    }


    // cap nhat
    public boolean capNhatMonAn(MonAn monAn){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maloai", monAn.getMaloai());
        contentValues.put("tenmon", monAn.getTenmon());
        contentValues.put("gia", monAn.getGia());
        contentValues.put("mota", monAn.getMota());
        long check = sqLiteDatabase.update("MONAN",contentValues, "mamon = ?", new String[]{String.valueOf(monAn.getMamon())});

        if (check == -1)
            return false;
        return true;
    }

    // xoa
    public boolean xoaMonAn(int mamon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("MONAN", "mamon = ?", new String[]{String.valueOf(mamon)});

        if (check == -1 )
            return false;
        return true;
    }
}






















