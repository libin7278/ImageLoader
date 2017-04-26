package imageloader.libin.com.imageloaderdemo;

import android.app.Application;

import imageloader.libin.com.images.loader.ImageLoader;

/**
 * Created by doudou on 2017/4/10.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.init(getApplicationContext());

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        ImageLoader.trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        ImageLoader.clearAllMemoryCaches();
    }
}
