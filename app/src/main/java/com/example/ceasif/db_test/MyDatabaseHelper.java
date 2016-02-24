package com.example.ceasif.db_test;

/**
 * Created by ceasif on 2/2/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TRANSACTION.DB";
    public static final String COL1 = "name";
    public static final String COL2 = "paid";
    public static final String COL3 = "expense";

    public static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    //public static final String DATABASE_CREATE = "create table transaction_table (id integer primary key autoincrement,name text,paid text,expense text)";
    public static final String DATABASE_CREATE = "create table transaction_table (id integer primary key autoincrement,name text,paid text,expense text)";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
      /* Log.w(MyDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");*/
        db.execSQL("DROP TABLE IF EXISTS transaction_table");
        onCreate(db);
    }

    public boolean insertData(String name, String paid, String expense)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COL1, name);
       // content.put(COL2, paid);
        content.put(COL3, expense);


       long result = db.insert("transaction_table",null,content);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from transaction_table",null);
        return res;
    }
}
