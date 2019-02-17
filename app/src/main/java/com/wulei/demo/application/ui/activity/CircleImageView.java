package com.wulei.demo.application.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;


@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

    private Bitmap mask;
    private Paint paint;
    private Paint mBorderPaint;
    private int mBorderWidth = 2;
    private int mBorderColor = Color.BLUE;

    public CircleImageView(Context paramContext) {
        super(paramContext);
    }

    public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public CircleImageView(Context context, AttributeSet paramAttributeSet, int paramInt) {
        super(context, paramAttributeSet, paramInt);
//        TypedArray a = context.obtainStyledAttributes(paramAttributeSet, R.styleable.CircularImage);
//        mBorderColor = a.getColor(R.styleable.CircularImage_border_color, mBorderColor);
//        float density = context.getResources().getDisplayMetrics().density; // 获取屏幕密度
//        mBorderWidth = a.getDimensionPixelOffset(R.styleable.CircularImage_border_width, 0);
//        a.recycle();
    }

    private boolean useDefaultStyle = false;

    public void setUseDefaultStyle(boolean useDefaultStyle) {
        this.useDefaultStyle = useDefaultStyle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (useDefaultStyle) {
            super.onDraw(canvas);
            return;
        }

        Drawable drawable = getDrawable();
        if (drawable == null) {
            super.draw(canvas);
            return;
        }

        /**
         * 第一种方法，使用图层进行图片混合
         */
        Drawable localDrawable = editDrawable(drawable);
        drawCircleImage(canvas, localDrawable);

        /**
         * 第二种方法，创建新的画布，并在该画布上进行混合,生成混合后的bitmap，
         * 再将该bitmap画回到该View的原画布上
         */
//        Bitmap src = ((BitmapDrawable) drawable).getBitmap();
//        Bitmap bitmap = createCircleImage(src);
//        canvas.drawBitmap(bitmap, 0, 0, null);

        // 对圆形图像进行描边
        drawBorder(canvas, getWidth(), getHeight());
    }

    private void drawCircleImage(Canvas canvas, Drawable localDrawable) {
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        localDrawable.draw(canvas);

        if (mask == null)
            mask = createOvalBitmap(getWidth(), getHeight());

        if (this.paint == null) {
            final Paint localPaint = new Paint();
            localPaint.setFilterBitmap(false);
            localPaint.setAntiAlias(true);
            // 关键！DST_IN具体情况请查阅官方文档
            localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            this.paint = localPaint;
        }
        canvas.drawBitmap(mask, 0, 0, paint);
        canvas.restore();
    }

    private void drawBorder(Canvas canvas, final int width, final int height) {
        if (mBorderWidth == 0) {
            return;
        }

        if (mBorderPaint == null) {
            mBorderPaint = new Paint();
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }

        RectF rectF = new RectF(0 + mBorderWidth, 0 + mBorderWidth,
                width - mBorderWidth, height - mBorderWidth);
        canvas.drawOval(rectF, mBorderPaint);


    }

    private Drawable editDrawable(Drawable localDrawable) {
        int drawableWidth = localDrawable.getIntrinsicWidth();
        int drawableHeight = localDrawable.getIntrinsicHeight();
        Log.i("xxx", "" + drawableWidth + "," + drawableHeight);
        final int width = getWidth();
        final int height = getHeight();
        Log.i("xxx", "" + width + "," + height);
        float rw = (float) drawableWidth / (float) width;
        float rh = (float) drawableHeight / (float) height;
        int dWidth;
        int dHeight;
        if (rw < rh) {
            dWidth = width;
            dHeight = (int) (drawableHeight / rw);
            //移动到中间的偏移量
            int yOff = (dHeight - height) / 2;
            localDrawable.setBounds(0, -yOff, dWidth, dHeight - yOff);
        } else {
            dWidth = (int) (drawableWidth / rh);
            dHeight = height;
            int xOff = (dWidth - width) / 2;
            localDrawable.setBounds(-xOff, 0, dWidth - xOff, dHeight);
        }
        return localDrawable;
    }

    /**
     * 创建圆形画布
     *
     * @param width
     * @param height
     * @return
     */
    public Bitmap createOvalBitmap(final int width, final int height) {
        Bitmap localBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        localPaint.setColor(Color.GREEN);
        localPaint.setAntiAlias(true); // 很重要！设置抗锯齿
        int padding = mBorderWidth;
        RectF localRectF = new RectF(padding, padding, width - padding, height - padding);
        localCanvas.drawOval(localRectF, localPaint);

        return localBitmap;
    }

    private Bitmap createCircleImage(Bitmap source) {
        Bitmap target = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        /**
         * 首先绘制圆形
         */
        int padding = mBorderWidth;
        RectF localRectF = new RectF(padding, padding, getWidth() - padding, getHeight() - padding);
        canvas.drawOval(localRectF, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        /**
         * 绘制图片
         */
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        Log.i("xxx", "sourceWidth:" + sourceWidth + ",sourceHeight:" + sourceHeight);
        final int width = getWidth();
        final int height = getHeight();
        Log.i("xxx", "width:" + width + ",height:" + height);
        float rw = (float) sourceWidth / (float) width;
        float rh = (float) sourceHeight / (float) height;
        int dWidth;
        int dHeight;
        if (rw < rh) {
            dWidth = width;
            dHeight = (int) (sourceHeight / rw);
            //移动到中间的偏移量
            int yOff = (dHeight - height) / 2;
            canvas.drawBitmap(source, new Rect(0, 0, sourceWidth, sourceHeight),
                    new Rect(0, -yOff, dWidth, dHeight - yOff), paint);

        } else {
            dWidth = (int) (sourceWidth / rh);
            dHeight = height;
            int xOff = (dWidth - width) / 2;
            canvas.drawBitmap(source, new Rect(0, 0, sourceWidth, sourceHeight),
                    new Rect(-xOff, 0, dWidth - xOff, dHeight), paint);
        }
        Log.i("xxx", "dWidth:" + dWidth + ",dHeight:" + dHeight);

        return target;
    }
}
