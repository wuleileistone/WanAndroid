package com.wulei.demo.application.ui.presenter;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wulei.demo.application.been.BannerBean;
import com.wulei.demo.application.been.HomeDetailBean;
import com.wulei.demo.application.ui.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2019/1/31.
 */
public class HomePresenterImp implements HomePresenter {

    @Override
    public void getData(HomeView homeView, RefreshLayout refreshlayout, String url, boolean isRefresh) {

        refreshlayout.getLayout().postDelayed(() -> {
            if (isRefresh) {
                getHomeData(homeView,url);
            } else {
                getMoreHomeData(homeView,url);
            }
        }, 2000);
    }

    private void getMoreHomeData(HomeView homeView,String url) {
        OkGo.<String>get(url)
                .tag("home1")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        HomeDetailBean homeDetailBean = JSONObject.parseObject(response.body(), HomeDetailBean.class);

                        homeView.getMoreHomeDataSuccess("获取更多数据成功", homeDetailBean);

                    }

                    @Override
                    public void onError(Response<String> response) {
                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }

    private void getHomeData(HomeView homeView,String url) {
        OkGo.<String>get(url)
                .tag("home")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        HomeDetailBean homeDetailBean = JSONObject.parseObject(response.body(), HomeDetailBean.class);
                        homeView.getHomeDataSuccess("成功请求数据", homeDetailBean);
                    }

                    @Override
                    public void onError(Response<String> response) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    @Override
    public void getBannerData(HomeView homeView, String url) {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> images = new ArrayList<>();

        OkGo.<String>get(url)
                .tag("banner")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();

                        BannerBean bean = JSONObject.parseObject(response.body(), BannerBean.class);
                        List<BannerBean.DataBean> data = bean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            titles.add(data.get(i).getTitle());
                            images.add(data.get(i).getImagePath());
                        }
                        homeView.getBannerDataSuccess("获取轮播图消息成功",images,titles,bean);
                    }


                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });
    }
}
