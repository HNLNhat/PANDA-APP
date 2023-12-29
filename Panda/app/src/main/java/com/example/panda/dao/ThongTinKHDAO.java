package com.example.panda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.panda.database.DbHelper;
import com.example.panda.model.ThongTinKH;

import java.util.ArrayList;

public class ThongTinKHDAO {

    DbHelper dbHelper;
    public ThongTinKHDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lay thong tin kh
    public ArrayList<ThongTinKH> getThongTinKH(){
        ArrayList<ThongTinKH> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT kh.makh, nd.mand, nd.hoten, kh.sdt, kh.diachi FROM THONGTINKH kh, NGUOIDUNG nd WHERE kh.mand = nd.mand ",null);

        if (cursor.getCount() !=0 ){
            cursor.moveToFirst();
            do {

                list.add(new ThongTinKH(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)));

            }while (cursor.moveToNext());
        }

        return list;
    }

    // them thông tin
    public boolean themThongTinKH(ThongTinKH thongTinKH){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", thongTinKH.getMadn());
        contentValues.put("sdt", thongTinKH.getSdt());
        contentValues.put("diachi", thongTinKH.getDiachi());
        long check = sqLiteDatabase.insert("THONGTINKH",null,contentValues);

        if (check == -1 )
            return false;
        return true;

    }

    // sua thong tin
    public boolean capNhatThongThinKH(ThongTinKH thongTinKH){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mand", thongTinKH.getMadn());
        contentValues.put("sdt", thongTinKH.getSdt());
        contentValues.put("diachi", thongTinKH.getDiachi());
        long check = sqLiteDatabase.update("THONGTINKH",contentValues,"makh = ?", new String[]{String.valueOf(thongTinKH.getMakh())});

        if (check == -1 )
            return false;
        return true;


    }

}
