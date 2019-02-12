package com.wulei.demo.application.ui.presenter;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wulei.demo.application.been.TiXiBean;
import com.wulei.demo.application.ui.view.TixiView;

import java.util.List;

/**
 * Created by wulei on 2019/2/1.
 */
public class TixiPresenterImp implements TixiPresenter {
    @Override
    public void getTixiData(TixiView tixiView, String url) {
        OkGo.<String>get(url)
                .tag("tixi")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        TiXiBean tiXiBean = com.alibaba.fastjson.JSONObject.parseObject(response.body(), TiXiBean.class);
                        List<TiXiBean.DataBean> data = tiXiBean.getData();
                        tixiView.TixiData("获取体系数据成功",data);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }
}
