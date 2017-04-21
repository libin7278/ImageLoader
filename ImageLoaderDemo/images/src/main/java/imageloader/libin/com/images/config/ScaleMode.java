package imageloader.libin.com.images.config;

/**
 * Created by doudou on 2017/4/10.
 */

public interface ScaleMode {
    /**
     * 居中并填充整个view,太小就扩张,太大就裁剪,图片只缩放,不会拉伸.
     */
    int CENTER_CROP = 1;
    /**
     * 拉伸图片,使之刚好填充满view
     */
    int FIT_XY = 2;
    /**
     * 图片居中,如果太小,不会拉伸放大,导致外部显示view背景,如果太大,图片很多部位显示不出来
     */
    int CENTER = 4;
    int FOCUS_CROP = 5;
    int FIT_CENTER = 6;
    int FIT_START = 7;
    int FIT_END = 8;
    int CENTER_INSIDE = 9;
}
