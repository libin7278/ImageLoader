package imageloader.libin.com.images.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

import imageloader.libin.com.images.R;

/**
 * Created by doudou on 2017/5/3.
 */

public class MultiView extends ImageView {
    /**
     * 图片的类型，圆形or圆角or多边形
     */
    private Context mContext;

    /**
     * 传输类型
     */
    private int type;

    /**
     * 圆形
     */
    public static final int TYPE_CIRCLE = 0;

    /**
     * 圆角
     */
    public static final int TYPE_ROUND = 1;

    /**
     * 多边形
     */
    public static final int TYPE_MULTI = 3;

    /**
     *默认多边形角的个数
     */
    public static final int ANGLECOUNT = 5;

    /**
     * 默认开始绘制的角度
     */
    public static final int CURRENTANGLE = 180;

    /**
     * 多边形的半径
     */
    private int startRadius;

    /**
     * 多边形角的个数
     */
    private int angleCount ;

    private int[] angles;

    /**
     * 开始绘制的角度
     */
    private int currentAngle;

    /**
     * 存储角位置的集合
     */
    private List<PointF> pointFList = new ArrayList<>();

    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 10;

    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;

    /**
     * 圆角的半径
     */
    private int mRadius;

    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;

    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;

    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    public MultiView(Context context) {
        this(context, null);
    }

    public MultiView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public MultiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        init(context, attrs);

    }

    public void init(Context context, AttributeSet attrs) {
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);

        mBorderRadius = typedArray.getDimensionPixelSize(
                R.styleable.RoundImageView_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                BODER_RADIUS_DEFAULT, getResources()
                                        .getDisplayMetrics()));// 默认为10dp
        type = typedArray.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle
        angleCount = typedArray.getInt(R.styleable.RoundImageView_angleCount, ANGLECOUNT);
        currentAngle = typedArray.getInt(R.styleable.RoundImageView_currentAngle, currentAngle);

        typedArray.recycle(); //回收之后对象可以重用
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 如果类型是圆形或多边形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }

        if (type == TYPE_MULTI) {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());

            setMeasuredDimension(mWidth, mWidth);

            angles = new int[angleCount];

            for (int i = 0; i < angleCount; i++) {
                int partOfAngle = 360 / angleCount; //每个顶点的角度
                angles[i] = currentAngle + partOfAngle * i;

                startRadius = mWidth / 2;
                float x = (float) (Math.sin(Math.toRadians(angles[i])) * startRadius);
                float y = (float) (Math.cos(Math.toRadians(angles[i])) * startRadius);
                pointFList.add(new PointF(x, y));
            }
        }

    }

    /**
     * 初始化BitmapShader
     */
    private void setUpShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = drawableToBitamp(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        float scale = 1.0f;
        if (type == TYPE_CIRCLE) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else if (type == TYPE_ROUND) {
            if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(getWidth() * 1.0f / bmp.getWidth(), getHeight() * 1.0f / bmp.getHeight());
            }

        } else if (type == TYPE_MULTI) {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);

        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        setUpShader();

        if (type == TYPE_ROUND) {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
                    mBitmapPaint);
        } else if (type == TYPE_MULTI) {
            //canvas.translate(startRadius,startRadius);

            Path mPath = drawPath();

            canvas.drawPath(mPath, mBitmapPaint);
        } else {
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
        }
    }

    /**
     * @return 多边形路径
     */
    private Path drawPath() {
        Path mPath = new Path();
        mPath.moveTo(pointFList.get(0).x, pointFList.get(0).y);
        for (int i = 2; i < angleCount; i++) {
            if (i % 2 == 0) {// 除以二取余数，余数为0则为偶数,否则奇数
                mPath.lineTo(pointFList.get(i).x, pointFList.get(i).y);
            }

        }

        if (angleCount % 2 == 0) {  //偶数，moveTo
            mPath.moveTo(pointFList.get(1).x, pointFList.get(1).y);
        } else {                    //奇数，lineTo
            mPath.lineTo(pointFList.get(1).x, pointFList.get(1).y);
        }

        for (int i = 3; i < angleCount; i++) {
            if (i % 2 != 0) {
                mPath.lineTo(pointFList.get(i).x, pointFList.get(i).y);
            }
        }

        mPath.offset(startRadius, startRadius);
        return mPath;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 圆角图片的范围
        if (type == TYPE_ROUND)
            mRoundRect = new RectF(0, 0, w, h);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private static final String STATE_INSTANCE = "state_instance";
    private static final String STATE_TYPE = "state_type";
    private static final String STATE_BORDER_RADIUS = "state_border_radius";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
        bundle.putInt(STATE_TYPE, type);
        bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            super.onRestoreInstanceState(((Bundle) state)
                    .getParcelable(STATE_INSTANCE));
            this.type = bundle.getInt(STATE_TYPE);
            this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
        } else {
            super.onRestoreInstanceState(state);
        }

    }

    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE && this.type != TYPE_MULTI) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }

    }

}
