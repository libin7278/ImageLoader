package imageloader.libin.com.imageloader.bigimage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.view.BigImageView;

import java.util.LinkedList;
import java.util.List;

import imageloader.libin.com.imageloader.ImageLoader;

/**
 * Created by doudou on 2017/4/10.
 */

public class PagerAdapterForBigImage extends PagerAdapter {
    List<String> urls;
    private LinkedList<BigImageView> mViewCache = null;


    public PagerAdapterForBigImage(List<String> urls){
        this.urls = urls;
        mViewCache = new LinkedList<>();
    }

    public void changeDatas(List<String> urls){
        if(urls!=null){
            this.urls.clear();
            this.urls.addAll(urls);
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        Log.e("instantiateItem","postion:"+position);
        BigImageView imageView = null;

        if(mViewCache.size() == 0){
            imageView = new BigImageView(container.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
        }else {
            imageView = mViewCache.removeFirst();
        }

        String url = urls.get(position);
        ImageLoader.loadBigImage(imageView,url);
        if(imageView.getParent()!=null){//多加一层判断,比较保险
            ViewGroup viewGroup = (ViewGroup) imageView.getParent();
            viewGroup.removeView(imageView);
        }
        container.addView(imageView);
        return imageView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        Log.e("destroyItem","postion------------------:"+position);
        // BigImageView imageView = mViews.get(i);
        //GlobalConfig.getLoader().clearMomoryCache((View) object);
        BigImageView contentView = (BigImageView) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);


    }


}