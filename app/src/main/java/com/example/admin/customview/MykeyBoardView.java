package com.example.admin.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by fushuang on 2017/8/22.
 */

public class MykeyBoardView extends KeyboardView {


    private Context context;

    public MykeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MykeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Keyboard keyboard = getKeyboard();
        List<Keyboard.Key> keys=null;
        if (keyboard != null) {
            keys = keyboard.getKeys();
            for (Keyboard.Key key : keys) {
                if (key.codes[0]==-4) {
                   drawKeyBackground(R.drawable.bg_keyboardview_yes,canvas,key);
                    drawText(canvas,key);
                }
            }
        }
    }

    private void drawText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);


        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }

    }

    private void drawKeyBackground(int id, Canvas canvas, Keyboard.Key key) {
        Drawable drawable = context.getResources().getDrawable(id);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0]!=0) {
            drawable.setState(drawableState);
        }
        drawable.setBounds(key.x,key.y, key.x+key.width,key.y+key.height);
        drawable.draw(canvas);
    }
}
