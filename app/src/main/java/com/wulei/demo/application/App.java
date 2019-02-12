package com.wulei.demo.application;

import android.app.Application;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by wulei on 2019/1/30.
 */
public class App extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        UMConfigure.init(this, "5c5201ecf1f55629dd0006b8", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        initView();
    }

    private void initView() {
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    public static Context getContext(){

        return context;
    }
}
