package com.wulei.demo.application.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by wulei on 2019/1/30.
 */
public class RotatePageTransformer implements ViewPager.PageTransformer{

    private final float MAX_ROTATION = 25.0f;//旋转的最大角度


    public void transformPage(View view, float position) {

//        view是viewpager的初始化方法中返回view对象，view是多个不同对象
//        A页面的position：[0,-1]
//        B页面的position： [1,0]

        int pageWidth = view.getWidth();

        //根据position的取值范围，区分A，B页面并设置不同的动画

        if (position < -1) { // [-Infinity,-1)
            view.setRotation(0);//设置左边的view不旋转

        } else if (position <= 0) { // [-1,0]
            //        A页面的position：[0,-1]
            //A页面执行旋转动画 取值[0,-25]
            view.setRotation(position * MAX_ROTATION);
            view.setPivotX(pageWidth / 2);
            view.setPivotY(view.getHeight());

        } else if (position <= 1) { // (0,1]
            //        B页面的position： [1,0]
            //B页面执行旋转动画 取值[25,0]
            view.setRotation(position*MAX_ROTATION);
            view.setPivotX(pageWidth / 2);
            view.setPivotY(view.getHeight());

        } else { // (1,+Infinity]
            view.setRotation(0);//设置右边不旋转
        }
    }
}
