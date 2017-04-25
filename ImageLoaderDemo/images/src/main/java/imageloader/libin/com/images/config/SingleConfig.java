package imageloader.libin.com.images.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.orhanobut.logger.Logger;

import java.io.File;

import imageloader.libin.com.images.utils.MyUtil;

/**
 * Created by doudou on 2017/4/10.
 */

public class SingleConfig {
    private Context context;
    private boolean ignoreCertificateVerify; //https是否忽略校验
    private String url;
    private float thumbnail; //缩略图缩放倍数
    private String filePath; //文件路径
    private int resId;  //资源id
    private String contentProvider; //内容提供者
    private boolean isGif; //是否是GIF图
    private View target;
    private int width;
    private int height;
    private int oWidth;
    private int oHeight;
    private boolean needBlur;//是否需要模糊

    private int priority;

    private int animationType;
    private int animationId;
    private Animation animation;

    private ViewPropertyAnimation.Animator animator;

    private int blurRadius;
    private int placeHolderResId;
    private int errorResId;
    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径
    private DiskCacheStrategy diskCacheStrategy;//是否跳过磁盘存储
    private int roundOverlayColor;//圆角/圆外覆盖一层背景色
    private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...
    private int borderWidth;//边框的宽度
    private int borderColor;//边框颜色

    private BitmapListener bitmapListener;

