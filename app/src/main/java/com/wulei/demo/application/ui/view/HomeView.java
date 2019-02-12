package com.wulei.demo.application.ui.view;

import com.wulei.demo.application.been.BannerBean;
import com.wulei.demo.application.been.HomeDetailBean;

import java.util.List;

/**
 * Created by wulei on 2019/1/31.
 */
public interface HomeView {

    void getHomeDataSuccess(String msg, HomeDetailBean homeDetailBean);

    void getHomeDataFail(String msg);

    void getMoreHomeDataSuccess(String msg, HomeDetailBean homeDetailBean);

    void getMoreHomeDataFail(String msg);

    void getBannerDataSuccess(String msg, List<String> images, List<String> titles, BannerBean bannerData);

    void getBannerDataFail();

}
