package com.wulei.demo.application.ui.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wulei.demo.application.R;
import com.wulei.demo.application.been.ProjectBean;
import com.wulei.demo.application.ui.presenter.ProjectPresenter;
import com.wulei.demo.application.ui.presenter.ProjectPresenterImp;
import com.wulei.demo.application.ui.view.ProgectView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class ProjectFragment extends BaseFragment implements ProgectView {

    private static ProjectFragment projectFragment = new ProjectFragment();
    @BindView(R.id.project_tablayout)
    TabLayout projectTablayout;
    @BindView(R.id.project_container)
    ViewPager projectContainer;
    Unbinder unbinder;
    private VPAdapter adapter;
    private ProjectPresenter projectPresenter;

    public static ProjectFragment getProjectFragment() {

        return projectFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.project_fragment;
    }

    @Override
    protected void initData() {
        String url = "http://www.wanandroid.com/project/tree/json";
        projectPresenter.reQuestData(this, url);
    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this, getView());

        projectPresenter = new ProjectPresenterImp();
        projectTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getProjectData(String msg, List<ProjectBean.DataBean> data) {


        ArrayList<TabFragment> tabFragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            tabFragments.add(new TabFragment(data.get(i)));
            titles.add(data.get(i).getName());
        }
        if (adapter == null) {
            adapter = new VPAdapter(getChildFragmentManager(), tabFragments, titles);

        } else {
        }

        projectTablayout.setupWithViewPager(projectContainer, false);
        projectContainer.setAdapter(adapter);
    }

    private class VPAdapter extends FragmentPagerAdapter {
        FragmentManager fragmentManager;
        ArrayList<TabFragment> mFragments;
        ArrayList<String> mTitles;

        public VPAdapter(FragmentManager supportFragmentManager, ArrayList<TabFragment> tabFragments, ArrayList<String> data) {
            super(supportFragmentManager);
            this.mFragments = tabFragments;
            this.mTitles = data;
            this.fragmentManager = supportFragmentManager;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