    public SingleConfig(ConfigBuilder builder) {
        this.url = builder.url;
        this.thumbnail = builder.thumbnail;
        this.filePath = builder.filePath;
        this.resId = builder.resId;
        this.contentProvider = builder.contentProvider;

        this.ignoreCertificateVerify = builder.ignoreCertificateVerify;

        this.target = builder.target;

        this.width = builder.width;
        this.height = builder.height;

        this.oWidth = builder.oWidth;
        this.oHeight = builder.oHeight;

        this.shapeMode = builder.shapeMode;
        if (shapeMode == ShapeMode.RECT_ROUND) {
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.scaleMode = builder.scaleMode;

        this.diskCacheStrategy = builder.diskCacheStrategy;

        this.animationId = builder.animationId;
        this.animationType = builder.animationType;
        this.animator = builder.animator;
        this.animation = builder.animation;

        this.priority = builder.priority;

        this.needBlur = builder.needBlur;
        this.placeHolderResId = builder.placeHolderResId;
        this.borderWidth = builder.borderWidth;
        if (borderWidth > 0) {
            this.borderColor = builder.borderColor;
        }

        this.asBitmap = builder.asBitmap;
        this.bitmapListener = builder.bitmapListener;

        this.roundOverlayColor = builder.roundOverlayColor;
        this.isGif = builder.isGif;
        this.blurRadius = builder.blurRadius;
        this.errorResId = builder.errorResId;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    private boolean asBitmap;//只获取bitmap

    public Context getContext() {
        if (context == null) {
            context = GlobalConfig.context;
        }
        return context;
    }

    public DiskCacheStrategy getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public String getContentProvider() {
        return contentProvider;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isNeedBlur() {
        return needBlur;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public int getRectRoundRadius() {
        return rectRoundRadius;
    }

    public int getResId() {
        return resId;
    }

    public int getScaleMode() {
        return scaleMode;
    }

    public int getShapeMode() {
        return shapeMode;
    }

    public View getTarget() {
        return target;
    }

    public String getUrl() {
        return url;
    }

    public int getHeight() {
        if (height <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                height = target.getMeasuredWidth();
            }
            if (height <= 0) {
                height = GlobalConfig.getWinHeight();
            }
        }
        Logger.e("getHeight  :" + height);

        return height;
    }

    public int getWidth() {
        if (width <= 0) {
            //先去imageview里取,如果为0,则赋值成matchparent
            if (target != null) {
                width = target.getMeasuredWidth();
            }
            if (width <= 0) {
                width = GlobalConfig.getWinWidth();
            }
        }

        Logger.e("getWidth  :" + width);
        return width;
    }

    public int getoWidth() {
        return oWidth;
    }

    public int getoHeight() {
        return oHeight;
    }

    public int getAnimationType() {
        return animationType;
    }

    public int getAnimationId() {
        return animationId;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getPriority() {
        return priority;
    }

    public ViewPropertyAnimation.Animator getAnimator() {
        return animator;
    }

    public int getRoundOverlayColor() {
        return roundOverlayColor;
    }

    public boolean isIgnoreCertificateVerify() {
        return ignoreCertificateVerify;
    }

    public BitmapListener getBitmapListener() {

        return bitmapListener;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public void setBitmapListener(BitmapListener bitmapListener) {
        this.bitmapListener = MyUtil.getBitmapListenerProxy(bitmapListener);
    }

    private void show() {
        GlobalConfig.getLoader().request(this);
    }

    public boolean isGif() {
        return isGif;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public interface BitmapListener {
        void onSuccess(Bitmap bitmap);

        void onFail();
    }

    public static class ConfigBuilder {
        private Context context;

        private boolean ignoreCertificateVerify = GlobalConfig.ignoreCertificateVerify;

        /**
         * 图片源
         * 类型	SCHEME	示例
         * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
         * 本地文件	file://	FileInputStream
         * Content provider	content://	ContentResolver
         * asset目录下的资源	asset://	AssetManager
         * res目录下的资源	  res://	Resources.openRawResource
         * Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
         *
         * @param config
         * @return
         */
        private String url;
        private float thumbnail;
        private String filePath;
        private int resId;
        private String contentProvider;
        private boolean isGif = false;

        private View target;
        private boolean asBitmap;//只获取bitmap
        private BitmapListener bitmapListener;

        // TODO: 2017/4/24 宽高的获取
        private int width;
        private int height;

        private int oWidth; //选择加载分辨率的宽
        private int oHeight; //选择加载分辨率的高

        private boolean needBlur = false;//是否需要模糊
        private int blurRadius;

        //UI:
        private int placeHolderResId;

        private int errorResId;

        private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
        private int rectRoundRadius;//圆角矩形时圆角的半径

        private DiskCacheStrategy diskCacheStrategy;

        private int roundOverlayColor;//圆角/圆外覆盖一层背景色
        private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

        private int priority; //请求优先级

        public int animationId; //动画资源id
        public int animationType; //动画资源Type
        public Animation animation; //动画资源
        public ViewPropertyAnimation.Animator animator; //动画资源id

        private int borderWidth;//边框的宽度
        private int borderColor;//边框颜色


        public ConfigBuilder(Context context) {
            this.context = context;
        }

        public ConfigBuilder setRoundOverlayColor(int roundOverlayColor) {
            this.roundOverlayColor = roundOverlayColor;
            return this;
        }

        public ConfigBuilder ignoreCertificateVerify(boolean ignoreCertificateVerify) {
            this.ignoreCertificateVerify = ignoreCertificateVerify;
            return this;
        }

        /**
         * 设置网络路径
         *
         * @param url
         * @return
         */
        public ConfigBuilder url(String url) {
            this.url = url;
            if (url.contains("gif")) {
                isGif = true;
            }
            return this;
        }

        /**
         * 缩略图
         * @param thumbnail
         * @return
         */
        public ConfigBuilder thumbnail(float thumbnail){
            this.thumbnail = thumbnail;
            return this;
        }

        /**
         * error图
         *
         * @param errorResId
         * @return
         */
        public ConfigBuilder error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public ConfigBuilder file(String filePath) {
            if (filePath.startsWith("content:")) {
                this.contentProvider = filePath;
                return this;
            }

            if (!new File(filePath).exists()) {
                //throw new RuntimeException("文件不存在");
                Log.e("imageloader", "文件不存在");
                return this;
            }

            this.filePath = filePath;
            if (filePath.contains("gif")) {
                isGif = true;
            }
            return this;
        }

        public ConfigBuilder res(int resId) {
            this.resId = resId;
            return this;
        }

        public ConfigBuilder content(String contentProvider) {
            this.contentProvider = contentProvider;
            return this;
        }

        public void into(View targetView) {
            this.target = targetView;
            new SingleConfig(this).show();
        }

        public void asBitmap(BitmapListener bitmapListener) {
            this.bitmapListener = MyUtil.getBitmapListenerProxy(bitmapListener);
            this.asBitmap = true;
            new SingleConfig(this).show();
        }

        /**
         * 加载图片的分辨率
         * @param oWidth
         * @param oHeight
         * @return
         */
        public ConfigBuilder override(int oWidth, int oHeight) {
            this.oWidth = MyUtil.dip2px(oWidth);
            this.oHeight = MyUtil.dip2px(oHeight);

            Logger.e("width : " +oWidth  + "    oHeight : " +oHeight);
            return this;
        }

        /**
         * 占位图
         *
         * @param placeHolderResId
         * @return
         */
        public ConfigBuilder placeHolder(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }


        /**
         * 是否需要高斯模糊
         *
         * @return
         */
        public ConfigBuilder blur(int blurRadius) {
            this.needBlur = true;
            this.blurRadius = blurRadius;
            return this;
        }

        /**
         * 圆角
         *
         * @param overlayColorWhenGif
         * @return
         */
        public ConfigBuilder asCircle(int overlayColorWhenGif) {
            this.shapeMode = ShapeMode.OVAL;
            this.roundOverlayColor = overlayColorWhenGif;
            return this;
        }

        /**
         * 形状为圆角矩形时的圆角半径
         *
         * @param rectRoundRadius
         * @return
         */
        public ConfigBuilder rectRoundCorner(int rectRoundRadius, int overlayColorWhenGif) {
            this.rectRoundRadius = MyUtil.dip2px(rectRoundRadius);
            this.shapeMode = ShapeMode.RECT_ROUND;
            this.roundOverlayColor = overlayColorWhenGif;
            return this;
        }

        /**
         * 跳过磁盘缓存
         */
        public ConfigBuilder diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        /**
         * 拉伸/裁剪模式
         *
         * @param scaleMode 取值ScaleMode
         * @return
         */
        public ConfigBuilder scale(int scaleMode) {
            this.scaleMode = scaleMode;
            return this;
        }


        public ConfigBuilder animate(int animationId) {
            this.animationType = AnimationMode.ANIMATIONID;
            this.animationId = animationId;
            return this;
        }

        public ConfigBuilder animate(ViewPropertyAnimation.Animator animator) {
            this.animationType = AnimationMode.ANIMATOR;
            this.animator = animator;
            return this;
        }

        public ConfigBuilder animate(Animation animation) {
            this.animationType = AnimationMode.ANIMATION;
            this.animation = animation;
            return this;
        }

        public ConfigBuilder priority(int priority) {
            this.priority = priority;

            return this;
        }
    }



}
