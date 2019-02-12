package com.wulei.demo.application.ui.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wulei.demo.application.R;
import com.wulei.demo.application.utils.DensityUtil;
import com.wulei.demo.application.utils.ZoomOutPageTransformer;


public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private int[] imageIds = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3,R.mipmap.guide4};
    private Button mButton;
    private LinearLayout mLinearLayout;
    private ImageView mIv_focus;
    private ViewPager mViewPager;
    private int mPointMargin;

    protected void initView() {
        mButton = (Button) findViewById(R.id.bt);
        mButton.setOnClickListener(this);

        mLinearLayout = (LinearLayout) findViewById(R.id.ll_point_group);
        mIv_focus = (ImageView) findViewById(R.id.iv_focus);

//        mViewPager = (CustomViewPager) findViewById(R.id.viewpager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        GuideAdapter guideAdapter = new GuideAdapter();
        mViewPager.setAdapter(guideAdapter);

        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
//        mViewPager.setPageTransformer(true, new DepthPageTransformer());
//        mViewPager.setPageTransformer(true, new RotatePageTransformer());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动时，实时被调用起来
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mViewPager.getAdapter().getCount() - 1) {
                    mButton.setVisibility(View.VISIBLE);
                } else {
                    mButton.setVisibility(View.INVISIBLE);
                }

                //手指滑动时，要让选中点实时动起来即可
                //间距 = (position+比例值)*点之间的间距
                mIv_focus.setTranslationX((position+positionOffset)*mPointMargin);
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    protected void initData() {

        //根据引导界面的图片长度，动态添加小白点
        for (int i = 0; i < imageIds.length; i++) {
            //一定要设置白点为5dp的宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 5), DensityUtil
                    .dip2px(this, 5));
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.gray_dot);
            if (i != 0) {
                params.leftMargin = DensityUtil.dip2px(this, 15);
            }
            point.setLayoutParams(params);

            mLinearLayout.addView(point);
        }


        mIv_focus.post(new Runnable() {//当view 显示之后执行的任务
            @Override
            public void run() {
                //获取点之间的间距
                mPointMargin = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                System.out.println("pointMargin:" + mPointMargin);

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(GuideActivity.this, LoginAndRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(GuideActivity.this);
            iv.setBackgroundResource(imageIds[position]);

            container.addView(iv);

            //将当前的页面对象在初始化的时候也传递给控件来操作
//            mViewPager.addChildView(iv,position);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

            //将当前的页面对象从集合中删除
//            mViewPager.removeChildView(position);
        }
    }
}