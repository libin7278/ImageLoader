package imageloader.libin.com.images.config;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.bumptech.glide.MemoryCategory;

import imageloader.libin.com.images.loader.GlideLoader;
import imageloader.libin.com.images.loader.ILoader;

/**
 * Created by doudou on 2017/4/10.
 */

public class GlobalConfig {

    public static String baseUrl;
    public static Context context;

    /**
     * 屏幕高度
     */
    private static int winHeight;

    /**
     * 屏幕宽度
     */
    private static int winWidth;

    /**
     * lrucache 最大值
     */
    public static int cacheMaxSize;

    /**
     * https是否忽略校验,默认不忽略
     */
    public static boolean ignoreCertificateVerify = false;

    public static void init(Context context, int cacheSizeInM, MemoryCategory memoryCategory, boolean isInternalCD) {

        GlobalConfig.context = context;
        GlobalConfig.cacheMaxSize = cacheSizeInM;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        GlobalConfig.winWidth = wm.getDefaultDisplay().getWidth();
        GlobalConfig.winHeight = wm.getDefaultDisplay().getHeight();
        getLoader().init(context, cacheSizeInM, memoryCategory, isInternalCD);

    }

    private static Handler mainHandler;

    public static Handler getMainHandler() {
        if (mainHandler == null) {
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    private static ILoader loader;

    public static ILoader getLoader() {

        if (loader == null) {
            loader = new GlideLoader();
        }

        return loader;
    }


    public static int getWinHeight() {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return winHeight < winWidth ? winHeight : winWidth;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return winHeight > winWidth ? winHeight : winWidth;
        }
        return winHeight;
    }

    public static int getWinWidth() {
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return winHeight > winWidth ? winHeight : winWidth;
        } else if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return winHeight < winWidth ? winHeight : winWidth;
        }
        return winWidth;
    }

}
