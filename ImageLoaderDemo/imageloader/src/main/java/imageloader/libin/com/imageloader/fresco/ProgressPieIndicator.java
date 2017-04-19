package imageloader.libin.com.imageloader.fresco;

import android.text.TextUtils;
import android.view.View;

import com.github.piasy.biv.indicator.ProgressIndicator;
import com.github.piasy.biv.view.BigImageView;

import imageloader.libin.com.imageloader.R;

/**
 * Created by doudou on 2017/4/10.
 */

public class ProgressPieIndicator implements ProgressIndicator {
    // private ProgressBar progressBar;
    private View root;
    private String cacheFile;
    //private TextView textView;
    @Override
    public View getView(BigImageView parent) {
        View view = View.inflate(parent.getContext(), R.layout.progress,null);
        cacheFile = parent.currentImageFile();
        if(TextUtils.isEmpty(cacheFile)){
            cacheFile = parent.currentImageFile();
        }else if(cacheFile.equals(parent.currentImageFile())){

        }
        // progressBar = (ProgressBar) view.findViewById(R.id.progressBar00);
        //textView = (TextView) view.findViewById(R.id.tv_progress);
        root = view;
        return view;
    }

    @Override
    public void onStart() {
        if(root!=null)
            root.setVisibility(View.VISIBLE);
        //progressBar.setMax(100);
        // textView.setText("加载进度:0%");


    }

    @Override
    public void onProgress(int progress) {
        // progressBar.setProgress(progress);
        // Log.e("progressdd",progress+"");
        // textView.setText("加载进度:"+progress+"%");

    }




    @Override
    public void onFinish() {
        if(root!=null)
            root.setVisibility(View.GONE);

    }
}
