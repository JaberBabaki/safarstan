package com.ario.safarstan.customView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ario.safarstan.R;


public class AndroidImageAdapter1 extends PagerAdapter {
  Context mContext;

  public AndroidImageAdapter1(Context context) {
    this.mContext = context;
  }

  @Override
  public int getCount() {
    return sliderImagesId.length;
  }

  private int[] sliderImagesId = new int[]{
    R.drawable.a12, R.drawable.a13,
    R.drawable.a14, R.drawable.a15,R.drawable.a16
  };

  @Override
  public boolean isViewFromObject(View v, Object obj) {
    return v == ((ImageView) obj);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int i) {
    ImageView mImageView = new ImageView(mContext);
    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    mImageView.setImageResource(sliderImagesId[i]);
    ((ViewPager) container).addView(mImageView, 0);
    return mImageView;
  }

  @Override
  public void destroyItem(ViewGroup container, int i, Object obj) {
    ((ViewPager) container).removeView((ImageView) obj);
  }
}