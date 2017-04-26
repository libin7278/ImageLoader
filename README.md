前言
=============
> - 有人可能会问为什么选择Glide进行二次封装？
>  - 那你应该去看我的上一篇文章：
>  http://blog.csdn.net/github_33304260/article/details/70213300
> - 那么又有人问了Glide直接能使用了，已经很方便了，为啥还要封装？
>   - 入口统一，所有图片加载都在这一个地方管理，一目了然，即使有什么改动我也只需要改这一个类就可以了。
>   - 虽然现在的第三方库已经非常好用，但是如果我们看到第三方库就拿来用的话，很可能在第三方库无法满足业务需求或者停止维护的时候，发现替换库，工作量可见一斑。这就是不封装在切库时面临的窘境！
>   - 外部表现一致，内部灵活处理原则
>   - 更多内容参考：[如何正确使用开源项目？](http://mp.weixin.qq.com/s?__biz=MzA4NTQwNDcyMA==&mid=2650661623&idx=1&sn=ab28ac6587e8a5ef1241be7870851355#rd)  

初识Glide
=============
##Glide配置
**1、 在build.gradle中添加依赖：**

```
dependencies {
  compile 'com.github.bumptech.glide:glide:3.7.0'
  compile 'com.android.support:support-v4:19.1.0'
}
```
Or Maven:

```
<dependency>
  <groupId>com.github.bumptech.glide</groupId>
  <artifactId>glide</artifactId>
  <version>3.7.0</version>
</dependency>
<dependency>
  <groupId>com.google.android</groupId>
  <artifactId>support-v4</artifactId>
  <version>r7</version>
</dependency>
```

**2、混淆**

```
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
```

**3、权限**
如果是联网获取图片或者本地存储需要添加以下权限：

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

##Glide基本使用
Glide使用一个流接口（Fluent Interface）。用Glide完成一个完整的图片加载功能请求，需要向其构造器中至少传入3个参数，分别是：
> - with(Context context)- Context是许多Android API需要调用的， Glide也不例外。这里Glide非常方便，你可以任意传递一个Activity或者Fragment对象，它都可以自动提取出上下文。
> - load(String imageUrl) - 这里传入的是你要加载的图片的URL，大多数情况下这个String类型的变量会链接到一个网络图片。
> -  into(ImageView targetImageView) - 将你所希望解析的图片传递给所要显示的ImageView。

example:

```
ImageView targetImageView = (ImageView) findViewById(R.id.imageView);
String internetUrl = "http://i.imgur.com/DvpvklR.png";

Glide
    .with(context)
    .load(internetUrl)
    .into(targetImageView);
```
##Compatibility
> - **Android SDK**: Glide requires a minimum API level of 10.
> - **OkHttp 2.x**: there are optional dependencies available called okhttp-integration, see Integration Libraries wiki page.
> - **OkHttp 3**.x: there are optional dependencies available called okhttp3-integration, see Integration Libraries wiki page.
> - **Volley**: there are optional dependencies available called volley-integration, see Integration Libraries wiki page.
> - **Round Pictures**: CircleImageView/CircularImageView/RoundedImageView are known to have issues with TransitionDrawable (.crossFade() with .thumbnail() or .placeholder()) and animated GIFs, use a BitmapTransformation (.circleCrop() will be available in v4) or .dontAnimate() to fix the issue.
> - **Huge Images** (maps, comic strips): Glide can load huge images by downsampling them, but does not support zooming and panning ImageViews as they require special resource optimizations (such as tiling) to work without OutOfMemoryErrors.


##常用API
> - **thumbnail(float sizeMultiplier)**. 请求给定系数的缩略图。如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示。系数sizeMultiplier必须在(0,1)之间，可以递归调用该方法。
> - **sizeMultiplier(float sizeMultiplier).** 在加载资源之前给Target大小设置系数。
> - **diskCacheStrategy(DiskCacheStrategy strategy).**设置缓存策略。> -DiskCacheStrategy.SOURCE：缓存原始数据，DiskCacheStrategy.RESULT：缓存变换(如缩放、裁剪等)后的资源数据，DiskCacheStrategy.NONE：什么都不缓存，DiskCacheStrategy.ALL：缓存SOURC和RESULT。默认采用> -> -DiskCacheStrategy.RESULT策略，对于download only操作要使用> -DiskCacheStrategy.SOURCE。
> - **priority(Priority priority)**. 指定加载的优先级，优先级越高越优先加载，但不保证所有图片都按序加载。枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW。默认为Priority.NORMAL。
> - **dontAnimate()
> .** 移除所有的动画。
> - **animate(int animationId).** 在异步加载资源完成时会执行该动画。
> - **animate(ViewPropertyAnimation.Animator animator).** 在异步加载资源完成时> 会执行该动画。
> - **placeholder(int resourceId)**. 设置资源加载过程中的占位Drawable。
> - **placeholder(Drawable drawable).** 设置资源加载过程中的占位Drawable。
> - **fallback(int resourceId).** 设置model为空时要显示的Drawable。如果没设置fallback，model为空时将显示error的Drawable，如果error的Drawable也没设置，就显示placeholder的Drawable。
> - **fallback(Drawable drawable)**.设置model为空时显示的Drawable。
> - **error(int resourceId).**设置load失败时显示的Drawable。
> - **error(Drawable drawable).**设置load失败时显示的Drawable。
> -**listener(RequestListener《? super ModelType, TranscodeType》> -requestListener).** 监听资源加载的请求状态，可以使用两个回调：onResourceReady(R resource, T model, Target<R> target, boolean isFromMemoryCache, boolean isFirstResource)和onException(Exception e, T model, Target&lt;R&gt; target, boolean isFirstResource)，但不要每次请求都使用新的监听器，要避免不必要的内存申请，可以使用单例进行统一的异常监听和处理。
> - **skipMemoryCache(boolean skip).** 设置是否跳过内存缓存，但不保证一定不被缓存（比如请求已经在加载资源且没设置跳过内存缓存，这个资源就会被缓存在内存中）。
> - **override(int width, int height).** 重新设置Target的宽高值（单位为pixel）。
> - **into(Y target)**.设置资源将被加载到的Target。
> - **into(ImageView view).** 设置资源将被加载到的ImageView。取消该ImageView之前所有的加载并释放资源。
> - **into(int width, int height)**. 后台线程加载时要加载资源的宽高值（单位为pixel）。
> - **preload(int width, int height)**. 预加载resource到缓存中（单位为pixel）。
> - **asBitmap().** 无论资源是不是gif动画，都作为Bitmap对待。如果是gif动画会停在第一帧。
> - **asGif().**把资源作为GifDrawable对待。如果资源不是gif动画将会失败，会回调.error()。

***更多Glide详细介绍可以看[Glide官网](https://github.com/bumptech/glide)以及[Glide教程系列文章](http://www.jianshu.com/p/7610bdbbad17)***

如何封装
=============
明白了为什么封装以及基本原理，接下来我们就要开工，大干一场。

先看一下本人封装后的基本使用样式：

```
ImageLoader.with(this)
	.url("http://img.yxbao.com/news/image/201703/13/7bda462477.gif")
	.placeHolder(R.mipmap.ic_launcher,false)
	.rectRoundCorner(30, R.color.colorPrimary)
	.blur(40)
	.into(iv_round);
```

更多属性我们后再详细讲解使用，主要先来看看具体的封装。

先看一下uml：

![这里写图片描述](http://img.blog.csdn.net/20170420165918194?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ2l0aHViXzMzMzA0MjYw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

使用者只需要关心ImageLoader就好了，就算里面封装的库更换、更新也没关系，因为对外的接口是不变的。实际操作中是由实现了ILoader的具体类去操作的，这里我们只封装了GlideLoader，其实所有操作都是由ImageLoader下发指令，由GlideLoader具体去实现的。这里如果想封装别的第三方库，只需要实现ILoader自己去完成里面的方法。

##初始化

```
	public static int CACHE_IMAGE_SIZE = 250;

    public static void init(final Context context) {
        init(context, CACHE_IMAGE_SIZE);
    }

    public static void init(final Context context, int cacheSizeInM) {
        init(context, cacheSizeInM, MemoryCategory.NORMAL);
    }

    public static void init(final Context context, int cacheSizeInM, MemoryCategory memoryCategory) {
        init(context, cacheSizeInM, memoryCategory, true);
    }

    /**
     * @param context        上下文
     * @param cacheSizeInM   Glide默认磁盘缓存最大容量250MB
     * @param memoryCategory 调整内存缓存的大小 LOW(0.5f) ／ NORMAL(1f) ／ HIGH(1.5f);
     * @param isInternalCD   true 磁盘缓存到应用的内部目录 / false 磁盘缓存到外部存
     */
    public static void init(final Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {
        ImageLoader.context = context;
        GlobalConfig.init(context, cacheSizeInM, memoryCategory, isInternalCD);
    }
```

从这里可以看出我们提供了四个构造器，这里注释详细说明了所有参数的用法及意义。

除了初始化，我们还需要在Application中重写以下方法：

```
	@Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
		// 程序在内存清理的时候执行
        ImageLoader.trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
	    // 低内存的时候执行
        ImageLoader.clearAllMemoryCaches();
    }
```
上面这两个方法会在下面ImageLoader中介绍到。

##你所关心的类--ImageLoader
ImageLoader是封装好所有的方法供用户使用的，让我们看看都有什么方法：
> - ImageLoader.init(Context context) //初始化
> - ImageLoader.trimMemory(int level); 
> - ImageLoader.clearAllMemoryCaches();
> - ImageLoader.getActualLoader(); //获取当前的loader 
> - ImageLoader.with(Context context) //加载图片
> - ImageLoader.saveImageIntoGallery(String url) // 保存图片到相册
> - ImageLoader.pauseRequests() //取消请求
> - ImageLoader.resumeRequests() //回复的请求（当列表在滑动的时候，调用pauseRequests()取消请求，滑动停止时，调用resumeRequests()恢复请求  等等）
> - ImageLoader.clearDiskCache()//清除磁盘缓存(必须在后台线程中调用)
> - ImageLoader.clearMomoryCache(View view) //清除指定view的缓存
> - ImageLoader.clearMomory() // 清除内存缓存(必须在UI线程中调用)


##图片的各种设置信息--SingleConfig
我们所设置图片的所有属性都写在这个类里面。下面我们详细的看一下：

> - url(String url) //支持filepath、图片链接、contenProvider、资源id四种
> - thumbnail(float thumbnail)//缩略图
> - rectRoundCorner(int rectRoundRadius, int overlayColorWhenGif) //形状为圆角矩形时的圆角半径
> - asSquare() //形状为正方形
> - colorFilter(int color) //颜色滤镜
> - diskCacheStrategy(DiskCacheStrategy diskCacheStrategy) //DiskCacheStrategy.NONE :不缓存图片 ／DiskCacheStrategy.SOURCE :缓存图片源文件／DiskCacheStrategy.RESULT:缓存修改过的图片／DiskCacheStrategy.ALL:缓存所有的图片，默认
> - asCircle(int overlayColorWhenGif)//加载圆形图片
> - placeHolder(int placeHolderResId) //占位图
> - override(int oWidth, int oHeight) //加载图片时设置分辨率 a
> - scale(int scaleMode) // CENTER_CROP等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示 ; FIT_CENTER 等比例缩放图片，宽或者是高等于ImageView的宽或者是高 默认：FIT_CENTER
> - animate(int animationId ) 引入动画
 > - animate( Animation animation) 引入动画
 > - animate(ViewPropertyAnimation.Animator animato) 引入动画
> - asBitmap(BitmapListener bitmapListener)// 使用bitmap不显示到imageview
> - into(View targetView) //展示到imageview
> - colorFilter(int filteColor) //颜色滤镜
> - blur(int blurRadius) ／/高斯模糊
> - brightnessFilter(float level) //调节图片亮度
> - grayscaleFilter() //黑白效果
> - swirlFilter() //漩涡效果
> - toonFilter() //油画效果
> - sepiaFilter() //水墨画效果
> - contrastFilter(float constrasrLevel) //锐化效果
> - invertFilter() //胶片效果
> - pixelationFilter(float pixelationLevel)  //马赛克效果
> - sketchFilter() //  //素描效果
> - vignetteFilter() //晕映效果

[github项目地址](https://github.com/libin7278/ImageLoader)

##中转站--GlideLoader 
GlideLoader实现ILoader接口。在使用的时候我们虽然不用关心这个类，但是了解一下主要做了什么功能还是不错的。

```
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
                Logger.e("config.getDiskCacheStrategy() :  " + config.getDiskCacheStrategy());
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

            // TODO: 2017/4/21 设置图片滤镜(目前只有高斯)
            setShapeModeAndBlur(config, request);

            //设置缩略图
            if (config.getThumbnail() != 0) {
                request.thumbnail(config.getThumbnail());
            }

            //设置图片加载的分辨 sp
            if (config.getoWidth() != 0 && config.getoHeight() != 0) {
                request.override(config.getoWidth(), config.getoHeight());
                Logger.e("设置图片加载的分辨 : " + config.getoWidth() + "   " + config.getoHeight());
            }

            //是否跳过磁盘存储
            if (config.getDiskCacheStrategy() != null) {
                request.diskCacheStrategy(config.getDiskCacheStrategy());
                Logger.e("config.getDiskCacheStrategy() :  " + config.getDiskCacheStrategy());
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

                Logger.e("config.getTarget()" + config.getTarget().getMeasuredWidth());
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
        int shapeMode = config.getShapeMode();
        Transformation[] transformation = new Transformation[3];
        if (config.isNeedBlur()) {
           // transformation[0] = new BlurTransformation(config.getContext(), config.getBlurRadius());
            transformation[0] = new BrightnessFilterTransformation(config.getContext(), 0.5f);
            //transformation[0] =new GrayscaleTransformation(config.getContext()); 黑白效果
        }

        if(config.isNeedFilteColor()){
            transformation[2] = new ColorFilterTransformation(config.getContext(), config.getFilteColor());
        }

        switch (shapeMode) {
            case ShapeMode.RECT:

                break;
            case ShapeMode.RECT_ROUND:
                transformation[1] = new RoundedCornersTransformation
                        (config.getContext(), config.getRectRoundRadius(), 0, RoundedCornersTransformation.CornerType.ALL);

                break;
            case ShapeMode.OVAL:
                transformation[1] = new CropCircleTransformation(config.getContext());
                break;

            case ShapeMode.SQUARE:
                transformation[1] = new CropSquareTransformation(config.getContext());
                break;
        }

        if (transformation[0] != null && transformation[1] != null && transformation[2] != null) {
            request.bitmapTransform(transformation);
        } else if (transformation[0] != null && transformation[1] == null) {
            request.bitmapTransform(transformation[0]);
        } else if (transformation[0] == null && transformation[1] != null) {
            request.bitmapTransform(transformation[1]);
        }else if(transformation[0] == null && transformation[1] == null && transformation[2] != null){
            request.bitmapTransform(transformation[2]);
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
```

看一下效果哦：
![这里写图片描述](http://img.blog.csdn.net/20170426164943536?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ2l0aHViXzMzMzA0MjYw/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

到这里我们的封装就结束了，就可以愉快的使用了，欢迎大家提出意见与建议。

[Glide二次封装库源码](https://github.com/libin7278/ImageLoader)

后面会更新到jcenter，并会出一篇具体如何使用本库的文章，还请大家持续关注哦。
