package com.ario.safarstan;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ario.safarstan.DBHelper.SelectDB;
import com.ario.safarstan.model.JazebehModel;

import java.io.IOException;
import java.io.InputStream;

public class DetailJazebehActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private LinearLayoutManager lay;
  JazebehModel jazebeh;
  AdapterJazebehItem adapter;
  DrawerLayout navigationView;
  boolean bb = false;
  String strNameshohada;
  int id;
  boolean b = false;
  SelectDB check;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_jazebeh);

    setUpStatusBar();
    initToolbar();
     check = new SelectDB();
    Bundle extras = getIntent().getExtras();
    id = -1;
    if (extras != null) {
      id = extras.getInt("ID");
      jazebeh = check.selectJazebeh(id);
    }
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
    ImageView imgMain = (ImageView) findViewById(R.id.img_ba);
    final ImageView imgLike = (ImageView) findViewById(R.id.img_liked);
    ImageView imgMap = (ImageView) findViewById(R.id.img_call);
    TextView txtName = (TextView) findViewById(R.id.txt_named);
    TextView txtAddress = (TextView) findViewById(R.id.txt_address);
    TextView txtTozihat = (TextView) findViewById(R.id.txt_tozihat);
    Button btnSend = (Button) findViewById(R.id.btn_send_data);
    btnSend.setTypeface(Base.font1);
    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ti=jazebeh.getTitle();
            String ti2="\n"+jazebeh.getDetail();
            String sha = jazebeh.getAddress()+"\n";
            String app="اپلیکشن سفرستان";
            String tiok=ti+ti2+"\n"+"\n"+"\n"+sha+"\n"+"\n"+"\n"+app;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, tiok);
            DetailJazebehActivity.this.startActivity(Intent.createChooser(sharingIntent, "ارسال برای دیگران"));
      }
    });
    try {
      if (jazebeh.getPicUrl() == null || !jazebeh.getPicUrl().contains("p")) {
        imgMain.setImageResource(R.drawable.no_image);
      } else {
        InputStream ims = Base.getContext().getAssets().open("pic/" + jazebeh.getPicUrl() + ".jpg");
        Drawable d = Drawable.createFromStream(ims, null);
        imgMain.setImageDrawable(d);
        ims.close();
      }
    } catch (IOException ex) {
      Log.i("BUG", ex.toString());
    }
    if (jazebeh.getLike() == 0) {
      imgLike.setImageResource(R.drawable.faveno);
    } else {
      imgLike.setImageResource(R.drawable.faveyes);
    }
    imgLike.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("LIK"," "+ jazebeh.getLike());
        if (jazebeh.getLike() == 0) {
          imgLike.setImageResource(R.drawable.faveyes);
          check.id=jazebeh.getId();
          jazebeh.setLike(1);
          Log.i("LIK","1 "+ jazebeh.getLike());
          check.setLike();
        }else if (jazebeh.getLike() == 1){
          imgLike.setImageResource(R.drawable.faveno);
          check.id=jazebeh.getId();
          jazebeh.setLike(0);
          check.setDisLike();
          Log.i("LIK","2 "+ jazebeh.getLike());
        }
      }
    });
    txtName.setText(jazebeh.getTitle());
    txtAddress.setText(jazebeh.getAddress());
    txtTozihat.setText(jazebeh.getDetail());

    imgMap.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("LOC", "1");
        //statusCheck();
        //if (b == true) {
        Intent intent = new Intent(DetailJazebehActivity.this, MapActivity.class);
        intent.putExtra("lat", jazebeh.getLat());
        intent.putExtra("lang", jazebeh.getLang());
        DetailJazebehActivity.this.startActivity(intent);
        //}
      }
    });
  }

  public void statusCheck() {
    Log.i("LOC", "2");
    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    Log.i("LOC", "3");
    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      Log.i("LOC", "4");
      buildAlertMessageNoGps();

    }
  }

  private void buildAlertMessageNoGps() {
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
      .setCancelable(false)
      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
        public void onClick(final DialogInterface dialog, final int id) {
          startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
          b = true;
        }
      })
      .setNegativeButton("No", new DialogInterface.OnClickListener() {
        public void onClick(final DialogInterface dialog, final int id) {
          dialog.cancel();
        }
      });
    final AlertDialog alert = builder.create();
    alert.show();
    b = false;
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
        Intent intent = new Intent(DetailJazebehActivity.this, MessageActivity.class);
        DetailJazebehActivity.this.startActivity(intent);
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    favorate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(DetailJazebehActivity.this, FavirateActivity.class);
        DetailJazebehActivity.this.startActivity(intent);
        Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    about.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog2 = new Dialog(DetailJazebehActivity.this);
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

