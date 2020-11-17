package com.example.basicbankingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.os.Build.ID;

public class SqlHandler {

    public static final String DATABASE_NAME = "MY_DATABASE";
    public static final int DATABASE_VERSION = 1;
    Context context;
    public SQLiteDatabase sqlDatabase;
    SqlDbHelper dbHelper;
    int bool =1;

    public static final String DATABASE_TABLE = "PHONE_CONTACTS";
    public SqlHandler(Context context) {

        dbHelper = new SqlDbHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        sqlDatabase = dbHelper.getWritableDatabase();

        if(bool==1) {
            sqlDatabase.execSQL("delete from " + DATABASE_TABLE);
            bool =2;
        }
        }


    public void executeQuery(String query) {
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();
            }

            sqlDatabase = dbHelper.getWritableDatabase();
            sqlDatabase.execSQL(query);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);
        }

    }
    public void deleteAll()
    {
   }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (sqlDatabase.isOpen()) {
                sqlDatabase.close();

            }
            sqlDatabase = dbHelper.getWritableDatabase();
            c1 = sqlDatabase.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

    public static class SqlDbHelper extends SQLiteOpenHelper {


        public static final String COLUMN1 = "slno";
        public static final String COLUMN2 = "name";
        public static final String COLUMN3 = "phone";
        public static final String COLOUMN4 = "balance";
        private static final String SCRIPT_CREATE_DATABASE = "create table "
                + DATABASE_TABLE + " (" + COLUMN1
                + " integer primary key autoincrement, " + COLUMN2
                + " text not null, " + COLUMN3 + " text not null, "+COLOUMN4+" text not null);";


        public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            db.execSQL(SCRIPT_CREATE_DATABASE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }


        }
    public boolean updateStudents(String name, String email, String balance){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqlDbHelper.COLUMN2,name);
        contentValues.put(SqlDbHelper.COLUMN3, email);
        contentValues.put(SqlDbHelper.COLOUMN4,balance);
        db.execSQL("UPDATE "+DATABASE_TABLE+" SET name = "+"'"+name+"' "+ "WHERE phone = "+"'"+email+"' "+"SET balance = "+"'"+balance+"'");


        return true;
    }

    public boolean updateValues(String position, int balance){
        ContentValues values = new ContentValues();
        values.put("balance", balance);
        sqlDatabase.update(DATABASE_TABLE, values,"name=?", new String[]{position} );
        return true;
    }

}

