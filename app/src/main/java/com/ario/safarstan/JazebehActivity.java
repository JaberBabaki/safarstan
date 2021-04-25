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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ario.safarstan.DBHelper.SelectDB;
import com.ario.safarstan.model.JazebehHaModel;

import java.util.ArrayList;
import java.util.List;

public class JazebehActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private LinearLayoutManager lay;
  List<JazebehHaModel> shar;
  List<JazebehHaModel> shar2;
  AdapterJazebehItem adapter;
  DrawerLayout navigationView;
  boolean bb = false;
  String strNameshohada;
  int id;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_jazebeh);
    setUpStatusBar();
    initToolbar();
    SelectDB check = new SelectDB();
    Bundle extras = getIntent().getExtras();
    id = -1;
    if (extras != null) {
      id = extras.getInt("ID");
      shar = check.selectJazebehHa(id);
    }
    initView();
    setUpItemNavigation();

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
          strNameshohada = shar.get(i).getTitle();
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
    shar2 = new ArrayList<JazebehHaModel>(shar);
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    lay = new LinearLayoutManager(Base.getContext(), LinearLayoutManager.VERTICAL, false);
    adapter = new AdapterJazebehItem(JazebehActivity.this, shar2);

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
        Intent intent = new Intent(JazebehActivity.this, MessageActivity.class);
        JazebehActivity.this.startActivity(intent);
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    favorate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(JazebehActivity.this, FavirateActivity.class);
        JazebehActivity.this.startActivity(intent);
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    about.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog2 = new Dialog(JazebehActivity.this);
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
