package imageloader.libin.com.images.loader;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.MemoryCategory;

import java.io.File;

import imageloader.libin.com.images.config.SingleConfig;

/**
 * Created by doudou on 2017/4/10.
 */

public interface ILoader {

    void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD);

    void request(SingleConfig config);

    void pause();

    void resume();

    void clearDiskCache();

    void clearMomoryCache(View view);

    void clearMomory();

    File getFileFromDiskCache(String url);

    boolean  isCached(String url);

    void trimMemory(int level);

    void clearAllMemoryCaches();

}
