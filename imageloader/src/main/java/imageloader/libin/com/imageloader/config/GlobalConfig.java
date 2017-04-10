package imageloader.libin.com.imageloader.config;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import imageloader.libin.com.imageloader.fresco.FrescoLoader;
import imageloader.libin.com.imageloader.glide.GlideLoader;
import imageloader.libin.com.imageloader.interfaces.ILoader;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class GlobalConfig {

    public static String baseUrl;

    public static Context context;

    public static Handler getMainHandler() {
        if(mainHandler==null){
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return mainHandler;
    }

    private static Handler mainHandler;

    public static int getWinHeight() {
        if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            return winHeight<winWidth? winHeight : winWidth;
        }else if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            return winHeight>winWidth? winHeight : winWidth;
        }
        return winHeight;
    }

    public static int getWinWidth() {
        if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            return winHeight>winWidth? winHeight : winWidth;
        }else if(context.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            return winHeight<winWidth? winHeight : winWidth;
        }
        return winWidth;
    }

    private static int winHeight;
    private static int winWidth;
    private static boolean userFresco;
    //private static int oritation;

    public static void init(Context context,int cacheSizeInM,boolean userFresco){

        GlobalConfig.context = context;
        GlobalConfig.cacheMaxSize = cacheSizeInM;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        GlobalConfig.winWidth = wm.getDefaultDisplay().getWidth();
        GlobalConfig.winHeight = wm.getDefaultDisplay().getHeight();
        //oritation = context.getResources().getConfiguration().orientation;
        GlobalConfig.userFresco = userFresco;
        getLoader().init(context,cacheSizeInM);




    }
    /**
     * lrucache 最大值
     */
    public static int cacheMaxSize= 50;

    /**
     * 缓存文件夹
     */
    public static String cacheFolderName = "frescoCache";

    /**
     * bitmap是888还是565编码,后者内存占用相当于前者一般,前者显示效果要好一点点,但两者效果不会差太多
     */
    public static boolean highQuality = false;

    /**
     * https是否忽略校验,默认不忽略
     */

    public static boolean ignoreCertificateVerify = false;

    private static ILoader loader;

    public static ILoader getLoader() {
        if(loader == null){
            if(userFresco){
                Log.e("ff","fresco");
                loader = new FrescoLoader();
            }else {
                Log.e("ff","GlideLoader");
                loader = new GlideLoader();
            }

        }
        return loader;
    }


}
