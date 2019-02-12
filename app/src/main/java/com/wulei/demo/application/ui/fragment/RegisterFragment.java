package com.wulei.demo.application.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wulei.demo.application.MainActivity;
import com.wulei.demo.application.R;
import com.wulei.demo.application.been.RegisterBean;
import com.wulei.demo.application.ui.activity.LoginAndRegisterActivity;
import com.wulei.demo.application.ui.presenter.RegisterPresenter;
import com.wulei.demo.application.ui.presenter.RegisterPresenterImp;
import com.wulei.demo.application.ui.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class RegisterFragment extends Fragment implements RegisterView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_account)
    TextInputEditText etAccount;
    @BindView(R.id.til_account)
    TextInputLayout tilAccount;
    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.tiet_repassword)
    TextInputEditText tietRepassword;
    @BindView(R.id.til_repassword)
    TextInputLayout tilRepassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;
    private View view;
    private Context context;
    private RegisterPresenter registerPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = (LoginAndRegisterActivity) getActivity();
        registerPresenter = new RegisterPresenterImp();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.register_fragment, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

//                Toast.makeText(context, "点击了", Toast.LENGTH_SHORT).show();
                LoginAndRegisterActivity activity = (LoginAndRegisterActivity) getActivity();
                activity.removeRegisterFragment();

                break;
            case R.id.btn_submit:
                String account = tilAccount.getEditText().getText().toString().trim();
                String password = tilPassword.getEditText().getText().toString().trim();
                String rePassword = tilRepassword.getEditText().getText().toString().trim();
                tilAccount.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                //验证用户名和密码
                if (validateAccount(account) && validatePassword(password) && validateRePassword(rePassword)) {
                    String url = "http://www.wanandroid.com/user/register";
                    registerPresenter.register((LoginAndRegisterActivity)getActivity(),this,account,password,rePassword,url);
                }

                break;
        }
    }

    @Override
    public void registerSuccess(String msg, RegisterBean.DataBean dataBean) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        //Todo :在这里保存新注册的用户的一些信息，用于下次登陆的时候回显在登陆界面
        LoginAndRegisterActivity activity = (LoginAndRegisterActivity) getActivity();
        activity.finish();
    }

    @Override
    public void registerFail(String msg) {

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


    /**
     * 验证密码
     *
     * @param rePassword
     * @return
     */
    private boolean validateRePassword(String rePassword) {

        if (TextUtils.isEmpty(rePassword)) {
            showError(tilPassword, "密码不能为空");
            return false;
        }

        if (rePassword.length() < 6 || rePassword.length() > 18) {
            showError(tilPassword, "密码长度为6-18位");
            return false;
        }

        if (!TextUtils.equals(rePassword, rePassword)) {
            showError(tilRepassword, "两次密码不相同");
            return false;
        }
        return true;

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
}
