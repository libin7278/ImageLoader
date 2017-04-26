package imageloader.libin.com.images.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

import java.io.File;

import imageloader.libin.com.images.config.GlobalConfig;
import imageloader.libin.com.images.config.SingleConfig;

/**
 * Created by doudou on 2017/4/19.
 */

public class ImageLoader {
    public static Context context;
    /**
     * 默认最大缓存
     */
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

    /**
     * 获取当前的Loader
     * @return
     */
    public static ILoader getActualLoader() {
        return GlobalConfig.getLoader();
    }

    /**
     * 加载普通图片
     *
     * @param context
     * @return
     */
    public static SingleConfig.ConfigBuilder with(Context context) {
        return new SingleConfig.ConfigBuilder(context);
    }


    /**
     * 图片保存到相册
     *
     * @param url
     */
    private static void saveImageIntoGallery(String url) {
        File file = GlobalConfig.getLoader().getFileFromDiskCache(url);
        if (file != null && file.exists()) {
            //todo 拷贝文件到picture文件夹中
        }

    }


    public static void trimMemory(int level) {
        GlobalConfig.getLoader().trimMemory(level);
    }

    public static void clearAllMemoryCaches() {
        GlobalConfig.getLoader().clearAllMemoryCaches();
    }

    public static void pauseRequests() {
        getActualLoader().pause();

    }

    public static void resumeRequests() {
        getActualLoader().resume();
    }

    public static void clearDiskCache() {
        Glide.get(GlobalConfig.context).clearDiskCache();
    }

    public static void clearMomoryCache(View view) {
        Glide.clear(view);
    }

    public static void clearMomory() {
        Glide.get(GlobalConfig.context).clearMemory();
    }
}
