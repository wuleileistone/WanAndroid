package com.wulei.demo.application.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.wulei.demo.application.R;

public class SplashActivity extends BaseActivity {


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;

            if (what > 0) {
                what--;
                time.setText(what + "秒");
                Message obtain = Message.obtain();
                obtain.what = what;
                mHandler.sendMessageDelayed(obtain, 1000);
            } else {
                SharedPreferences sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                boolean isfrist = sp.getBoolean("isfrist", true);
                if (isfrist) {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginAndRegisterActivity.class));
                }

                finish();
            }
        }
    };
    private TextView time;


    @Override
    protected void initData() {
        setTranslucentStatus();
        Message msg = Message.obtain();
        msg.what = 3;
        mHandler.sendMessageDelayed(msg, 1000);
    }

    @Override
    protected void initView() {
        time = (TextView) findViewById(R.id.time);
//        setImmerseLayout(time);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
