package com.ario.safarstan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ario.safarstan.DBHelper.SelectDB;
import com.ario.safarstan.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private LinearLayoutManager lay;
  List<MessageModel> shar = new ArrayList<MessageModel>();
  AdapterMessageItem adapter;
  DrawerLayout navigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_message);

    setUpStatusBar();
    initToolbar();
    SelectDB check = new SelectDB();
    shar = check.selectAllMessage();
    initView();
    setUpItemNavigation();
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
    Log.i("SIZ", "" + shar.size());
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    lay = new LinearLayoutManager(Base.getContext(), LinearLayoutManager.VERTICAL, false);
    adapter = new AdapterMessageItem(MessageActivity.this, shar);

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


  public void setUpItemNavigation() {
    LinearLayout messge = (LinearLayout) navigationView.findViewById(R.id.lay_message);
    LinearLayout favorate = (LinearLayout) navigationView.findViewById(R.id.lay_favorate);
    LinearLayout about = (LinearLayout) navigationView.findViewById(R.id.lay_about);
    messge.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
      }
    });
    favorate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(MessageActivity.this, FavirateActivity.class);
        MessageActivity.this.startActivity(intent);
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    about.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog2 = new Dialog(MessageActivity.this);
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
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
  }
}
