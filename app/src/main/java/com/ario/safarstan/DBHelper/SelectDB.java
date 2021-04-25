package com.ario.safarstan.DBHelper;

import android.database.Cursor;
import android.util.Log;

import com.ario.safarstan.model.JazebehHaModel;
import com.ario.safarstan.model.JazebehModel;
import com.ario.safarstan.model.MessageModel;
import com.ario.safarstan.model.SharModel;

import java.util.ArrayList;


public class SelectDB extends DataAccess {

  private String shahr;
  private Integer ostan;
  public int id;


  public void setLike() {
    openDatabase();
    String sql = "update jazebehha set like=1 where id="+id;
    newDb.execSQL(sql);
    newDb.close();

  }
  public void setDisLike() {
    openDatabase();
    String sql = "update jazebehha set like=0 where id="+id;
    newDb.execSQL(sql);
    newDb.close();

  }


  public ArrayList<SharModel> selectAllShar() {
    ArrayList<SharModel> Data = new ArrayList<SharModel>();
    openDatabase();
    String sql = "SELECT * FROM shar";
    Cursor cursor = newDb.rawQuery(sql, null);
    while (cursor.moveToNext()) {
      SharModel shar = new SharModel();
      shar.setId(cursor.getInt(cursor.getColumnIndex("id")));
      shar.setName(cursor.getString(cursor.getColumnIndex("name")));
      shar.setDetail(cursor.getString(cursor.getColumnIndex("tozihat")));
      shar.setPicUrl(cursor.getString(cursor.getColumnIndex("naghshe")));
      Data.add(shar);
    }
    cursor.close();
    newDb.close();
    return Data;
  }

  public ArrayList<JazebehHaModel> selectJazebehHa(int idShar) {
    ArrayList<JazebehHaModel> Data = new ArrayList<JazebehHaModel>();
    openDatabase();
    String sql = "SELECT * FROM jazebehha where id_shar=" + idShar;
    Cursor cursor = newDb.rawQuery(sql, null);
    while (cursor.moveToNext()) {
      JazebehHaModel jazebeh = new JazebehHaModel();
      jazebeh.setId(cursor.getInt(cursor.getColumnIndex("id")));
      jazebeh.setLike(cursor.getInt(cursor.getColumnIndex("like")));
      jazebeh.setTitle(cursor.getString(cursor.getColumnIndex("name")));
      jazebeh.setDetail(cursor.getString(cursor.getColumnIndex("tozihat")));
      jazebeh.setPicUrl(cursor.getString(cursor.getColumnIndex("picture")));
      jazebeh.setAddress(cursor.getString(cursor.getColumnIndex("address")));
      jazebeh.setFilm(cursor.getString(cursor.getColumnIndex("film")));
      Data.add(jazebeh);
    }
    cursor.close();
    newDb.close();
    return Data;
  }


  public ArrayList<JazebehHaModel> selectAllJazebehHa() {
    ArrayList<JazebehHaModel> Data = new ArrayList<JazebehHaModel>();
    openDatabase();
    String sql = "SELECT * FROM jazebehha";
    Cursor cursor = newDb.rawQuery(sql, null);
    while (cursor.moveToNext()) {
      JazebehHaModel jazebeh = new JazebehHaModel();
      jazebeh.setId(cursor.getInt(cursor.getColumnIndex("id")));
      jazebeh.setLike(cursor.getInt(cursor.getColumnIndex("like")));
      jazebeh.setTitle(cursor.getString(cursor.getColumnIndex("name")));
      jazebeh.setDetail(cursor.getString(cursor.getColumnIndex("tozihat")));
      jazebeh.setPicUrl(cursor.getString(cursor.getColumnIndex("picture")));
      jazebeh.setAddress(cursor.getString(cursor.getColumnIndex("address")));
      jazebeh.setFilm(cursor.getString(cursor.getColumnIndex("film")));
      Data.add(jazebeh);
    }
    cursor.close();
    newDb.close();
    return Data;
  }
  public JazebehModel selectJazebeh(int idShar) {
    JazebehModel jazebeh = new JazebehModel();
    openDatabase();
    String sql = "SELECT * FROM jazebehha where id=" + idShar;
    Cursor cursor = newDb.rawQuery(sql, null);
    while (cursor.moveToNext()) {
      jazebeh.setId(cursor.getInt(cursor.getColumnIndex("id")));
      jazebeh.setLike(cursor.getInt(cursor.getColumnIndex("like")));
      jazebeh.setTitle(cursor.getString(cursor.getColumnIndex("name")));
      jazebeh.setDetail(cursor.getString(cursor.getColumnIndex("tozihat")));
      jazebeh.setPicUrl(cursor.getString(cursor.getColumnIndex("picture")));
      jazebeh.setAddress(cursor.getString(cursor.getColumnIndex("address")));
      jazebeh.setFilm(cursor.getString(cursor.getColumnIndex("film")));
      jazebeh.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
      jazebeh.setLang(cursor.getDouble(cursor.getColumnIndex("lang")));
    }
    cursor.close();
    newDb.close();
    return jazebeh;
  }
  public ArrayList<JazebehHaModel> selectJazebehLike() {
    ArrayList<JazebehHaModel> Data = new ArrayList<JazebehHaModel>();
    openDatabase();
    String sql = "SELECT * FROM jazebehha where like=1";
    Cursor cursor = newDb.rawQuery(sql, null);
    while (cursor.moveToNext()) {
      JazebehHaModel jazebeh = new JazebehHaModel();
      jazebeh.setId(cursor.getInt(cursor.getColumnIndex("id")));
      jazebeh.setLike(cursor.getInt(cursor.getColumnIndex("like")));
      jazebeh.setTitle(cursor.getString(cursor.getColumnIndex("name")));
      jazebeh.setDetail(cursor.getString(cursor.getColumnIndex("tozihat")));
      jazebeh.setPicUrl(cursor.getString(cursor.getColumnIndex("picture")));
      jazebeh.setAddress(cursor.getString(cursor.getColumnIndex("address")));
      jazebeh.setFilm(cursor.getString(cursor.getColumnIndex("film")));
      Data.add(jazebeh);
    }
    cursor.close();
    newDb.close();
    return Data;
  }

  public ArrayList<MessageModel> selectAllMessage() {
    openDatabase();
    ArrayList<MessageModel> Data = new ArrayList<MessageModel>();
    String sql = "SELECT * FROM message";
    Cursor cursor = newDb.rawQuery(sql, null);
    Log.i("SIZ", "" + cursor);
    while (cursor.moveToNext()) {
      MessageModel message = new MessageModel();
      message.setId(cursor.getInt(cursor.getColumnIndex("id")));
      message.setTitle(cursor.getString(cursor.getColumnIndex("title")));
      message.setMessage(cursor.getString(cursor.getColumnIndex("message")));

      Log.i("SIZ", "" + message.getMessage());
      Data.add(message);
    }
    Log.i("SIZ", "" + Data.size());
    cursor.close();
    newDb.close();
    return Data;
  }

}
