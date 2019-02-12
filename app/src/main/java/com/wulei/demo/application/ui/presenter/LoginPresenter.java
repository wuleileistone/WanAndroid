package com.wulei.demo.application.ui.presenter;

import com.wulei.demo.application.ui.view.LoginView;

/**
 * Created by wulei on 2019/1/30.
 */
public interface LoginPresenter{
    void login(LoginView loginView, String name, String password, String url);
}
