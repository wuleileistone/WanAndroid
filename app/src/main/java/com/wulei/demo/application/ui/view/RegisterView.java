package com.wulei.demo.application.ui.view;

import com.wulei.demo.application.been.RegisterBean;

/**
 * Created by wulei on 2019/1/31.
 */
public interface RegisterView {

    void registerSuccess(String msg, RegisterBean.DataBean dataBean);

    void registerFail(String msg);

}
