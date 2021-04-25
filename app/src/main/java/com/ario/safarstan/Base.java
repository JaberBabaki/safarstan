package com.ario.safarstan;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.io.ByteArrayOutputStream;

public class Base extends Application {
  public static Typeface font1;
  public static String FONT1_NAME   = "font/IRAN-Sans-Light.otf";
  public static ByteArrayOutputStream getPic;
  public static int rotation;
  public static Bitmap bit;
  private static Activity currentActivity;
  public static String title;
  public static String matn;
  private static Context context;
  public static String tag;
  public static String DIR_SDCARD;
  public static String DIR_PATH;
  public static String DIR_DB;
  public static String  DB_NAME="safarstan.db";
  public static String  DB_PATH=DIR_DB+"/"+DB_NAME;
  public static Fragment fragment;
  public static FragmentManager fragmentManager;

  public static int folloing;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    font1 = Typeface.createFromAsset(getContext().getAssets(), FONT1_NAME);
    DIR_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    DIR_PATH = DIR_SDCARD + "/safarstan/";
    DIR_DB = DIR_PATH + "db";

  }
  public static Context getContext() {
    if (currentActivity != null) {
      return currentActivity;
    }
    return context;
  }

}
