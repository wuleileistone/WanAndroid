package com.wulei.demo.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wulei.demo.application.ui.activity.BaseActivity;
import com.wulei.demo.application.ui.fragment.HomeFragment;
import com.wulei.demo.application.ui.fragment.HotFragment;
import com.wulei.demo.application.ui.fragment.MineFragment;
import com.wulei.demo.application.ui.fragment.ProjectFragment;
import com.wulei.demo.application.ui.fragment.TixiFragment;
import com.wulei.demo.application.utils.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_contain)
    FrameLayout mainContain;
    @BindView(R.id.main_navigation)
    BottomNavigationView mainNavigation;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    protected void initData() {
//        setImmerseLayout(mainContain);
//        setTranslucentStatus();
        SharedPreferences sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isfrist", false)
                .commit();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_contain, HomeFragment.getHomeFragment(), "home").commit();

        BottomNavigationViewHelper.disableShiftMode(mainNavigation);
        mainNavigation.setItemIconTintList(null);

        mainNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {

                    case R.id.home:

                        fragmentTransaction.replace(R.id.main_contain, HomeFragment.getHomeFragment(), "home").commit();
                        return true;

                    case R.id.hot:
                        fragmentTransaction.replace(R.id.main_contain, HotFragment.getHotFragment(), "hot").commit();
                        return true;

                    case R.id.tixi:
                        fragmentTransaction.replace(R.id.main_contain, TixiFragment.getTixiFragment(), "tixi").commit();
                        return true;

                    case R.id.project:
                        fragmentTransaction.replace(R.id.main_contain, new ProjectFragment(), "project").commit();
                        return true;

                    case R.id.mine:
                        fragmentTransaction.replace(R.id.main_contain, MineFragment.getMineFragment(), "mine").commit();
                        Toast.makeText(App.getContext(), "点击了mine", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        });
    }



}
