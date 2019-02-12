package com.wulei.demo.application.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.umeng.socialize.UMShareAPI;
import com.wulei.demo.application.R;
import com.wulei.demo.application.ui.fragment.LoginFragment;
import com.wulei.demo.application.ui.fragment.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginAndRegisterActivity extends BaseActivity {

    @BindView(R.id.login_register)
    FrameLayout loginRegister;
    private FragmentManager manager;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected void initData() {
        setTranslucentStatus();
    }

    @Override
    protected void initView() {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        showLoginView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_and_register;
    }

    private void showLoginView() {
        manager.beginTransaction().add(R.id.login_register,loginFragment).addToBackStack("login").commit();
    }

    public void showRegisterFragment() {
        manager.beginTransaction().add(R.id.login_register, registerFragment).addToBackStack("register").commit();
    }
    public void removeRegisterFragment() {
        manager.beginTransaction().remove(registerFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
