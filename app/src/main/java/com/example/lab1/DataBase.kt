package com.example.lab1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private var DATAВASE_NAМE="android_database.db"
private  var DATAВASE_VERSION = 1;

class DataBase(context: Context): SQLiteOpenHelper(context, DATAВASE_NAМE,null, DATAВASE_VERSION) {

    public var UID ="_id";
    public var ZAPROS = "zapros";
    public  var CODE = "code";

    public  var ТАВLЕ_NАМЕ = "search";

    private  var SQL_CREATE_ENТRIES = "CREATE TABLE "+ ТАВLЕ_NАМЕ +
            " ( " + UID +" integer primary key,"+
            ZAPROS + " VARCHAR(255), "+CODE+" INTEGER);";
    private var SQL_DELETE_ENТRIES ="DROP TABLE IF EXISTS" +
            ТАВLЕ_NАМЕ;

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL(this.SQL_CREATE_ENТRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL(this.SQL_DELETE_ENТRIES);
        onCreate (p0) ;
    }
}