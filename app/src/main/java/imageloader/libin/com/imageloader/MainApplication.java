package imageloader.libin.com.imageloader;

import android.app.Application;

/**
 * Created by doudou on 2017/4/10.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoader.init(getApplicationContext(), 40,true);

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
