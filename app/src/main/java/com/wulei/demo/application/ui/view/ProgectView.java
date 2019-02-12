package com.wulei.demo.application.ui.view;

import com.wulei.demo.application.been.ProjectBean;

import java.util.List;

/**
 * Created by wulei on 2019/2/3.
 */
public interface ProgectView {

    void getProjectData(String msg, List<ProjectBean.DataBean> data);

}
