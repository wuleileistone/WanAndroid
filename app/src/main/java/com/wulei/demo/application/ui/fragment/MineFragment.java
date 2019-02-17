package com.wulei.demo.application.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.wulei.demo.application.R;
import com.wulei.demo.application.ui.activity.BaiduMap;
import com.wulei.demo.application.ui.activity.CurrentLocationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class MineFragment extends BaseFragment {

    private static MineFragment mineFragment = new MineFragment();
    @BindView(R.id.baidumap)
    Button baidumap;
    Unbinder unbinder;
    Unbinder unbinder1;
    @BindView(R.id.currentmap)
    Button currentmap;
    Unbinder unbinder2;

    public static MineFragment getMineFragment() {
        return mineFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this, getView());

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }



    @OnClick({R.id.baidumap, R.id.currentmap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baidumap:
                Intent intent = new Intent(getActivity(), BaiduMap.class);
                startActivity(intent);

                break;
            case R.id.currentmap:
                Intent intent2 = new Intent(getActivity(), CurrentLocationActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
