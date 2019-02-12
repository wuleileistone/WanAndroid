package com.wulei.demo.application.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wulei.demo.application.R;
import com.wulei.demo.application.adapter.TiXiAdapter;
import com.wulei.demo.application.been.TiXiBean;
import com.wulei.demo.application.ui.presenter.TixiPresenter;
import com.wulei.demo.application.ui.presenter.TixiPresenterImp;
import com.wulei.demo.application.ui.view.TixiView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class TixiFragment extends BaseFragment implements TixiView {

    private static TixiFragment tixiFragment = new TixiFragment();
    @BindView(R.id.recyclerview_tixi)
    RecyclerView recyclerviewTixi;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.rootview)
    LinearLayout rootview;
    Unbinder unbinder;
    private Context context;
    private TiXiAdapter adapter;
    private TixiPresenter tixiPresenter;
    private View footer;

    public static TixiFragment getTixiFragment() {

        return tixiFragment;
    }

    @Override

    protected int getViewId() {
        return R.layout.tixi_fragment;
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerviewTixi.setLayoutManager(linearLayoutManager);
        adapter = new TiXiAdapter(R.layout.item_tixi, null);
        recyclerviewTixi.setAdapter(adapter);
        smartrefreshlayout.autoRefresh();
        smartrefreshlayout.setEnableLoadMore(false);
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh(refreshLayout);
            }
        });

        adapter.setEmptyView(R.layout.empty_view,rootview);
    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this, getView());
        this.context = getActivity();
        tixiPresenter = new TixiPresenterImp();
        footer = View.inflate(getActivity(), R.layout.tixi_footer, null);
    }

    private void refresh(RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTixiData();
            }
        }, 2000);
    }

    private void getTixiData() {
        String url = "http://www.wanandroid.com/tree/json";
        tixiPresenter.getTixiData(this, url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void TixiData(String msg, List<TiXiBean.DataBean> data) {
        adapter.setNewData(data);
        smartrefreshlayout.finishRefresh();

        LinearLayout footerLayout = adapter.getFooterLayout();
        if (footerLayout == null) {
            adapter.setFooterView(footer);
        }
    }
}
