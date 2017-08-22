package com.example.admin.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fushuang on 2017/8/21.
 */

public class XfermodeView extends View {

    private Paint fgPaint;
    private Paint bgPaint;
    private Bitmap blue;
    private Bitmap red;

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        fgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        red = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pir_red);    //前景
        blue = BitmapFactory.decodeResource(context.getResources(), R.mipmap.pic_blue);  //背景
//        bgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        fgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        canvas.drawBitmap(red,new Rect(0,0,getMeasuredWidth(),getMeasuredHeight()),new RectF(0,0,red.getWidth(),red.getHeight()), bgPaint);
//        canvas.drawBitmap(blue,new Rect(0,0,getMeasuredWidth(),getMeasuredHeight()),new RectF(0,0,blue.getWidth(),blue.getHeight()),fgPaint);


        canvas.drawBitmap(blue,0,0, bgPaint);
        canvas.drawBitmap(red,0,0,fgPaint);

    }

}
