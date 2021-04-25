package com.ario.safarstan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ario.safarstan.model.SharModel;
import com.ario.safarstan.customView.AndroidImageAdapter1;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jaber
 */
public class AdapterMainItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int VIEW_TYPE_HEADER = 0;
  private static final int VIEW_TYPE_DEFAULT_ITEM = 1;


  static Context context;
  List<SharModel> movies;
  NewsViewHolderBanner holderBanner;
  int pager;


  public AdapterMainItem(Context context, List<SharModel> movies, int pager) {
    this.context = context;
    this.movies = movies;
    this.pager = pager;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_HEADER:
        View view = LayoutInflater.from(context).inflate(R.layout.item_namyeh_header, parent, false);
        return new NewsViewHolderBanner(view, pager);
      case VIEW_TYPE_DEFAULT_ITEM:
        View view2 = LayoutInflater.from(context).inflate(R.layout.item_namyeh, parent, false);
        return new MovieHolder(view2);
      default:
        return null;
    }

  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holde, final int position) {
    if (holde instanceof MovieHolder) {
      final MovieHolder holder = (MovieHolder) holde;
      final SharModel post = movies.get(position - 1);
      holder.txtName.setText(post.getName());
      holder.txtMain.setText(post.getDetail());
      holder.card.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(context, JazebehActivity.class);
          intent.putExtra("ID", post.getId());
          context.startActivity(intent);
        }
      });

      try {
        InputStream ims = context.getAssets().open("pic/" + post.getPicUrl() + ".jpg");
        Drawable d = Drawable.createFromStream(ims, null);
        holder.urlImageMain.setImageDrawable(d);
        ims.close();
      } catch (IOException ex) {
        Log.i("BUG", ex.toString());
      }


    } else {

      holderBanner = (NewsViewHolderBanner) holde;
      AndroidImageAdapter1 adapterView = new AndroidImageAdapter1(context);
      if (MainActivity.mainItem != 1) {
        MainActivity.mainItem = 1;
        holderBanner.mViewPager.setAdapter(adapterView);
        Thread t = new Thread() {
          public void run() {
            for (int i = 0; i <= 4; i++) {
              try {
                final int a = i;
                ((Activity) context).runOnUiThread(new Runnable() {
                  public void run() {
                    holderBanner.mViewPager.setCurrentItem(a);
                  }
                });
                System.out.println("Value of i= " + i);
                sleep(3000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              if (i == 4) {
                Log.i("LLL", "" + i);
                i = 0;
              }


            }
          }
        };
        t.start();
      }
    }
  }

  @Override
  public int getItemCount() {
    return movies.size() + 1;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == 0) {
      return VIEW_TYPE_HEADER;
    } else {
      return VIEW_TYPE_DEFAULT_ITEM;
    }

  }

  /* VIEW HOLDERS */
  public class NewsViewHolderBanner extends RecyclerView.ViewHolder {

    private ImageView imgPersonal;
    private ViewPager mViewPager;

    public NewsViewHolderBanner(View itemView, int pager) {
      super(itemView);
      mViewPager = (ViewPager) itemView.findViewById(R.id.view_page);
      if (pager == 1) {
        mViewPager.setVisibility(View.GONE);
      }
      //imgPersonal = (ImageView) itemView.findViewById(R.id.img_pesonal);
    }

  }

  static class MovieHolder extends RecyclerView.ViewHolder {
    private TextView txtName;
    private TextView txtMain;
    private ImageView urlImageMain;
    private CardView card;

    public MovieHolder(View itemView) {
      super(itemView);
      txtName = (TextView) itemView.findViewById(R.id.txt_name);
      txtMain = (TextView) itemView.findViewById(R.id.txt_main_item);
      urlImageMain = (ImageView) itemView.findViewById(R.id.img_main_item);
      card = (CardView) itemView.findViewById(R.id.card_item_message);
    }


  }
}
