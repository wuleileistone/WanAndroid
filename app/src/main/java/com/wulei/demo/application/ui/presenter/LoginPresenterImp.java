package com.wulei.demo.application.ui.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wulei.demo.application.ui.view.LoginView;

/**
 * Created by wulei on 2019/1/30.
 */
public class LoginPresenterImp implements LoginPresenter {

    @Override
    public void login(LoginView loginView, String name, String password, String url) {
        OkGo.<String>post(url)
                .tag("login")
                .params("username", name)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        loginView.loginSuccess(body);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        String body = response.body();
                        loginView.loginFail(body);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });

    }
}
