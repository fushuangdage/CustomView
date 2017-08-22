package com.example.admin.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by fushuang on 2017/8/21.
 */

public class SelectView extends View {
    private Paint bitmapPaint;
    private Paint bitmapErase;
    private Paint checkeraser;
    private Drawable drawable;
    private int w;
    private int h;
    private Bitmap bitmap;
    private float progress=1;
    private int r;
    private boolean isChecked=true;
    private Paint circlePaint;

    public SelectView(Context context) {
        super(context);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(Color.RED);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.RED);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(dp(5));
        bitmapErase=new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapErase.setColor(0);
        bitmapErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        drawable = context.getResources().getDrawable(R.mipmap.check);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.check);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isChecked= !isChecked;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        progress= (float) animation.getAnimatedValue();
                        postInvalidate();
                    }
                });

                valueAnimator.start();
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        this.r=w/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        canvas.drawRect(0,0,w,h,bitmapPaint);

        if (isChecked){
//            canvas.drawBitmap(bitmap,0,0,bitmapPaint);
            canvas.drawCircle(r,r,r*progress,bitmapPaint);
            int dh = drawable.getIntrinsicHeight();
            int dw = drawable.getIntrinsicWidth();
            int left = (w - dw) / 2;
            int top = (h - dh) / 2;
            int right = (w + dw) / 2;
            int bottom = (h + dh) / 2;
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(canvas);
        }else {
            canvas.drawCircle(r,r,r-dp(5),circlePaint);
        }
    }

    public int dp(float value) {
        if (value == 0) {
            return 0;
        }
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

}

