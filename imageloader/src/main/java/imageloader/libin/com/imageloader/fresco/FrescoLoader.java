package imageloader.libin.com.imageloader.fresco;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;
import com.github.piasy.biv.view.BigImageView;

import java.io.File;

import imageloader.libin.com.imageloader.config.GlobalConfig;
import imageloader.libin.com.imageloader.config.ScaleMode;
import imageloader.libin.com.imageloader.config.ShapeMode;
import imageloader.libin.com.imageloader.config.SingleConfig;
import imageloader.libin.com.imageloader.gif.GifUtils;
import imageloader.libin.com.imageloader.interfaces.ILoader;
import imageloader.libin.com.imageloader.utils.MyUtil;
import jp.wasabeef.fresco.processors.BlurPostprocessor;
import okhttp3.OkHttpClient;

import static imageloader.libin.com.imageloader.config.GlobalConfig.context;

/**
 * Created by doudou on 2017/4/10.
 */

public class FrescoLoader implements ILoader {
    @Override
    public void init(final Context context, int cacheSizeInM) {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setMaxCacheSize(cacheSizeInM*1024*1024)
                .setBaseDirectoryName(GlobalConfig.cacheFolderName)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return context.getCacheDir();
                    }
                })
                .build();
        MyImageCacheStatsTracker imageCacheStatsTracker = new MyImageCacheStatsTracker();

        OkHttpClient okHttpClient= MyUtil.getClient(GlobalConfig.ignoreCertificateVerify);
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context,okHttpClient)
                //ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setImageCacheStatsTracker(imageCacheStatsTracker)
                .setDownsampleEnabled(true)//Downsampling，它处理图片的速度比常规的裁剪更快，
                // 并且同时支持PNG，JPG以及WEP格式的图片，非常强大,与ResizeOptions配合使用
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                //让fresco即时清理内存:http://blog.csdn.net/honjane/article/details/65629799
                .setBitmapMemoryCacheParamsSupplier(new MyBitmapMemoryCacheParamsSupplier((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)))
                .build();
        //Fresco.initialize(context, config);

        BigImageViewer.initialize(FrescoImageLoader.with(context,config));

    }

    @Override
    public void request(SingleConfig config) {
        if(config.isAsBitmap()){
            requestBitmap(config);
        }else {
            requestForImageView(config);
        }


    }

    private void requestForImageView(SingleConfig config) {
        if(config.getTarget() instanceof BigImageView){
            MyUtil.viewBigImage(config);
            return;
        }

        if(config.getTarget() instanceof SimpleDraweeView){
            ImageRequest request = buildRequest(config);


            setDraweeHierarchy(config);


            AbstractDraweeController controller = buildPipelineDraweeController(config,request);

            ((SimpleDraweeView) config.getTarget()).setController(controller);
        }

        if(config.getTarget() instanceof ImageView){
            final ImageView imageView = (ImageView) config.getTarget();
            config.setBitmapListener(new SingleConfig.BitmapListener() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void onFail() {

                }
            });
            requestBitmap(config);
            return;
        }




    }



    private AbstractDraweeController buildPipelineDraweeController(SingleConfig config,ImageRequest request) {
        PipelineDraweeControllerBuilder builder =  Fresco.newDraweeControllerBuilder();
        builder.setImageRequest(request);
        if(config.isAsBitmap()){

        }else {
            builder.setAutoPlayAnimations(true);//自动播放gif动画
            if(config.getTarget() instanceof SimpleDraweeView){
                SimpleDraweeView view = (SimpleDraweeView) config.getTarget();
                builder.setOldController(view.getController());
            }

        }


        return builder.build();
    }

    private void setDraweeHierarchy(SingleConfig config) {
        SimpleDraweeView simpleDraweeView = null;
        if(config.isAsBitmap()){
            return;
        }

        if(config.getTarget() instanceof SimpleDraweeView){
            simpleDraweeView = (SimpleDraweeView) config.getTarget();
        }
        if(simpleDraweeView ==null){
            return;
        }

        GenericDraweeHierarchy hierarchy = simpleDraweeView.getHierarchy();

        if(hierarchy==null){
            hierarchy = new GenericDraweeHierarchyBuilder(context.getResources()).build();
        }

        if(MyUtil.shouldSetPlaceHolder(config)){
            hierarchy.setPlaceholderImage(config.getPlaceHolderResId(), ScalingUtils.ScaleType.FIT_CENTER);
        }



        //边角形状和边框
        int shapeMode = config.getShapeMode();
        RoundingParams roundingParams =null;

        switch (shapeMode){
            case ShapeMode.RECT:
                roundingParams = RoundingParams.fromCornersRadius(0);
                if(config.getBorderWidth()>0){
                    roundingParams.setBorder(config.getBorderColor(),config.getBorderWidth());
                }
                break;
            case ShapeMode.RECT_ROUND:
                roundingParams = RoundingParams.fromCornersRadius(config.getRectRoundRadius());
                if(config.getBorderWidth()>0){
                    roundingParams.setBorder(config.getBorderColor(),config.getBorderWidth());
                }
                if(config.isGif() && config.getRoundOverlayColor()>0){
                    roundingParams.setOverlayColor(config.getRoundOverlayColor());
                }
                break;
            case ShapeMode.OVAL:
                roundingParams = RoundingParams.asCircle();
                if(config.getBorderWidth()>0){
                    roundingParams.setBorder(config.getBorderColor(),config.getBorderWidth());
                }
                if(config.isGif() && config.getRoundOverlayColor()>0){
                    roundingParams.setOverlayColor(config.getRoundOverlayColor());
                }
                break;
        }

        hierarchy.setRoundingParams(roundingParams);

        //伸缩模式
        int scaleMode = config.getScaleMode();
        switch (scaleMode){
            case ScaleMode.CENTER_CROP:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
            case ScaleMode.CENTER_INSIDE:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
            case ScaleMode.FIT_CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
                break;
            case ScaleMode.FIT_XY:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY);
                break;
            case ScaleMode.FIT_END:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END);
                break;
            case ScaleMode.FOCUS_CROP:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
                break;
            case ScaleMode.CENTER:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER);
                break;
            case ScaleMode.FIT_START:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START);
                break;
            default:
                hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP);
                break;
        }

        simpleDraweeView.setHierarchy(hierarchy);


    }

    private ImageRequest buildRequest(SingleConfig config) {
        Uri uri = MyUtil.buildUriByType(config);

        ImageRequestBuilder builder =   ImageRequestBuilder.newBuilderWithSource(uri);

        if(!config.isAsBitmap()){
            builder.setProgressiveRenderingEnabled(true)//支持图片渐进式加载
                    .setLocalThumbnailPreviewsEnabled(true);

        }

        Postprocessor postprocessor=null;
        if(config.isNeedBlur()){
            postprocessor = new BlurPostprocessor(context,config.getBlurRadius(),2);//todo 高斯模糊的配置
        }


        ResizeOptions resizeOptions = getResizeOption(config);

        builder.setPostprocessor(postprocessor)
                .setResizeOptions(resizeOptions)//缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
                // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
                .setAutoRotateEnabled(true);



        return builder.build();
    }

    private ResizeOptions getResizeOption(SingleConfig config) {
        ResizeOptions resizeOptions = null;
        if (config.getWidth() >0 && config.getHeight() > 0){
            resizeOptions = new ResizeOptions(config.getWidth(),config.getHeight());//todo 通过图片宽高和view宽高计算出最合理的resization
        }
        return resizeOptions;
    }





    /**
     * 注:能够拿到网络gif的第一帧图,但拿不到res,本地file的第一帧图
     * @param config
     */
    private void requestBitmap(final SingleConfig config) {

        final ImageRequest request = buildRequest(config);
        final String finalUrl = request.getSourceUri().toString();//;MyUtil.appendUrl(config.getUrl());
        Log.e("uri",finalUrl);
        DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(request, context);
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(Bitmap bitmap) {
                //注意，gif图片解码方法与普通图片不一样，是无法拿到bitmap的。如果要把gif的第一帧的bitmap返回，怎么做？
                //GifImage.create(bytes).decode(1l,9).getFrameInfo(1).
                if(bitmap!=null){
                    config.getBitmapListener().onSuccess(bitmap);
                    return;
                }

                File cacheFile  = getFileFromDiskCache(finalUrl);
                if(cacheFile ==null){
                    config.getBitmapListener().onFail();
                    return;
                }
                //还要判断文件是不是gif格式的
                if (!"gif".equalsIgnoreCase(MyUtil.getRealType(cacheFile))){
                    config.getBitmapListener().onFail();
                    return;
                }

                Bitmap bitmapGif = GifUtils.getBitmapFromGifFile(cacheFile);//拿到gif第一帧的bitmap
                Bitmap target = MyUtil.compressBitmap(bitmapGif, true, config.getWidth(), config.getHeight());//将bitmap压缩到指定宽高。
                if (target != null) {
                    config.getBitmapListener().onSuccess(target);
                } else {
                    config.getBitmapListener().onFail();
                }

            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                config.getBitmapListener().onFail();
            }
        }, CallerThreadExecutor.getInstance());

    }

    @Override
    public void pause() {
        Fresco.getImagePipeline().pause();
    }

    @Override
    public void resume() {
        Fresco.getImagePipeline().resume();
    }

    @Override
    public void clearDiskCache() {

        Fresco.getImagePipeline().clearDiskCaches();
    }

    @Override
    public void clearCacheByUrl(String url) {
        url = MyUtil.appendUrl(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        imagePipeline.evictFromMemoryCache(uri);
        imagePipeline.evictFromDiskCache(uri);
        //imagePipeline.evictFromCache(uri);//这个包含了从内存移除和从硬盘移除
    }

    @Override
    public void clearMomoryCache(View view) {

    }

    @Override
    public void clearMomoryCache(String url) {
        url = MyUtil.appendUrl(url);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(url);
        imagePipeline.evictFromMemoryCache(uri);
    }

    @Override
    public File getFileFromDiskCache(String url) {
        url = MyUtil.appendUrl(url);
        File localFile = null;
        if (!TextUtils.isEmpty(url)) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(url),null);
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }

    @Override
    public boolean isCached(String url) {
        if(TextUtils.isEmpty(url)){
            return false;
        }
        url = MyUtil.appendUrl(url);
        DataSource<Boolean> isIn = Fresco.getImagePipeline().isInDiskCache(Uri.parse(url));
        if(isIn!=null){
            try {
                return isIn.getResult();
            }catch (Exception e){
                return false;
            }
        }else {
            return false;
        }


       /* ImageRequest imageRequest = ImageRequest.fromUri(url);
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                .getEncodedCacheKey(imageRequest,null);
        return ImagePipelineFactory.getInstance()
                .getMainFileCache().hasKey(cacheKey);*/
    }

    @Override
    public  void trimMemory(int level){
        //todo  BitmapMemoryCacheTrimStrategy.getTrimRatio(trimType)
    }

    @Override
    public  void  clearAllMemoryCaches(){
        Fresco.getImagePipeline().clearMemoryCaches();
    }
}