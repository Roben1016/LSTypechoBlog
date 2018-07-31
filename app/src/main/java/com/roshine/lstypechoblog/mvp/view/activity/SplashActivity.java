package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.customs.WeakRefHandler;

public class SplashActivity extends AppCompatActivity implements Handler.Callback {

    private static final int HANDLE_GO = 100;
    private WeakRefHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler = new WeakRefHandler(this);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(HANDLE_GO, null),
                1000 * 3);
    }

    //判断APP是否打开过
    private void isAppOpened() {
//        if(SPUtil.checkLogined()){
//            goMain();
//        }else{
            goLogin();
//        }
    }

    //跳转到登录界面
    private void goLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //跳转到引导界面
    private void goMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLE_GO:
                isAppOpened();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
