package com.wulei.demo.application.ui.presenter;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wulei.demo.application.ui.view.HomeView;

/**
 * Created by wulei on 2019/1/31.
 */
public interface HomePresenter {

    void getData(HomeView homeView, RefreshLayout refreshlayout, String url, boolean isRefresh);

    void getBannerData(HomeView homeView,String url);

}
