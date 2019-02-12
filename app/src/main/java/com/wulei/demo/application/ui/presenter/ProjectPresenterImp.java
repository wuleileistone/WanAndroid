package com.wulei.demo.application.ui.presenter;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wulei.demo.application.been.ProjectBean;
import com.wulei.demo.application.ui.view.ProgectView;

import java.util.List;

/**
 * Created by wulei on 2019/2/3.
 */
public class ProjectPresenterImp implements ProjectPresenter {
    @Override
    public void reQuestData(ProgectView projectView, String url) {

        OkGo.<String>get(url).tag("progect").execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                ProjectBean projectBean = JSONObject.parseObject(body, ProjectBean.class);
                List<ProjectBean.DataBean> data = projectBean.getData();
                if (projectBean.getErrorCode() == 0) {
                    projectView.getProjectData("项目数据请求成功", data);
                } else {

                }
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
