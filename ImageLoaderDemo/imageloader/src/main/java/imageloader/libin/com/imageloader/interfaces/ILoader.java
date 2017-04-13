package imageloader.libin.com.imageloader.interfaces;

import android.content.Context;
import android.view.View;

import java.io.File;

import imageloader.libin.com.imageloader.config.SingleConfig;

/**
 * Created by doudou on 2017/4/10.
 */

public interface ILoader {
    void init(Context context, int cacheSizeInM);

    void request(SingleConfig config);

    void pause();

    void resume();

    void clearDiskCache();

    void clearCacheByUrl(String url);

    void clearMomoryCache(View view);
    void clearMomoryCache(String url);

    File getFileFromDiskCache(String url);

    boolean  isCached(String url);

    void trimMemory(int level);

    void clearAllMemoryCaches();

}
