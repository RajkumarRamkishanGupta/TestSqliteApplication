package com.example.dell.mysqliteapplicationtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.dell.mysqliteapplicationtest.DbContract.TABLE_NAME;

/**
 * Created by dell on 22/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper{


    public static final int DATABAE_VERSION=1;
    Context ctx;


    SQLiteDatabase db;
    public static final String CREATE_TABLE="create table "+ TABLE_NAME+ "(id integer primary key autoincrement,"+DbContract.TITLE+" text,"
            + DbContract.FIRST+" text,"+ DbContract.LAST+" text,"
            + DbContract.EMAIL+" text,"+ DbContract.STREET+" text,"
            + DbContract.THUMBNAIL+" text,"+ DbContract.CITY+" text,"
            + DbContract.STATE+" text,"+ DbContract.PHONE+" text,"
            + DbContract.POSTCODE+" text);";

    public static final String DROP_TABLE="create table if exists " + TABLE_NAME;


    public DbHelper(Context context) {
        super(context,DbContract.DB_NAME,null,DATABAE_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void SaveToLocalDB(String title,String first,String last,String email,String street,String thumbnil,String city,String stste
            ,String phone,String postcode,SQLiteDatabase database){

        ContentValues content=new ContentValues();
        content.put(DbContract.TITLE,title);
        content.put(DbContract.FIRST,first);
        content.put(DbContract.LAST,last);
        content.put(DbContract.EMAIL,email);
        content.put(DbContract.STREET,street);
        content.put(DbContract.THUMBNAIL,thumbnil);
        content.put(DbContract.CITY,city);
        content.put(DbContract.STATE,stste);
        content.put(DbContract.PHONE,phone);
        content.put(DbContract.POSTCODE,postcode);
        //db is obj of sqliteda
        database.insert(TABLE_NAME,null,content);
    }

    public Cursor readFromLocalDb(SQLiteDatabase database){

        String[] projections = {"id",DbContract.TITLE,DbContract.FIRST,DbContract.LAST,DbContract.EMAIL,DbContract.STREET,DbContract.THUMBNAIL
        ,DbContract.CITY,DbContract.STATE,DbContract.PHONE,DbContract.POSTCODE};
        return  (database.query(DbContract.TABLE_NAME,projections,null,null,null,null,null));
    }
}
