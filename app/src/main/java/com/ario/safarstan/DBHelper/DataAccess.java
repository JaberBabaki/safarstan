package com.ario.safarstan.DBHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.ario.safarstan.Base;

import java.io.IOException;


public class DataAccess {

    public SQLiteDatabase newDb;
    private DBHelper      DbHelper;
    private String        myPath = Base.DB_PATH;


    // ArrayList<DataMessage> DataMessage = new ArrayList<DataMessage>();

    public void copyDatabase() {
        DbHelper = new DBHelper(Base.getContext());
        try {
            DbHelper.createDataBase();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openDatabase() {
        newDb = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory().getAbsolutePath()+"/safarstan/db/safarstan.db", null, SQLiteDatabase.OPEN_READWRITE);
    }

    public int command(String sql) {
        newDb.execSQL(sql);
        newDb.close();
        return 1;
    }

}
