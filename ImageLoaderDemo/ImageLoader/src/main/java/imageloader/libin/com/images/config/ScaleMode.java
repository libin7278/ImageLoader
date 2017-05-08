package imageloader.libin.com.images.config;

/**
 * Created by doudou on 2017/4/10.
 */

public interface ScaleMode {
    /**
     * 等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示
     */
    int CENTER_CROP = 1;
    /**
     * 等比例缩放图片，宽或者是高等于ImageView的宽或者是高
     */
    int FIT_CENTER = 2;
}
