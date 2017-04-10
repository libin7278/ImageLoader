package imageloader.libin.com.imageloader.bigimage;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.piasy.biv.view.BigImageView;

import java.util.List;

import imageloader.libin.com.imageloader.ImageLoader;

/**
 * Created by doudou on 2017/4/10.
 */

public class RecycleAdapterForBigImage extends RecyclerView.Adapter<RecycleAdapterForBigImage.MyViewHolder> {
    List<String> urls;

    public RecycleAdapterForBigImage(List<String> urls){
        this.urls = urls;
    }


    @Override
    public RecycleAdapterForBigImage.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BigImageView  imageView = new BigImageView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);

        MyViewHolder viewHolder = new MyViewHolder(imageView);
        Log.e("big","onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleAdapterForBigImage.MyViewHolder holder, int position) {
        ImageLoader.loadBigImage(holder.bigImageView,urls.get(position));
        Log.e("big","onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        BigImageView bigImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            bigImageView = (BigImageView) itemView;
        }
    }
}
