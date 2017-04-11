package imageloader.libin.com.imageloader.config;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

import imageloader.libin.com.imageloader.fresco.FrescoLoader;
import imageloader.libin.com.imageloader.glide.GlideLoader;
import imageloader.libin.com.imageloader.interfaces.ILoader;

/**
 * Created by doudou on 2017/4/10.
 */

public class GlobalConfig {

    public static String baseUrl;
    public static Context context;
    private static int winHeight;
    private static int winWidth;
    private static boolean useFresco;

    /**
     * lrucache 最大值
     */
    public static int cacheMaxSize = 50;

    /**
     * 缓存文件夹
     */
    public static String PHOTO_FRESCO = "frescoCache";

    /**
     * https是否忽略校验,默认不忽略
     */
    public static boolean ignoreCertificateVerify = false;

    public static void init(Context context, int cacheSizeInM, boolean useFresco) {

        GlobalConfig.context = context;
        GlobalConfig.cacheMaxSize = cacheSizeInM;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        GlobalConfig.winWidth = wm.getDefaultDisplay().getWidth();
        GlobalConfig.winHeight = wm.getDefaultDisplay().getHeight();
        GlobalConfig.useFresco = useFresco;
        getLoader().init(context, cacheSizeInM);

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
            if (useFresco) {
                Logger.e("FrescoLoader");
                loader = new FrescoLoader();
            } else {
                Logger.e("GlideLoader");
                loader = new GlideLoader();
            }

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
