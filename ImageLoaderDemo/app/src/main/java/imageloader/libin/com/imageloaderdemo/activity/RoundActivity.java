package imageloader.libin.com.imageloaderdemo.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import imageloader.libin.com.imageloaderdemo.R;
import imageloader.libin.com.images.config.PriorityMode;
import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.loader.ImageLoader;

public class RoundActivity extends AppCompatActivity {
    ImageView iv_round;
    ImageView iv_circle;
    ImageView iv_circle1;
    ImageView iv_circle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        iv_round = (ImageView) findViewById(R.id.iv_round);
        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        iv_circle1 = (ImageView) findViewById(R.id.iv_circle1);
        iv_circle2 = (ImageView) findViewById(R.id.iv_circle2);


        Logger.e("iv_round.getWidth() : " + iv_round.getWidth());

        Logger.e(" iv_round.getMeasuredWidth() : " + iv_round.getMeasuredWidth());


//        ImageLoader.pauseRequests();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ImageLoader.resumeRequests();
//            }
//        }, 10000);
//
        show();


    }

    private void show() {
//        Glide.with(this)
//                .load("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
//                .override(20,20)
//                .into(iv_round);

        ImageLoader.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")
                //.url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")

                .url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
                //.url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
                //.thumbnail(0.1f)
                // .res(R.drawable.thegif)
        .pixelationFilter(20f)
                .priority(PriorityMode.PRIORITY_IMMEDIATE)
                .placeHolder(R.mipmap.ic_launcher)
                .rectRoundCorner(10, R.color.colorPrimary)
                // .override(20,20)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                .scale(ScaleMode.FIT_CENTER)
                .blur(1)
                .invertFilter()
                //.grayscaleFilter()
                .brightnessFilter(0.5f)
                //.colorFilter(Color.argb(80, 255, 0, 0))
                // .border(100, Color.BLACK)
                //.priority(PriorityMode.PRIORITY_LOW)
                .into(iv_round);

        ImageLoader.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")


                .url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
                //.url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
                //.contrastFilter(10f)
        .sketchFilter()
                // .res(R.drawable.thegif)
                //.placeHolder(R.mipmap.ic_launcher)
                //.rectRoundCorner(50, R.color.colorPrimary)
                // .override(20,20)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                .swirlFilter()
                //             .scale(ScaleMode.CENTER)
                //               .blur(40)
                //.priority(PriorityMode.PRIORITY_HIGH)
                //.colorFilter(Color.argb(80, 255, 0, 0))
                .brightnessFilter(0.1f)
                //.border(100, Color.BLACK)
                .into(iv_circle);


        ImageLoader.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")

                .url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
                //.url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
                // .thumbnail(0.1f)
                // .res(R.drawable.thegif)
                .placeHolder(R.mipmap.ic_launcher)
                //.rectRoundCorner(50, R.color.colorPrimary)
                // .override(20,20)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.scale(ScaleMode.CENTER)
                //               .blur(40)
                .asSquare()
                .priority(PriorityMode.PRIORITY_IMMEDIATE)
                //.border(100, Color.BLACK)
                .sepiaFilter()
                .vignetteFilter()
                .contrastFilter(0.5f)
                .into(iv_circle1);

        ImageLoader.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")

                //.url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
                .url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
                // .thumbnail(0.1f)
                // .res(R.drawable.thegif)
                .placeHolder(R.mipmap.ic_launcher)
                .rectRoundCorner(50, R.color.colorPrimary)
                // .override(20,20)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.scale(ScaleMode.CENTER)
                //               .blur(40)
                .toonFilter()
                //.border(100, Color.BLACK)
                .into(iv_circle2);


//        ImageLoader.with(this)
//                .placeHolder(R.mipmap.ic_launcher,false)
//                //.res(R.drawable.thegif)
//                .url("https://pic1.zhimg.com/v2-7868c606d6ddddbdd56f0872e514925c_b.jpg")
//                .widthHeight(250, 150)
//                .rectRoundCorner(15, R.color.colorPrimary)
//                .blur(20)
//                .asBitmap(new SingleConfig.BitmapListener() {
//                    @Override
//                    public void onSuccess(Bitmap bitmap) {
//                        Log.e("bitmap", bitmap.getWidth() + "---height:" + bitmap.getHeight() + "--" + bitmap.toString());
//                        iv_round.setImageBitmap(bitmap);
//                    }
//
//                    @Override
//                    public void onFail() {
//                        Log.e("bitmap", "fail");
//
//                    }
//                });
////        .into(ivRes);


    }
}
