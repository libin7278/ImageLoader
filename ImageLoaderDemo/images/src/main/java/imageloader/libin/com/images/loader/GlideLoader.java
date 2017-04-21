package imageloader.libin.com.images.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import imageloader.libin.com.images.config.GlobalConfig;
import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.config.ShapeMode;
import imageloader.libin.com.images.config.SingleConfig;
import imageloader.libin.com.images.utils.MyUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by doudou on 2017/4/10.
 * 参考:
 * https://mrfu.me/2016/02/28/Glide_Sries_Roundup/
 */

public class GlideLoader implements ILoader {

    /**
     * @param context        上下文
     * @param cacheSizeInM   Glide默认磁盘缓存最大容量250MB
     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
     * @param isInternalCD   true 磁盘缓存到应用的内部目录 / false 磁盘缓存到外部存
     */
    @Override
    public void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        Glide.get(context).setMemoryCategory(memoryCategory); //如果在应用当中想要调整内存缓存的大小，开发者可以通过如下方式：
        GlideBuilder builder = new GlideBuilder(context);
        if (isInternalCD) {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        } else {
            builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheSizeInM * 1024 * 1024));
        }
    }

    @Override
    public void request(final SingleConfig config) {
        RequestManager requestManager = Glide.with(config.getContext());
        DrawableTypeRequest request = getDrawableTypeRequest(config, requestManager);

        if (config.isAsBitmap()) {
            SimpleTarget target = new SimpleTarget<Bitmap>(config.getWidth(), config.getHeight()) {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    config.getBitmapListener().onSuccess(bitmap);
                }
            };

            setShapeModeAndBlur(config, request);

            if(config.getDiskCacheStrategy() != null){
                request.diskCacheStrategy(config.getDiskCacheStrategy());
                Log.e("GlideLoader" , "config.getDiskCacheStrategy() :  " +config.getDiskCacheStrategy());
            }


            request.asBitmap().into(target);

        } else {

            if (request == null) {
                return;
            }

            if (MyUtil.shouldSetPlaceHolder(config)) {
                request.placeholder(config.getPlaceHolderResId());
            }

            int scaleMode = config.getScaleMode();

            switch (scaleMode) {
                case ScaleMode.CENTER_CROP:
                    request.centerCrop();
                    break;
                case ScaleMode.CENTER_INSIDE:
                    request.centerCrop();
                    break;
                case ScaleMode.FIT_CENTER:
                    request.fitCenter();
                    break;
                case ScaleMode.FIT_XY:
                    request.centerCrop();
                    break;
                case ScaleMode.FIT_END:
                    request.centerCrop();
                    break;
                case ScaleMode.FOCUS_CROP:
                    request.centerCrop();
                    break;
                case ScaleMode.CENTER:
                    request.centerCrop();
                    break;
                case ScaleMode.FIT_START:
                    request.centerCrop();
                    break;
                default:
                    request.centerCrop();
                    break;
            }


            setShapeModeAndBlur(config, request);

            // TODO: 2017/4/21 设置图片滤镜

            // TODO: 2017/4/21  thumbnail(0.1f)属性抛出去给用户自己设置，在加载圆角的时候去
//            request.override(config.getWidth(), config.getHeight())
//                    .thumbnail(0.1f);

            if(config.getThumbnail() != 0){
                request.override(config.getWidth(), config.getHeight()).thumbnail(config.getThumbnail());
                Log.e("GlideLoader", "feiling");
            }else{
                request.override(config.getWidth(), config.getHeight());
                Log.e("GlideLoader", "weiling");
            }


            if(config.getDiskCacheStrategy() != null){
                request.diskCacheStrategy(config.getDiskCacheStrategy());
                Log.e("GlideLoader" , "config.getDiskCacheStrategy() :  " +config.getDiskCacheStrategy());
            }

            if (config.getErrorResId() > 0) {
                request.error(config.getErrorResId());
            }

            if (config.getTarget() instanceof ImageView) {
                request.into((ImageView) config.getTarget());
            }
        }

    }

    @Nullable
    private DrawableTypeRequest getDrawableTypeRequest(SingleConfig config, RequestManager requestManager) {
        DrawableTypeRequest request = null;
        if (!TextUtils.isEmpty(config.getUrl())) {
            request = requestManager.load(MyUtil.appendUrl(config.getUrl()));
        } else if (!TextUtils.isEmpty(config.getFilePath())) {
            request = requestManager.load(MyUtil.appendUrl(config.getFilePath()));
        } else if (!TextUtils.isEmpty(config.getContentProvider())) {
            request = requestManager.loadFromMediaStore(Uri.parse(config.getContentProvider()));
        } else if (config.getResId() > 0) {
            request = requestManager.load(config.getResId());
        }
        return request;
    }

    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest request) {
        int shapeMode = config.getShapeMode();
        Transformation[] transformation = new Transformation[2];
        if (config.isNeedBlur()) {
            transformation[0] = new BlurTransformation(config.getContext(), config.getBlurRadius());
        }

        switch (shapeMode) {
            case ShapeMode.RECT:

//                if (config.getBorderWidth() > 0) {
//
//                }

                break;
            case ShapeMode.RECT_ROUND:
                transformation[1] =
                        new RoundedCornersTransformation(config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);

//                if (config.getBorderWidth() > 0) {
//
//                }
//
//                if (config.isGif() && config.getRoundOverlayColor() > 0) {
//
//                }

                break;
            case ShapeMode.OVAL:
                transformation[1] = new CropCircleTransformation(config.getContext());
//                if (config.getBorderWidth() > 0) {
//
//                }
//                if (config.isGif() && config.getRoundOverlayColor() > 0) {
//
//                }
                break;
        }

        if (transformation[0] != null && transformation[1] != null) {
            request.bitmapTransform(transformation);
        } else if (transformation[0] != null && transformation[1] == null) {
            request.bitmapTransform(transformation[0]);
        } else if (transformation[0] == null && transformation[1] != null) {
            request.bitmapTransform(transformation[1]);
        }
    }

    @Override
    public void pause() {
        Glide.with(GlobalConfig.context).pauseRequestsRecursive();

    }

    @Override
    public void resume() {
        Glide.with(GlobalConfig.context).resumeRequestsRecursive();
    }

    @Override
    public void clearDiskCache() {
        Glide.get(GlobalConfig.context).clearDiskCache();
    }

    @Override
    public void clearCacheByUrl(String url) {

    }

    @Override
    public void clearMomoryCache(View view) {
        Glide.clear(view);
    }

    @Override
    public void clearMomory() {
        Glide.get(GlobalConfig.context).clearMemory();
    }

    @Override
    public File getFileFromDiskCache(String url) {
        return null;
    }

    @Override
    public boolean isCached(String url) {
        return false;
    }

    @Override
    public void trimMemory(int level) {
        Glide.with(GlobalConfig.context).onTrimMemory(level);
    }

    @Override
    public void clearAllMemoryCaches() {
        Glide.with(GlobalConfig.context).onLowMemory();
    }
}
