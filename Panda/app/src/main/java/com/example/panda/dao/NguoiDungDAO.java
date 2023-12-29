package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;

public class NguoiDungDAO {
    SharedPreferences sharedPreferences ;
    DbHelper dbHelper;

    public NguoiDungDAO (Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("PANDA", Context.MODE_PRIVATE);
    }

    // dang nhap
    public boolean checkDangNhap(String madn, String matkhau){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE mand = ? AND matkhau = ?", new String[]{madn,matkhau});

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mand", cursor.getString(0));
            editor.putString("hoten", cursor.getString(1));
            editor.putString("loai", cursor.getString(3));
            editor.commit();
            return true;
        }else {
            return false;
        }
    }


    public boolean DangKy(String mand, String hoten, String matkhau, String loai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", mand);
        contentValues.put("hoten", hoten);
        contentValues.put("matkhau", matkhau);
        contentValues.put("loai", loai);
        long check = sqLiteDatabase.insert("NGUOIDUNG",null,contentValues);
        if (check == -1)
            return false;
        return true;
    }

    public int capNhatMK(String username, String oldpass, String newpass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE mand = ? AND matkhau = ?", new String[]{username,oldpass});

        if (cursor.getCount() > 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newpass);
            long check = sqLiteDatabase.update("NGUOIDUNG",contentValues,"mand = ?", new String[]{username});
            if (check == -1)
                return -1;
            return 1;
        }
        return 0;
    }
}
