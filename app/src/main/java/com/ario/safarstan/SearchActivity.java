package com.ario.safarstan;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ario.safarstan.DBHelper.SelectDB;
import com.ario.safarstan.model.JazebehHaModel;
import com.ario.safarstan.model.SharModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
  private ImageView imgJazebeh;
  private ImageView imgShar;
  private LinearLayout layFilter;
  private LinearLayout layShar;
  private LinearLayout layJazebeh;
  private EditText edtSearch;
  private RecyclerView recyclerView;
  private AdapterJazebehItem newsAdapter;
  private AdapterMainItem newsAdapter2;
  private CardView cardLoading;
  private List<JazebehHaModel> posts;
  private List<JazebehHaModel> posts2;
  private List<SharModel> postsShar;
  private List<SharModel> postsShar2;
  boolean bb = false;
  String strNameshohada;
  DrawerLayout navigationView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    navigationView = (DrawerLayout) findViewById(R.id.drawer);
    setUpItemNavigation();
    edtSearch = (EditText) findViewById(R.id.edt_search1);
    layFilter = (LinearLayout) findViewById(R.id.lay_filter);
    layShar = (LinearLayout) findViewById(R.id.lay_top_post);
    layJazebeh = (LinearLayout) findViewById(R.id.lay_top_member);
    imgJazebeh = (ImageView) findViewById(R.id.img_top_member);
    imgShar = (ImageView) findViewById(R.id.img_top_post);
    TextView txtFilter = (TextView) findViewById(R.id.txt_filter);
    ImageView imgFilter = (ImageView) findViewById(R.id.img_filter);
    LinearLayout layFilterTop = (LinearLayout) findViewById(R.id.lay_filter_t);
    ImageView imgGride = (ImageView) findViewById(R.id.list_gride);
    ImageView imgVertical = (ImageView) findViewById(R.id.list_vertical);
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    ImageView imgDrawer = (ImageView) findViewById(R.id.img_drawer);
    imgDrawer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        navigationView.openDrawer(GravityCompat.START);
      }
    });
    LinearLayoutManager lay = new LinearLayoutManager(Base.getContext(), LinearLayoutManager.VERTICAL, false);
    loadData();
    recyclerView.setLayoutManager(lay);
    imgJazebeh.setVisibility(View.VISIBLE);
    imgShar.setVisibility(View.INVISIBLE);


    txtFilter.setOnClickListener(onclic);
    imgFilter.setOnClickListener(onclic);
    layFilterTop.setOnClickListener(onclic);
    edtSearch.setTypeface(Base.font1);
    //Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).show();
    imgGride.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        StaggeredGridLayoutManager lay = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lay);

      }
    });

    imgVertical.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        LinearLayoutManager lay = new LinearLayoutManager(Base.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lay);
      }
    });
    layShar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        imgShar.setVisibility(View.VISIBLE);
        imgJazebeh.setVisibility(View.INVISIBLE);
        loadDataShar();
        //Collections.sort(posts2, new MyPostComp());
        //newsAdapter.notifyDataSetChanged();
        layFilter.setVisibility(View.GONE);

      }
    });
    layJazebeh.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        imgJazebeh.setVisibility(View.VISIBLE);
        imgShar.setVisibility(View.INVISIBLE);
        loadData();
        //Collections.sort(posts2, new MyMemberComp());
        //newsAdapter.notifyDataSetChanged();

        layFilter.setVisibility(View.GONE);
      }
    });
    /*if(imgJazebeh.getVisibility()==View.VISIBLE) {
      edtSearch.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
          String searchString = edtSearch.getText().toString().trim();
          int textLength = searchString.length();
          posts2.clear();
          bb = false;
          for (int i = 0; i <= (posts.size() - 1); i++) {
            strNameshohada = posts.get(i).getTitle();
            if (textLength <= strNameshohada.length()) {
              if (searchString.equalsIgnoreCase(strNameshohada.substring(0, textLength)))
                bb = true;
            }
            if (bb) {
              posts2.add(posts.get(i));
            }
          }
          newsAdapter.notifyDataSetChanged();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
      });
    }*/
    //Toast.makeText(Base.getContext(), "sahar...", Toast.LENGTH_LONG).show();
    edtSearch.addTextChangedListener(new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        String searchString = edtSearch.getText().toString().trim();
        int textLength = searchString.length();
        if (imgShar.getVisibility() == View.VISIBLE) {
          postsShar2.clear();
          bb = false;
          for (int i = 0; i <= (postsShar.size() - 1); i++) {
            strNameshohada = postsShar.get(i).getName();
            if (textLength <= strNameshohada.length()) {
              if (searchString.equalsIgnoreCase(strNameshohada.substring(0, textLength)))
                bb = true;
            }
            if (bb) {
              postsShar2.add(postsShar.get(i));
            }
          }
          newsAdapter2.notifyDataSetChanged();
        }
        if (imgJazebeh.getVisibility() == View.VISIBLE) {
          posts2.clear();
          bb = false;
          for (int i = 0; i <= (posts.size() - 1); i++) {
            strNameshohada = posts.get(i).getTitle();
            if (textLength <= strNameshohada.length()) {
              if (searchString.equalsIgnoreCase(strNameshohada.substring(0, textLength)))
                bb = true;
            }
            if (bb) {
              posts2.add(posts.get(i));
            }
          }
          newsAdapter.notifyDataSetChanged();
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });
  }


  View.OnClickListener onclic = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      if (layFilter.getVisibility() == View.GONE) {
        layFilter.setVisibility(View.VISIBLE);
      } else {
        layFilter.setVisibility(View.GONE);
      }
    }
  };

  private void loadData() {
    SelectDB select = new SelectDB();
    posts = select.selectAllJazebehHa();
    posts2 = new ArrayList<JazebehHaModel>(posts);
    newsAdapter = new AdapterJazebehItem(SearchActivity.this, posts2);
    recyclerView.setAdapter(newsAdapter);
    newsAdapter.notifyDataSetChanged();
    //cardLoading.setVisibility(View.GONE);
  }

  private void loadDataShar() {
    SelectDB select = new SelectDB();
    postsShar = select.selectAllShar();
    postsShar2 = new ArrayList<SharModel>(postsShar);
    newsAdapter2 = new AdapterMainItem(SearchActivity.this, postsShar2, 1);
    recyclerView.setAdapter(newsAdapter2);
    newsAdapter2.notifyDataSetChanged();
    //cardLoading.setVisibility(View.GONE);
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
        Intent intent = new Intent(SearchActivity.this, MessageActivity.class);
        SearchActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "jaber...", Toast.LENGTH_SHORT).cancel();
      }
    });
    favorate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigationView.closeDrawers();
        Intent intent = new Intent(SearchActivity.this, FavirateActivity.class);
        SearchActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
      }
    });
    about.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Dialog dialog2 = new Dialog(SearchActivity.this);
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
        //Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        //MainActivity.this.startActivity(intent);
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
        //Toast.makeText(Base.getContext(), "sahr...", Toast.LENGTH_SHORT).cancel();
      }
    });
  }
}
