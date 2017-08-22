package com.example.admin.customview;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by fushuang on 2017/8/16.
 */

public class GifView extends RelativeLayout implements View.OnClickListener {


    private Drawable mRed, mYellow, mBlue;
    private Drawable[] drawables;
    private Interpolator[] interpolators;
    private int height;
    private int width;
    private Random random=new Random();
    private int intrinsicWidth;
    private int intrinsicHeight;
    private LayoutParams params;
    private PointF p0;
    private PointF p1;
    private PointF p2;
    private PointF p3;

    public GifView(Context context) {
        super(context,null);
    }

    public GifView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        interpolators=new Interpolator[4];
        drawables=new Drawable[3];
        drawables[0]=mRed=getResources().getDrawable(R.mipmap.pl_red);
        drawables[1]= mYellow=getResources().getDrawable(R.mipmap.pl_yellow);
        drawables[2]=mBlue=getResources().getDrawable(R.mipmap.pl_blue);
        interpolators[0] = new LinearInterpolator();// 线性
        interpolators[1] = new AccelerateDecelerateInterpolator();// 先加速后减速
        interpolators[2] = new AccelerateInterpolator();// 加速
        interpolators[3] = new DecelerateInterpolator();// 减速
        intrinsicWidth = mRed.getIntrinsicWidth();
        intrinsicHeight = mRed.getIntrinsicHeight();
        params = new LayoutParams(intrinsicWidth, intrinsicHeight);
        params.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        params.addRule(ALIGN_PARENT_RIGHT, TRUE);
        params.setMargins(0, 0, 60, 60);//放置在屏幕的右下角

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }


    @Override
    public void onClick(View v) {
        ImageView imageView = new ImageView(this.getContext());
        imageView.setLayoutParams(params);
        imageView.setBackground(drawables[random.nextInt(drawables.length)]);
        addView(imageView);
        addAnimator(imageView);

    }

    private void addAnimator(final ImageView imageView) {
        initPointF();
        BezierEvaluator evaluator = new BezierEvaluator(p1, p2);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(evaluator, p0, p3);
        valueAnimator.setDuration(3000);
        valueAnimator.setTarget(imageView);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                imageView.setAlpha(1-animation.getAnimatedFraction());
                if (animation.getAnimatedFraction()>=1){
                    removeView(imageView);
                }
            }
        });
        valueAnimator.start();
    }

    //初始化贝塞尔曲线的四个点
    private void initPointF() {
        p0 = new PointF(width - 60 - intrinsicWidth, height - 60 - intrinsicHeight);
        p1 = new PointF(random.nextInt(width), random.nextInt((int) p0.y));
        p2 = new PointF(random.nextInt(width), random.nextInt((int) p1.y));
        p3 = new PointF(random.nextInt(width),- intrinsicHeight);
    }


    public class BezierEvaluator implements TypeEvaluator<PointF>{

        private PointF p1,p2;

        public BezierEvaluator(PointF p1, PointF p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public PointF evaluate(float t, PointF startValue, PointF endValue) {
            PointF pointF = new PointF();
            pointF.x=startValue.x * (1 - t) * (1 - t) * (1 - t) //
                    + 3 * p1.x * t * (1 - t) * (1 - t)//
                    + 3 * p2.x * t * t * (1 - t)//
                    + endValue.x * t * t * t;//

            pointF.y=startValue.y * (1 - t) * (1 - t) * (1 - t) //
                    + 3 * p1.y * t * (1 - t) * (1 - t)//
                    + 3 * p2.y * t * t * (1 - t)//
                    + endValue.y * t * t * t;//
            return pointF;
        }
    }


}
