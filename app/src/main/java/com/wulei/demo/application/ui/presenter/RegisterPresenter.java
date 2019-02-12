package com.wulei.demo.application.ui.presenter;

import android.content.Context;

import com.wulei.demo.application.ui.view.RegisterView;

/**
 * Created by wulei on 2019/1/31.
 */
public interface RegisterPresenter {
    void register(Context context, RegisterView registerView, String username, String password, String rePassword, String url);

}
