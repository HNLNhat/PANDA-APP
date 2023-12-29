package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;
import com.example.panda.model.LoaiMon;

import java.util.ArrayList;

public class LoaiDAO {
    DbHelper dbHelper;
    public LoaiDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lay ds loai mon
    public ArrayList<LoaiMon> getDSLoai(){
        ArrayList<LoaiMon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAIMON", null);

        if (cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new LoaiMon(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    // them loai
    public boolean themLoai(String tenloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = sqLiteDatabase.insert("LOAIMON", null, contentValues);

        if (check == -1)
            return false;
        return true;
    }

    // cap nhat
    public boolean capNhatLoai(LoaiMon loaiMon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai",loaiMon.getTenloai() );
        long check = sqLiteDatabase.update("LOAIMON", contentValues, "maloai = ?", new String[]{String.valueOf(loaiMon.getMaloai())});

        if (check == -1)
            return false;
        return true;
    }

    // xoa
    public int xoaLoaiMon(int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM MONAN WHERE maloai = ?", new String[]{String.valueOf(maloai)});

        if (cursor.getCount() !=0){
            return -1;
        }

        long check = sqLiteDatabase.delete("LOAIMON", "maloai = ?", new String[]{String.valueOf(maloai)});

        if (check == -1)
            return 0;
        return 1;
    }
}






















