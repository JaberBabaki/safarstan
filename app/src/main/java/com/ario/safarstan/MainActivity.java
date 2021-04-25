package com.ario.safarstan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ario.safarstan.DBHelper.DataAccess;
import com.ario.safarstan.DBHelper.SelectDB;
import com.ario.safarstan.model.SharModel;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private int position;
  private Bundle bundle;
  private CardView cardLoading;
  private RecyclerView recyclerView;
  private int page = 0;
  private LinearLayoutManager lay;
  List<SharModel> shar;
  List<SharModel> shar2;
  AdapterMainItem adapter;
  ViewPager mViewPager;
  DrawerLayout navigationView;
  public static int mainItem = 0;
  boolean bb = false;
  String strNameshohada;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setUpStatusBar();
    initToolbar();
    copyFile();

    SelectDB check = new SelectDB();
    shar = check.selectAllShar();
    initView();
    setUpItemNavigation();
    // com.ayat.tico
    // com.mansouri.tico
    JSONObject localJSONObject = new JSONObject();
    try {
      localJSONObject.put("do", "getstate");
      localJSONObject.put("usertype", "parent");
      localJSONObject.put("token", "059317c4a7fdf24ff33f6b51475abcd8ccba4d5d");
      localJSONObject.put("oid", "3010");
      localJSONObject.put("id", 1);
    } catch (Exception localException) {
    }
    //new GetSpinnerWebService(UltraRegisterActivity.this, 2).getWebService("http://ticoapp.co/api.php", localJSONObject);

    ArrayList localArrayList = new ArrayList(Arrays.asList((localJSONObject.toString() + "8vNlDITxO").split("")));
    Collections.sort(localArrayList);
    String str1 = "";
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext()) {
      String str2 = (String) localIterator.next();
      if (((str2.compareTo("a") < 0) || (str2.compareTo("z") > 0)) && ((str2.compareTo("A") < 0) || (str2.compareTo("Z") > 0)) && ((str2.compareTo("0") < 0) || (str2.compareTo("9") > 0)))
        continue;
      str1 = str1 + str2;
    }

    Log.i("TIC", "" + str1.hashCode());

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    final EditText edtSearch = (EditText) toolbar.findViewById(R.id.edt_search);
    edtSearch.addTextChangedListener(new TextWatcher() {

      @Override
      public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        String searchString = edtSearch.getText().toString().trim();

        int textLength = searchString.length();

        shar2.clear();
        bb = false;
        for (int i = 0; i <= (shar.size() - 1); i++) {

          strNameshohada = shar.get(i).getName();

          if (textLength <= strNameshohada.length()) {

            if (searchString.equalsIgnoreCase(strNameshohada.substring(0, textLength)))
              bb = true;
          }
          if (bb) {
            shar2.add(shar.get(i));
          }
        }
        adapter.notifyDataSetChanged();

      }


      @Override
      public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                    int arg3) {

      }


      @Override
      public void afterTextChanged(Editable s) {

      }
    });

  }

  public void setUpStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      View view = getWindow().getDecorView();
      view.setSystemUiVisibility(view.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    ImageView imgDrawer = (ImageView) findViewById(R.id.img_drawer);
    navigationView = (DrawerLayout) findViewById(R.id.drawer);
    imgDrawer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        navigationView.openDrawer(GravityCompat.START);
      }
    });
  }

  public void initView() {
    //ref.setBackgroundColor(Color.parseColor("#818d9d"));
    shar2 = new ArrayList<SharModel>(shar);
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    lay = new LinearLayoutManager(Base.getContext(), LinearLayoutManager.VERTICAL, false);
    adapter = new AdapterMainItem(MainActivity.this, shar2,0);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(lay);
    recyclerView.setAdapter(adapter);

  }


  public void initToolbar() {
    final LinearLayout laySearch = (LinearLayout) findViewById(R.id.lay_search_p);
    ImageView imgDrawer = (ImageView) findViewById(R.id.img_drawer);
    final ImageView imgSearch = (ImageView) findViewById(R.id.img_search);
    final ImageView imgCancel = (ImageView) findViewById(R.id.img_cancel_search);
    final EditText edtSearch = (EditText) findViewById(R.id.edt_search);
    edtSearch.setTypeface(Base.font1);

    // myactivity.navigationView.openDrawer(GravityCompat.START);

    imgSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        laySearch.setVisibility(View.VISIBLE);
        imgSearch.setVisibility(View.GONE);
      }
    });
    imgCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        laySearch.setVisibility(View.GONE);
        imgSearch.setVisibility(View.VISIBLE);
      }
    });
  }

  public void copyFile() {
    File dir = new File(Base.DIR_PATH);
    if (!dir.exists()) {
      Log.i("LOG", "H" + Base.DIR_SDCARD);
      dir.mkdir();
      File dir3 = new File(Base.DIR_DB);
      dir3.mkdir();
      DataAccess dataAccess = new DataAccess();
      dataAccess.copyDatabase();
    }

  }


  public void setUpItemNavigation() {
    LinearLayout messge = (LinearLayout) navigationView.findViewById(R.id.lay_message);
    LinearLayout favorate = (LinearLayout) navigationView.findViewById(R.id.lay_favorate);
    LinearLayout about = (LinearLayout) navigationView.findViewById(R.id.lay_about);
    LinearLayout search = (LinearLayout) navigationView.findViewById(R.id.lay_search1);
    messge.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(MainActivity.this, MessageActivity.class);
        MainActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    favorate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(MainActivity.this, FavirateActivity.class);
        MainActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
      }
    });
    about.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog2 = new Dialog(MainActivity.this);
        dialog2.setContentView(R.layout.dialog_about);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout layEltemas = (LinearLayout) dialog2.findViewById(R.id.lay_eltemas);
        navigationView.closeDrawers();
        layEltemas.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            dialog2.cancel();
          }
        });
        dialog2.setCancelable(true);
        dialog2.show();
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
      }
    });
    search.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        MainActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
      }
    });
  }
}
