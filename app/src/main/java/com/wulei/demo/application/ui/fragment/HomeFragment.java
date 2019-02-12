package com.wulei.demo.application.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wulei.demo.application.MainActivity;
import com.wulei.demo.application.R;
import com.wulei.demo.application.adapter.HomeAdapter;
import com.wulei.demo.application.been.BannerBean;
import com.wulei.demo.application.been.HomeDetailBean;
import com.wulei.demo.application.ui.presenter.HomePresenter;
import com.wulei.demo.application.ui.presenter.HomePresenterImp;
import com.wulei.demo.application.ui.view.HomeView;
import com.wulei.demo.application.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private static HomeFragment homeFragment = new HomeFragment();
    @BindView(R.id.header_home)
    ClassicsHeader headerHome;
    @BindView(R.id.recyclerview_home)
    RecyclerView recyclerviewHome;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.rootview)
    LinearLayout rootview;
    Unbinder unbinder;
    private HomePresenter homePresenter;
    private Context context;
    private int pageNum = 0;
    private View headerBanner;
    private HomeAdapter homeAdapter;
    private Banner bannerLayout;;
    private Banner banner;

    public static HomeFragment getHomeFragment() {
        return homeFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initData() {
        smartrefreshlayout.autoRefresh();
        smartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNum++;
                String url = "http://www.wanandroid.com/article/list/" + pageNum + "/json";
                homePresenter.getData(homeFragment, refreshLayout, url, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                String url = "http://www.wanandroid.com/article/list/" + 0 + "/json";
                homePresenter.getData(homeFragment, refreshLayout, url, true);
                String banner = "http://www.wanandroid.com/banner/json";
                homePresenter.getBannerData(homeFragment,banner);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewHome.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(getActivity(),R.layout.item_home, null);
        headerBanner = LayoutInflater.from(context).inflate(R.layout.header_banner, smartrefreshlayout, false);
        homeAdapter.addHeaderView(headerBanner);
        recyclerviewHome.setAdapter(homeAdapter);

        homeAdapter.setEmptyView(R.layout.empty_view, rootview);
    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this, getView());
        homePresenter = new HomePresenterImp();
        this.context = (MainActivity)getActivity();
    }


    private void setBanner(View view, ArrayList<String> images, List<String> data, List<BannerBean.DataBean> dataBeans) {
        bannerLayout = view.findViewById(R.id.banner);
        banner = bannerLayout.setImages(images).setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
//        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setBannerTitles(data);
        banner.start();

//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                switch (position) {
////                    case 0:
////                        startActivity(new Intent(mContext, PublicActivity.class));
////                        break;
//                    default:
//                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
//                        intent.putExtra("link", dataBeans.get(position).getUrl());
//                        startActivity(intent);
//                        break;
//                }
//
//            }
//        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getHomeDataSuccess(String msg, HomeDetailBean homeDetailBean) {
           //初次进入后刷新数据获取
        homeAdapter.setNewData(homeDetailBean.getData().getDatas());
        smartrefreshlayout.finishRefresh();

    }

    @Override
    public void getHomeDataFail(String msg) {

    }

    @Override
    public void getMoreHomeDataSuccess(String msg, HomeDetailBean homeDetailBean) {
     //下拉获取更多的数据获取

        homeAdapter.addData(homeDetailBean.getData().getDatas());
        smartrefreshlayout.finishLoadmore();
    }

    @Override
    public void getMoreHomeDataFail(String msg) {

    }

    @Override
    public void getBannerDataSuccess(String msg, List<String> images, List<String> titles, BannerBean bannerData) {
        //轮播图的数据返回

        setBanner(headerBanner, (ArrayList<String>) images,titles,bannerData.getData());
    }

    @Override
    public void getBannerDataFail() {

    }

}
