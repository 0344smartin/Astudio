package com.example.myagenda;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySQLiteManager extends SQLiteOpenHelper {

    final private String table = "articulos";
    public MySQLiteManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table+"(codigo int primary key,descripcion text,precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sb, int i, int i1) {
    }

    public boolean delete(Asset a) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(table,"codigo="+ a.getId(),null);
        db.close();
        return (i > 0);

    }

    public boolean add(Asset a)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues reg = new ContentValues();
        reg.put("codigo",a.getId());
        reg.put("descripcion",a.getDescripcion());
        reg.put("precio",a.getPrecio());
        long t = db.insert(table, null, reg);
        db.close();
        return t>0;
    }

    public void getAll(ArrayList<Asset> assets) {
        assets.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(
                "select * from articulos order by codigo",null)){
            while(cursor.moveToNext()){
                Asset asset = new Asset();
                asset.setId(cursor.getInt(0));
                asset.setDescripcion(cursor.getString(1));
                asset.setPrecio(cursor.getFloat(2));
                assets.add(asset);
            }
        } catch (Exception ignored){
        } finally {
            db.close();
        }
    }
}
