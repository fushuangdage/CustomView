package com.example.admin.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by fushuang on 2017/8/16.
 */

public class ColorTextView extends TextView {

    private TextPaint afterPaint;
    private TextPaint beforePaint;
    public String text;
    public float mCurrentProgress = 0.6f;


    public void setmCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        postInvalidate();
    }

    public void setText(String text) {
        this.text = text;
    }

    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        afterPaint = new TextPaint();
        afterPaint.setAntiAlias(true);
        afterPaint.setColor(Color.GREEN);
        afterPaint.setDither(true);
        afterPaint.setTextSize(getTextSize());
        beforePaint = new TextPaint();
        beforePaint.setAntiAlias(true);
        beforePaint.setColor(Color.BLACK);
        beforePaint.setDither(true);
        beforePaint.setTextSize(getTextSize());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int middle = (int) (width * mCurrentProgress);
        drawText(canvas,beforePaint,0,middle);
        drawText(canvas,afterPaint,middle,width);
    }

    private void drawText(Canvas canvas, Paint paint, int startX, int endX) {
        text=getText().toString();
        canvas.save();
        //裁剪画布,获取绘制区域
        int height = getMeasuredHeight();
        canvas.clipRect(startX,0,endX, height);

        Rect rect = new Rect();
        beforePaint.getTextBounds(text,0,text.length(),rect);
        Paint.FontMetrics fontMetrics = beforePaint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float yBaseline = getMeasuredHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(text,0,text.length(),getMeasuredWidth()/2-rect.width()/2,yBaseline,paint);
        canvas.restore();
    }


}
