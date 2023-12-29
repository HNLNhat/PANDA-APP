package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;
import com.example.panda.model.ChiTietDH;

import java.util.ArrayList;

public class ChiTietDHDAO {
    DbHelper dbHelper;
    public ChiTietDHDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lay ds chi tiet
    public ArrayList<ChiTietDH> getDSChiTietDH(int madh){
        ArrayList<ChiTietDH> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ct.macthd, dh.madh, mo.mamon, mo.tenmon, ct.soluong, mo.anh, ct.tien, dh.trangthai FROM CHITIETDH ct, DONHANG dh, MONAN mo WHERE mo.mamon = ct.mamon and ct.madh = dh.madh", null);

        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {
                int ma = cursor.getInt(1);
                if (ma == madh) {
                    list.add(new ChiTietDH(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6),cursor.getInt(7)));
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    // them
    public boolean themCHiTietDH(ChiTietDH chiTietDH){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("madh", chiTietDH.getMadh());
        contentValues.put("mamon", chiTietDH.getMamon());
        contentValues.put("soluong", chiTietDH.getSoluong());
        contentValues.put("tien", chiTietDH.getTien());
        long check = sqLiteDatabase.insert("CHITIETDH",null,contentValues);

        if (check == -1)
            return false;
        return true;
    }

    // xóa chi tiết đơn hàng

    public boolean xoaChitietdh(int madh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("CHITIETDH", "madh = ?", new String[]{String.valueOf(madh)});

        if (check == -1)
            return false;
        return true;
    }
}
