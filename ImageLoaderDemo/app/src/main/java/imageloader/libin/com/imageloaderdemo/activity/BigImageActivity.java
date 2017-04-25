package imageloader.libin.com.imageloaderdemo.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;

import imageloader.libin.com.imageloaderdemo.R;
import imageloader.libin.com.images.config.PriorityMode;
import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.loader.ImageLoader;


public class BigImageActivity extends AppCompatActivity {
    ImageView iv_round;
    ImageView iv_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);

        iv_round = (ImageView) findViewById(R.id.iv_round);
        iv_circle = (ImageView) findViewById(R.id.iv_circle);

        show();

    }

    private void show() {

        ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                // if it's a custom view class, cast it here
                // then find subviews and do the animations
                // here, we just use the entire view for the fade animation
                view.setAlpha( 0f );

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
                fadeAnim.setDuration( 2500 );
                fadeAnim.start();
            }
        };

        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(0);

        Glide.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")

               // .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
                .load("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")

                //.thumbnail("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")
                // .res(R.drawable.thegif)
                //.centerCrop()
                .placeholder(R.mipmap.ic_launcher)
               // .placeHolder(R.mipmap.ic_launcher)
               //.rectRoundCorner(40, R.color.colorPrimary)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.thumbnail(0.5f)
                //.fitCenter()
                //.crossFade(3000)
                //.animate(animation)
                .priority(Priority.IMMEDIATE)
                .centerCrop()
                .into(iv_round);

        ImageLoader.with(this)
                //.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")
                .url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")

                //.url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490944508&di=671845045c66356487c1a539c4ed0717&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.letv.com%2Fforum%2F201606%2F27%2F185306g84m4gsxztvzxjt5.jpg")
               // .url("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2796659031,1466769776&fm=80&w=179&h=119&img.JPEG")
                //.thumbnail("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")
                // .res(R.drawable.thegif)
                .placeHolder(R.mipmap.ic_launcher)
               //.rectRoundCorner(40, R.color.colorPrimary)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                //.thumbnail(0.9f)
                .animate(animation)
                .scale(ScaleMode.CENTER_CROP)
               // .animate(android.R.anim.slide_in_left)
                .priority(PriorityMode.PRIORITY_LOW)
                .into(iv_circle);


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
