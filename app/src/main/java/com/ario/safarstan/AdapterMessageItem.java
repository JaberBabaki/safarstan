package com.ario.safarstan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ario.safarstan.model.MessageModel;

import java.util.List;

/**
 * Created by jaber
 */
public class AdapterMessageItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int VIEW_TYPE_HEADER = 0;
  private static final int VIEW_TYPE_DEFAULT_ITEM = 1;


  static Context context;
  List<MessageModel> movies;


  public AdapterMessageItem(Context context, List<MessageModel> movies) {
    this.context = context;
    this.movies = movies;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //LayoutInflater inflater = LayoutInflater.from(context);
    switch (viewType) {
      case VIEW_TYPE_DEFAULT_ITEM:
        View view2 = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MovieHolder(view2);
      default:
        return null;
    }

  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holde, final int position) {
      final MovieHolder holder = (MovieHolder) holde;
      final MessageModel post = movies.get(position );
       holder.txtName.setText(post.getTitle());
       holder.txtMain.setText(post.getMessage());
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

    public MovieHolder(View itemView) {
      super(itemView);
      txtName = (TextView) itemView.findViewById(R.id.txt_name);
      txtMain = (TextView) itemView.findViewById(R.id.txt_main_item);
    }


  }
}
