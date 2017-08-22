package com.example.admin.customview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by fushuang on 2017/8/18.
 */

public class StartView extends LinearLayout {

    private int[] childCount = new int[1];

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d("6666666666666", "handleMessage: ");
            View childAt = getChildAt(msg.what);
            TranslateAnimation  animation = new TranslateAnimation(0, 0, 0, 500);
            animation.setDuration(500);
            animation.setFillAfter(true);
            childAt.startAnimation(animation);
        }
    };

    public StartView(Context context) {
        super(context);
    }

    public StartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        for (int i = 0; i < 16; i++) {
            TextView textView = new TextView(getContext());
            textView.setText("*");
            textView.setTextSize(20);
            if (i % 4 == 0) {
                textView.setPadding(10, 0, 0, 0);
            }
            addView(textView);
        }
//        ViewAnimationUtils.createCircularReveal()
    }

    public void startAnimator() {
        for (int i = 0; i < 16; i++) {
            handler.sendEmptyMessageDelayed(i, i * 50);
        }

    }


}
