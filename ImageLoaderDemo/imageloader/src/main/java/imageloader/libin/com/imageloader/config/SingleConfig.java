package imageloader.libin.com.imageloader.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import java.io.File;

import imageloader.libin.com.imageloader.utils.MyUtil;

/**
 * Created by doudou on 2017/4/10.
 */

public class SingleConfig {
    public Context getContext() {
        if(context==null){
            context = GlobalConfig.context;
        }
        return context;
    }

    private Context context;

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

    public int getHeight() {
        if(height<=0){
            //先去imageview里取,如果为0,则赋值成matchparent
            if(target!=null){
                height=  target.getMeasuredWidth();
            }
            if(height<=0){
                height=GlobalConfig.getWinWidth();
            }
        }

        return height;
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

    public int getWidth() {
        if(width<=0){
            //先去imageview里取,如果为0,则赋值成matchparent
            if(target!=null){
                width=  target.getMeasuredWidth();
            }
            if(width<=0){
                width=GlobalConfig.getWinWidth();
            }
        }
        return width;
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

    private  boolean ignoreCertificateVerify ;
    private String url;

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    private String thumbnailUrl;//小图的url
    private String filePath;
    private int resId;
    private String contentProvider;
    private boolean isGif;

    private View target;

    private int width;
    private int height;

    private boolean needBlur ;//是否需要模糊
    private int blurRadius;

    //UI:
    private int placeHolderResId;
    private boolean reuseable;

    public int getLoadingResId() {
        return loadingResId;
    }

    public int getErrorResId() {
        return errorResId;
    }

    private int loadingResId;
    private int errorResId;

    public boolean isReuseable() {
        return reuseable;
    }



    private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
    private int rectRoundRadius;//圆角矩形时圆角的半径

    private int roundOverlayColor;//圆角/圆外覆盖一层背景色
    private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

    private int borderWidth;//边框的宽度
    private int borderColor;//边框颜色

    public boolean isAsBitmap() {
        return asBitmap;
    }

    private boolean asBitmap;//只获取bitmap

    public void setBitmapListener(BitmapListener bitmapListener) {
        this.bitmapListener = MyUtil.getBitmapListenerProxy(bitmapListener);
    }

    private BitmapListener bitmapListener;

    private void show(){
        GlobalConfig.getLoader().request(this);
    }


    public boolean isGif() {
        return isGif;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public SingleConfig(ConfigBuilder builder){
        this.url = builder.url;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.filePath = builder.filePath;
        this.resId = builder.resId;
        this.contentProvider = builder.contentProvider;

        this.ignoreCertificateVerify = builder.ignoreCertificateVerify;

        this.target = builder.target;
        this.width = builder.width;
        this.height = builder.height;

        this.shapeMode = builder.shapeMode;
        if(shapeMode== ShapeMode.RECT_ROUND){
            this.rectRoundRadius = builder.rectRoundRadius;
        }
        this.scaleMode = builder.scaleMode;

        this.needBlur = builder.needBlur;
        this.placeHolderResId = builder.placeHolderResId;
        this.borderWidth = builder.borderWidth;
        if(borderWidth>0){
            this.borderColor = builder.borderColor;
        }


        this.asBitmap = builder.asBitmap;
        this.bitmapListener = builder.bitmapListener;

        this.roundOverlayColor = builder.roundOverlayColor;
        this.isGif = builder.isGif;
        this.blurRadius = builder.blurRadius;
        this.reuseable = builder.reuseable;
        this.loadingResId = builder.loadingResId;
        this.errorResId = builder.errorResId;
    }


    public interface BitmapListener{
        void onSuccess(Bitmap bitmap);
        void onFail();
    }

    public static class ConfigBuilder{
        private Context context;

        private  boolean ignoreCertificateVerify = GlobalConfig.ignoreCertificateVerify;

        //图片源
        /**
         * 类型	SCHEME	示例
         远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案
         本地文件	file://	FileInputStream
         Content provider	content://	ContentResolver
         asset目录下的资源	asset://	AssetManager
         res目录下的资源	res://	Resources.openRawResource
         Uri中指定图片数据	data:mime/type;base64,	数据类型必须符合 rfc2397规定 (仅支持 UTF-8)
         * @param config
         * @return
         */
        private String url;
        private String thumbnailUrl;//小图的url
        private String filePath;
        private int resId;
        private String contentProvider;
        private boolean isGif=false;

        private View target;
        private boolean asBitmap;//只获取bitmap
        private BitmapListener bitmapListener;

        private int width;
        private int height;

        private boolean needBlur = false;//是否需要模糊
        private int blurRadius;

        //UI:
        private int placeHolderResId;
        private boolean reuseable;//当前view是不是可重用的

        private int loadingResId;
        private int errorResId;

        private int shapeMode;//默认矩形,可选直角矩形,圆形/椭圆
        private int rectRoundRadius;//圆角矩形时圆角的半径

        public ConfigBuilder setRoundOverlayColor(int roundOverlayColor) {
            this.roundOverlayColor = roundOverlayColor;
            return this;
        }

        private int roundOverlayColor;//圆角/圆外覆盖一层背景色
        private int scaleMode;//填充模式,默认centercrop,可选fitXY,centerInside...

        private int borderWidth;//边框的宽度
        private int borderColor;//边框颜色

        public ConfigBuilder(Context context){
            this.context = context;
        }

        public ConfigBuilder ignoreCertificateVerify(boolean ignoreCertificateVerify){
            this.ignoreCertificateVerify = ignoreCertificateVerify;
            return this;
        }

        /**
         * 设置网络路径
         * @param url
         * @return
         */
        public ConfigBuilder url(String url){
            this.url = url;
            if(url.contains("gif")){
                isGif = true;
            }
            return this;
        }

        /**
         * 缩略图
         * @param thumbnailUrl
         * @return
         */
        public ConfigBuilder thumbnail(String thumbnailUrl){
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }


        public ConfigBuilder loading(int  loadingResId){
            this.loadingResId = loadingResId;
            return this;
        }

        /**
         * error图
         * @param errorResId
         * @return
         */
        public ConfigBuilder error(int  errorResId){
            this.errorResId = errorResId;
            return this;
        }

        public ConfigBuilder file(String filePath){
            if(filePath.startsWith("content:")){
                this.contentProvider = filePath;
                return this;
            }

            if(!new File(filePath).exists()){
                //throw new RuntimeException("文件不存在");
                Log.e("imageloader","文件不存在");
                return this;
            }

            this.filePath = filePath;
            if(filePath.contains("gif")){
                isGif = true;
            }
            return this;
        }

        public ConfigBuilder res(int resId){
            this.resId = resId;
            return this;
        }
        public ConfigBuilder content(String contentProvider){
            this.contentProvider = contentProvider;
            return this;
        }

        public void into(View targetView){
            this.target = targetView;
            new SingleConfig(this).show();
        }

        public void asBitmap(BitmapListener bitmapListener){
            this.bitmapListener = MyUtil.getBitmapListenerProxy(bitmapListener);
            this.asBitmap = true;
            new SingleConfig(this).show();
        }

        /**
         * dp单位
         * @param width
         * @param height
         * @return
         */
        public ConfigBuilder widthHeight(int width,int height){
            this.width = MyUtil.dip2px(width);
            this.height = MyUtil.dip2px(height);
            return this;
        }

        /**
         * 占位图
         * @param placeHolderResId
         * @param reuseable
         * @return
         */
        public ConfigBuilder placeHolder(int placeHolderResId,boolean reuseable){
            this.placeHolderResId = placeHolderResId;
            this.reuseable = reuseable;
            return this;
        }


        /**
         * 是否需要高斯模糊
         * @return
         */
        public ConfigBuilder blur(int blurRadius){
            this.needBlur = true;
            this.blurRadius = blurRadius;
            return this;
        }

        /**
         * 圆角
         * @param overlayColorWhenGif
         * @return
         */
        public ConfigBuilder asCircle(int overlayColorWhenGif){
            this.shapeMode = ShapeMode.OVAL;
            this.roundOverlayColor  = overlayColorWhenGif;
            return this;
        }

        /**
         * 形状为圆角矩形时的圆角半径
         * @param rectRoundRadius
         * @return
         */
        public ConfigBuilder rectRoundCorner(int rectRoundRadius,int overlayColorWhenGif){
            this.rectRoundRadius = MyUtil.dip2px(rectRoundRadius);
            this.shapeMode = ShapeMode.RECT_ROUND;
            this.roundOverlayColor  = overlayColorWhenGif;
            return this;
        }

        /**
         * 拉伸/裁剪模式
         * @param scaleMode 取值ScaleMode
         * @return
         */
        public ConfigBuilder scale(int scaleMode){
            this.scaleMode = scaleMode;
            return this;
        }

        /**
         * 设置边框
         * @param borderWidth
         * @param borderColor
         * @return
         */
        public ConfigBuilder border(int borderWidth,int borderColor){
            this.borderWidth = MyUtil.dip2px(borderWidth);
            this.borderColor = borderColor;
            return this;
        }

    }

}
