package com.example.admin.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.admin.customview.ComstomKeyboard.KeyboardUtil;

public class GiftActivity extends AppCompatActivity implements View.OnClickListener {

    private KeyboardUtil keyboardUtil;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);

        editText = (EditText) findViewById(R.id.edit_text);
        editText.setOnClickListener(this);
        keyboardUtil = new KeyboardUtil(this);
//        keyboardUtil.attachTo(editText);




//        final StartView startView = (StartView) findViewById(R.id.start);
//        startView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startView.startAnimator();
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        keyboardUtil.attachTo(editText);
    }
}
