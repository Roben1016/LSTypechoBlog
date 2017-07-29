package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roshine.lstypechoblog.R;

public class SplashActivity extends AppCompatActivity {

    private static final int HANDLE_GO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(HANDLE_GO, null),
                1000 * 3);
    }

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLE_GO:
                    isAppOpened();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    //判断APP是否打开过
    private void isAppOpened() {
//        goGuide();
        goLogin();
        finish();
    }

    //跳转到登录界面
    private void goLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    //跳转到引导界面
    private void goGuide() {

    }
}
