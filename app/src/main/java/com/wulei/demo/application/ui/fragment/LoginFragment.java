package com.wulei.demo.application.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wulei.demo.application.App;
import com.wulei.demo.application.MainActivity;
import com.wulei.demo.application.R;
import com.wulei.demo.application.been.LoginBeen;
import com.wulei.demo.application.ui.activity.LoginAndRegisterActivity;
import com.wulei.demo.application.ui.presenter.LoginPresenter;
import com.wulei.demo.application.ui.presenter.LoginPresenterImp;
import com.wulei.demo.application.ui.view.LoginView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/30.
 */
public class LoginFragment extends Fragment implements LoginView {

    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.weixin)
    ImageView weixin;
    Unbinder unbinder;
    @BindView(R.id.register)
    TextView register;
    private View view;
    private Context context;
    private LoginPresenter loginPresenter;
    private long exitTime = 0;
    private String mAccount;
    private String mPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = (LoginAndRegisterActivity) getActivity();

        loginPresenter = new LoginPresenterImp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.login_fragment, container, false);
        }

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (username != null && password != null) {
            etAccount.setText(username);
            tietPassword.setText(password);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //得到Fragment的根布局并使该布局可以获得焦点
        getView().setFocusableInTouchMode(true);
        //得到Fragment的根布局并且使其获得焦点
        getView().requestFocus();
        //对该根布局View注册KeyListener的监听
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    Log.i("aaa", System.currentTimeMillis() + "");
                    if ((System.currentTimeMillis() - exitTime) > 2000) {
                        Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                        //记录最后一次按键时间
                        exitTime = System.currentTimeMillis();
                    } else {
//                    这里是我做了保存数据操作
//                    BLEDeviceLab.get(getActivity()).saveDevices();
//                    mEditor.putString("ip", ipAddress);
//                    mEditor.commit();
                        getActivity().finish();
                        System.exit(0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loginSuccess(String msg) {
        LoginBeen loginBeen = JSONObject.parseObject(msg, LoginBeen.class);
        int errorCode = loginBeen.getErrorCode();
        if (errorCode == 0) {
            LoginBeen.DataBean data = loginBeen.getData();
            showError("登录成功！");
            SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
            sp.edit().putString("username", mAccount)
                    .putString("password", mPassword)
                    .commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    LoginAndRegisterActivity activity = (LoginAndRegisterActivity) getActivity();
                    activity.finish();

                }
            }, 1500);
        } else {
            showError(loginBeen.getErrorMsg());
        }

    }

    @Override
    public void loginFail(String msg) {
        showError(msg);
    }

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    private boolean validateAccount(String account) {
        if (TextUtils.isEmpty(account)) {
            showError(tilAccount, "用户名不能为空");
            return false;
        }
        return true;
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    private boolean validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            showError(tilPassword, "密码不能为空");
            return false;
        }

        if (password.length() < 6 || password.length() > 18) {
            showError(tilPassword, "密码长度为6-18位");
            return false;
        }

        return true;
    }

    private void showError(String error) {
        Toast.makeText(App.getContext(), error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    @OnClick({R.id.btn_submit, R.id.register, R.id.qq, R.id.weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String url = "http://www.wanandroid.com/user/login";
                mAccount = tilAccount.getEditText().getText().toString().trim();
                mPassword = tilPassword.getEditText().getText().toString().trim();
                tilAccount.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);
                //验证用户名和密码
                if (validateAccount(mAccount) && validatePassword(mPassword)) {
                    loginPresenter.login(this, mAccount, mPassword, url);
                }
                break;
            case R.id.register:
                LoginAndRegisterActivity activity = (LoginAndRegisterActivity) getActivity();
                activity.showRegisterFragment();
                break;
            case R.id.qq:
//                Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
                UMAuthListener authListener = new UMAuthListener() {
                    /**
                     * @desc 授权开始的回调
                     * @param platform 平台名称
                     */
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    /**
                     * @desc 授权成功的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param data 用户资料返回
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                        Toast.makeText(context, "成功了", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        LoginAndRegisterActivity activity1 = (LoginAndRegisterActivity) getActivity();
                        activity1.finish();
                    }

                    /**
                     * @desc 授权失败的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {

                        Toast.makeText(context, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @desc 授权取消的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform, int action) {
                        Toast.makeText(context, "取消了", Toast.LENGTH_LONG).show();
                    }
                };

                UMShareAPI.get(context).getPlatformInfo((LoginAndRegisterActivity)getActivity(), SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.weixin:
                break;
        }
    }




}
