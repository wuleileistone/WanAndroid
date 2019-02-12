package com.wulei.demo.application.ui.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wulei.demo.application.been.RegisterBean;
import com.wulei.demo.application.ui.view.RegisterView;

/**
 * Created by wulei on 2019/1/31.
 */
public class RegisterPresenterImp implements RegisterPresenter {
    @Override
    public void register(Context context,RegisterView registerView, String username, String password,String rePassword, String url) {

        OkGo.<String>post(url)
                .tag("register")
                .params("username", username)
                .params("password", password)
                .params("repassword", rePassword)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        RegisterBean registerBean = JSONObject.parseObject(body, RegisterBean.class);
                        if (registerBean.getErrorCode() == 0) {
                            RegisterBean.DataBean data = registerBean.getData();
                            registerView.registerSuccess("注册成功", data);

                            SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            sp.edit().putString("username", username)
                                    .putString("password", password)
                                    .commit();
                        }
                    }


                    @Override
                    public void onError(Response<String> response) {
                        String body = response.body();
                        RegisterBean registerBean = JSONObject.parseObject(body, RegisterBean.class);
                        registerView.registerFail(registerBean.getErrorMsg());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }
}
