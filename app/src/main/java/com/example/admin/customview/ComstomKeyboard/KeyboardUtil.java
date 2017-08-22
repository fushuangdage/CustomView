package com.example.admin.customview.ComstomKeyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.customview.MykeyBoardView;
import com.example.admin.customview.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by fushuang on 2017/8/22.
 */

public class KeyboardUtil implements KeyboardView.OnKeyboardActionListener {

    private final MykeyBoardView mKeyBoardView;
    private  Keyboard mKeyboder;
    private EditText mEditText;
    private Activity mActivity;

    public KeyboardUtil(Activity mActivity) {
        this.mActivity = mActivity;
        mKeyboder = new Keyboard(mActivity, R.xml.keyboardnumber);
        mKeyBoardView = ((MykeyBoardView) mActivity.findViewById(R.id.keyboard_view));
    }

    public void attachTo(EditText editText){
        mEditText = editText;
        hideSystemSofeKeyboard(mActivity,editText);
        showSoftKeyBoard();
    }


    private boolean isNumber(String str) {
        String wordstr = "0123456789";
        return wordstr.contains(str);
    }

    private void showSoftKeyBoard() {
//        mKeyBoardView.setKeyboard(mKeyboder);
        List<Keyboard.Key> keys = mKeyboder.getKeys();
        List<Keyboard.Key> newkeyList = new ArrayList<Keyboard.Key>();
        for (Keyboard.Key key : keys) {
            if (key.label!=null && isNumber(key.label.toString())) {
                newkeyList.add(key);
            }
        }

        int count = newkeyList.size();
        LinkedList<KeyModel> temp=new LinkedList<KeyModel>();
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(48 + i, i + ""));
        }

        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(count - i);
            KeyModel keyModel = temp.get(index);
            newkeyList.get(i).label=keyModel.getLable();
            newkeyList.get(i).codes[0]=keyModel.getCode();
            temp.remove(index);
        }

        mKeyBoardView.setKeyboard(mKeyboder);
        mKeyBoardView.setEnabled(true);
        mKeyBoardView.setPreviewEnabled(false);
        mKeyBoardView.setVisibility(View.VISIBLE);
        mKeyBoardView.setOnKeyboardActionListener(this);
    }


    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemSofeKeyboard(Context context, EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Editable editable = mEditText.getText();
        int start = mEditText.getSelectionStart();
        if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
            if (editable != null && editable.length() > 0) {
                if (start > 0) {
                    editable.delete(start - 1, start);
                }
            }
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 隐藏键盘
            hideKeyboard();
//            if (mOnCancelClick != null) {
//                mOnCancelClick.onCancellClick();
//            }
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
            hideKeyboard();
//            if (mOnOkClick != null) {
//                mOnOkClick.onOkClick();
//            }
        } else {
            editable.insert(start, Character.toString((char) primaryCode));
        }
    }

    private void hideKeyboard() {
        mKeyBoardView.setVisibility(View.GONE);
    }


    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {
        Toast.makeText(mActivity, "swipeLeft", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeRight() {
        Toast.makeText(mActivity, "swipeRight", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void swipeDown() {
        Toast.makeText(mActivity, "swipeDown", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void swipeUp() {
        Toast.makeText(mActivity, "swipeUp", Toast.LENGTH_SHORT).show();

    }
}
