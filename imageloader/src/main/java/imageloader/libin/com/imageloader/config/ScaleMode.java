package imageloader.libin.com.imageloader.config;

/**
 * Created by Administrator on 2017/3/15 0015.
 * 类型	描述
 center	居中，无缩放。
 centerCrop	保持宽高比缩小或放大，使得两边都大于或等于显示边界，且宽或高契合显示边界。居中显示。
 focusCrop	同centerCrop, 但居中点不是中点，而是指定的某个点。
 centerInside	缩放图片使两边都在显示边界内，居中显示。和 fitCenter 不同，不会对图片进行放大。
 如果图尺寸大于显示边界，则保持长宽比缩小图片。
 fitCenter	保持宽高比，缩小或者放大，使得图片完全显示在显示边界内，且宽或高契合显示边界。居中显示。
 fitStart	同上。但不居中，和显示边界左上对齐。
 fitEnd	同fitCenter， 但不居中，和显示边界右下对齐。
 fitXY	不保存宽高比，填充满显示边界。
 none	如要使用tile mode显示, 需要设置为none
 *
 */

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
