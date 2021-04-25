package com.ario.safarstan.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.ario.safarstan.Base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBHelper extends SQLiteOpenHelper {

    private final Context context;


    public DBHelper(Context context) {
        super(context, Base.DB_NAME, null, 1);
        this.context = context;
        //new File(G.DB_PATH).mkdirs();

    }


    public void createDataBase() throws IOException {
        Log.i("DBB", "+" + "1");
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.i("DBB", "+" + "3");
        } else {
            this.getReadableDatabase();
            Log.i("DBB", "+" + "4");
            try {
                copyDataBase();

            }
            catch (IOException e) {
                Log.i("LOG", "+" + e.toString());
                throw new Error("Error copying database");
            }
        }
    }


    private boolean checkDataBase() {
        File dbFile = new File(Base.DB_PATH);
        Log.i("DBB", "+" + "2");
        return dbFile.exists();
    }


    private void copyDataBase() throws IOException {

        InputStream myInput = context.getAssets().open("db/"+Base.DB_NAME);
        Log.i("DBB", "+" + "5");
       // Log.i("LOG", "+" + G.DB_NAME_Ass);
        String outFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/safarstan/db/safarstan.db";
        Log.i("DBB", "+" + "6"+outFileName);
       // Log.i("LOG", "++" + G.DB_PATH + G.DB_NAME);
        OutputStream myOutput = new FileOutputStream(outFileName);
        Log.i("DBB", "+" + "7");
       // Log.i("LOG", "+++" + G.DB_PATH + G.DB_NAME);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);

        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}