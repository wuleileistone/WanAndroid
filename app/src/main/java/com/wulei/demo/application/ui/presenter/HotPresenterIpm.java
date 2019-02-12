package com.wulei.demo.application.ui.presenter;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wulei.demo.application.been.HotBean;

/**
 * Created by wulei on 2019/2/1.
 */
public class HotPresenterIpm implements HotPresenter {
    @Override
    public void getHotData(HotView hotView, String url) {

        OkGo.<String>get(url).tag("hot")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        HotBean resultBean = JSONObject.parseObject(response.body(), HotBean.class);
                        if (resultBean.getErrorCode() == 0) {
                            hotView.getHotDta("热门数据请求成功", resultBean);
                        } else {
                            //错误请求
                        }
                    }
                });

    }
}
