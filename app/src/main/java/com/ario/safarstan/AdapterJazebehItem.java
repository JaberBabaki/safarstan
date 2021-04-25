package com.ario.safarstan;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ario.safarstan.model.JazebehHaModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by jaber
 */
public class AdapterJazebehItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int VIEW_TYPE_HEADER = 0;
  private static final int VIEW_TYPE_DEFAULT_ITEM = 1;


  static Context context;
  List<JazebehHaModel> movies;


  public AdapterJazebehItem(Context context, List<JazebehHaModel> movies) {
    this.context = context;
    this.movies = movies;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_DEFAULT_ITEM:
        View view2 = LayoutInflater.from(context).inflate(R.layout.item_namyeh, parent, false);
        return new MovieHolder(view2);
      default:
        return null;
    }

  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holde, final int position) {
      final MovieHolder holder = (MovieHolder) holde;
      final JazebehHaModel post = movies.get(position );
       holder.txtName.setText(post.getTitle());
       holder.txtMain.setText(post.getDetail());
       holder.card.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
           Intent intent = new Intent(context, DetailJazebehActivity.class);
           intent.putExtra("ID",post.getId());
           context.startActivity(intent);
         }
       });

      try
      {
        if(post.getPicUrl()==null||!post.getPicUrl().contains("p")){
          holder.urlImageMain.setImageResource(R.drawable.no_image);
        }else {
          InputStream ims = context.getAssets().open("pic/" + post.getPicUrl() + ".jpg");
          Drawable d = Drawable.createFromStream(ims, null);
          holder.urlImageMain.setImageDrawable(d);
          ims.close();
        }
      }
      catch(IOException ex)
      {
        Log.i("BUG",ex.toString());
      }


  }

  @Override
  public int getItemCount() {
    return movies.size() ;
  }

  @Override
  public int getItemViewType(int position) {
      return VIEW_TYPE_DEFAULT_ITEM;

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
