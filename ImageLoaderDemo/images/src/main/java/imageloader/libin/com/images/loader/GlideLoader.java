package imageloader.libin.com.images.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;

import imageloader.libin.com.images.config.AnimationMode;
import imageloader.libin.com.images.config.GlobalConfig;
import imageloader.libin.com.images.config.PriorityMode;
import imageloader.libin.com.images.config.ScaleMode;
import imageloader.libin.com.images.config.ShapeMode;
import imageloader.libin.com.images.config.SingleConfig;
import imageloader.libin.com.images.utils.MyUtil;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;


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

            if (config.getDiskCacheStrategy() != null) {
                request.diskCacheStrategy(config.getDiskCacheStrategy());
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
                case ScaleMode.FIT_CENTER:
                    request.fitCenter();
                    break;
                default:
                    request.fitCenter();
                    break;
            }

            setShapeModeAndBlur(config, request);

            //设置缩略图
            if (config.getThumbnail() != 0) {
                request.thumbnail(config.getThumbnail());
            }

            //设置图片加载的分辨 sp
            if (config.getoWidth() != 0 && config.getoHeight() != 0) {
                request.override(config.getoWidth(), config.getoHeight());
            }

            //是否跳过磁盘存储
            if (config.getDiskCacheStrategy() != null) {
                request.diskCacheStrategy(config.getDiskCacheStrategy());
            }

            //设置图片加载动画
            setAnimator(config, request);

            //设置图片加载优先级
            setPriority(config, request);

            if (config.getErrorResId() > 0) {
                request.error(config.getErrorResId());
            }

            if (config.getTarget() instanceof ImageView) {
                request.into((ImageView) config.getTarget());

            }
        }

    }

    /**
     * 设置加载优先级
     *
     * @param config
     * @param request
     */
    private void setPriority(SingleConfig config, DrawableTypeRequest request) {
        switch (config.getPriority()) {
            case PriorityMode.PRIORITY_LOW:
                request.priority(Priority.LOW);
                break;
            case PriorityMode.PRIORITY_NORMAL:
                request.priority(Priority.NORMAL);
                break;
            case PriorityMode.PRIORITY_HIGH:
                request.priority(Priority.HIGH);
                break;
            case PriorityMode.PRIORITY_IMMEDIATE:
                request.priority(Priority.IMMEDIATE);
                break;
            default:
                request.priority(Priority.IMMEDIATE);
                break;
        }
    }

    /**
     * 设置加载进入动画
     *
     * @param config
     * @param request
     */
    private void setAnimator(SingleConfig config, DrawableTypeRequest request) {
        if (config.getAnimationType() == AnimationMode.ANIMATIONID) {
            request.animate(config.getAnimationId());
        } else if (config.getAnimationType() == AnimationMode.ANIMATOR) {
            request.animate(config.getAnimator());
        } else if (config.getAnimationType() == AnimationMode.ANIMATION) {
            request.animate(config.getAnimation());
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

    /**
     * 设置图片滤镜和形状
     *
     * @param config
     * @param request
     */
    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest request) {

        int count = 0;

        Transformation[] transformation = new Transformation[statisticsCount(config)];

        if (config.isNeedBlur()) {
            transformation[count] = new BlurTransformation(config.getContext(), config.getBlurRadius());
            count++;
        }

        if (config.isNeedBrightness()) {
            transformation[count] = new BrightnessFilterTransformation(config.getContext(), config.getBrightnessLeve()); //亮度
            count++;
        }

        if (config.isNeedGrayscale()) {
            transformation[count] = new GrayscaleTransformation(config.getContext()); //黑白效果
            count++;
        }

        if (config.isNeedFilteColor()) {
            transformation[count] = new ColorFilterTransformation(config.getContext(), config.getFilteColor());
            count++;
        }

        if (config.isNeedSwirl()) {
            transformation[count] = new SwirlFilterTransformation(config.getContext(), 0.5f, 1.0f, new PointF(0.5f, 0.5f)); //漩涡
            count++;
        }

        if (config.isNeedToon()) {
            transformation[count] = new ToonFilterTransformation(config.getContext()); //油画
            count++;
        }

        if (config.isNeedSepia()) {
            transformation[count] = new SepiaFilterTransformation(config.getContext()); //墨画
            count++;
        }

        if (config.isNeedContrast()) {
            transformation[count] = new ContrastFilterTransformation(config.getContext(), config.getContrastLevel()); //锐化
            count++;
        }

        if (config.isNeedInvert()) {
            transformation[count] = new InvertFilterTransformation(config.getContext()); //胶片
            count++;
        }

        if (config.isNeedPixelation()) {
            transformation[count] =new PixelationFilterTransformation(config.getContext(), config.getPixelationLevel()); //马赛克
            count++;
        }

        if (config.isNeedSketch()) {
            transformation[count] =new SketchFilterTransformation(config.getContext()); //素描
            count++;
        }

        if (config.isNeedVignette()) {
            transformation[count] =new VignetteFilterTransformation(config.getContext(), new PointF(0.5f, 0.5f),
                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);//晕映
            count++;
        }

        switch (config.getShapeMode()) {
            case ShapeMode.RECT:

                break;
            case ShapeMode.RECT_ROUND:
                transformation[count] = new RoundedCornersTransformation
                        (config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
                count++;
                break;
            case ShapeMode.OVAL:
                transformation[count] = new CropCircleTransformation(config.getContext());
                count++;
                break;

            case ShapeMode.SQUARE:
                transformation[count] = new CropSquareTransformation(config.getContext());
                count++;
                break;
        }

        if (transformation.length != 0) {
            request.bitmapTransform(transformation);

        }

//        if (transformation[0] != null && transformation[1] != null && transformation[2] != null) {
//
//        } else if (transformation[0] != null && transformation[1] == null) {
//            request.bitmapTransform(transformation[0]);
//        } else if (transformation[0] == null && transformation[1] != null) {
//            request.bitmapTransform(transformation[1]);
//        }else if(transformation[0] == null && transformation[1] == null && transformation[2] != null){
//            request.bitmapTransform(transformation[2]);
//        }
    }

    private int statisticsCount(SingleConfig config) {
        int count = 0;

        if (config.getShapeMode() == ShapeMode.OVAL || config.getShapeMode() == ShapeMode.RECT_ROUND || config.getShapeMode() == ShapeMode.SQUARE) {
            count++;
        }

        if (config.isNeedBlur()) {
            count++;
        }

        if (config.isNeedFilteColor()) {
            count++;
        }

        if (config.isNeedBrightness()) {
            count++;
        }

        if (config.isNeedGrayscale()) {
            count++;
        }

        if (config.isNeedSwirl()) {
            count++;
        }

        if (config.isNeedToon()) {
            count++;
        }

        if (config.isNeedSepia()) {
            count++;
        }

        if (config.isNeedContrast()) {
            count++;
        }

        if (config.isNeedInvert()) {
            count++;
        }

        if (config.isNeedPixelation()) {
            count++;
        }

        if (config.isNeedSketch()) {
            count++;
        }

        if (config.isNeedVignette()) {
            count++;
        }

        return count;
    }

//    /**
//     * 设置图片滤镜和形状
//     *
//     * @param config
//     * @param request
//     */
//    private void setShapeModeAndBlur(SingleConfig config, DrawableTypeRequest request) {
//        Transformation[] transformation = new Transformation[3];
//        if (config.isNeedBlur()) {
//           //transformation[0] = new BlurTransformation(config.getContext(), config.getBlurRadius());
//            //transformation[0] = new BrightnessFilterTransformation(config.getContext(),10f); //亮度
//            //transformation[0] =new GrayscaleTransformation(config.getContext()); //黑白效果
//            //transformation[0] =new SwirlFilterTransformation(config.getContext(), 0.5f, 1.0f, new PointF(0.5f, 0.5f)); //漩涡
//            //transformation[0] =new ToonFilterTransformation(config.getContext()); //油画
//            //transformation[0] =new SepiaFilterTransformation(config.getContext()); //墨画
//            //transformation[0] =new ContrastFilterTransformation(config.getContext(), 2.0f); //锐化
//            //transformation[0] =new InvertFilterTransformation(config.getContext()); //胶片
//            //transformation[0] =new PixelationFilterTransformation(config.getContext(), 20); //马赛克
//            //transformation[0] =new SketchFilterTransformation(config.getContext()); //素描
//            //transformation[0] =new CropTransformation(config.getContext(), 500, 500, CropTransformation.CropType.TOP);
//            transformation[0] =new CropTransformation(config.getContext(), 2000, 2000 , CropTransformation.CropType.CENTER);
////            transformation[0] =new VignetteFilterTransformation(config.getContext(), new PointF(0.5f, 0.5f),
////                    new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);//晕映
//
//        }
//
//        if(config.isNeedFilteColor()){
//            transformation[2] = new ColorFilterTransformation(config.getContext(), config.getFilteColor());
//        }
//
//        switch (config.getShapeMode()) {
//            case ShapeMode.RECT:
//
//                break;
//            case ShapeMode.RECT_ROUND:
//                transformation[1] = new RoundedCornersTransformation
//                        (config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);
//
//                break;
//            case ShapeMode.OVAL:
//                transformation[1] = new CropCircleTransformation(config.getContext());
//                break;
//
//            case ShapeMode.SQUARE:
//                transformation[1] = new CropSquareTransformation(config.getContext());
//                break;
//        }
//
//        if (transformation[0] != null && transformation[1] != null && transformation[2] != null) {
//            request.bitmapTransform(transformation);
//        } else if (transformation[0] != null && transformation[1] == null) {
//            request.bitmapTransform(transformation[0]);
//        } else if (transformation[0] == null && transformation[1] != null) {
//            request.bitmapTransform(transformation[1]);
//        }else if(transformation[0] == null && transformation[1] == null && transformation[2] != null){
//            request.bitmapTransform(transformation[2]);
//        }
//    }

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
