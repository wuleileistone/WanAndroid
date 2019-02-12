package com.wulei.demo.application.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wulei.demo.application.R;
import com.wulei.demo.application.adapter.LeftAdapter;
import com.wulei.demo.application.adapter.RightAdapter;
import com.wulei.demo.application.been.HotBean;
import com.wulei.demo.application.ui.presenter.HotPresenter;
import com.wulei.demo.application.ui.presenter.HotPresenterIpm;
import com.wulei.demo.application.ui.presenter.HotView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wulei on 2019/1/31.
 */
public class HotFragment extends BaseFragment implements HotView {

    private static HotFragment hotFragment = new HotFragment();
    @BindView(R.id.recyclervie_left)
    RecyclerView recyclervieLeft;
    @BindView(R.id.recyclervie_right)
    RecyclerView recyclervieRight;
    Unbinder unbinder;
    private HotPresenter hotPresenter;
    private LeftAdapter leftAdapter;


    public static HotFragment getHotFragment() {
        return hotFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.hot_fragment;
    }

    @Override
    protected void initData() {
        String url = "http://www.wanandroid.com/navi/json";
        hotPresenter.getHotData(this, url);

    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this, getView());
        hotPresenter = new HotPresenterIpm();
        recyclervieLeft.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclervieRight.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void getHotDta(String msg, HotBean hotBean) {
        List<HotBean.DataBean> data = hotBean.getData();
        ArrayList<Boolean> booleans = new ArrayList<>();
        booleans.add(true);

        for (int i = 1; i < data.size(); i++) {
            booleans.add(false);
        }

        leftAdapter = new LeftAdapter(R.layout.item_text);
        recyclervieLeft.setAdapter(leftAdapter);
        leftAdapter.setNewData(data);
        leftAdapter.setSelect(booleans);
        leftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setSelected(adapter, position);
                RightAdapter rightAdapter = new RightAdapter(R.layout.item_tixi);
                recyclervieRight.setAdapter(rightAdapter);
                if (data!=null){
                    rightAdapter.setNewData(data);
                }
            }
        });
    }

    private void setSelected(BaseQuickAdapter adapter, int position) {
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (i==position){
                leftAdapter.getSelected().set(i,true);
            }else {
                leftAdapter.getSelected().set(i,false);
            }
            leftAdapter.notifyDataSetChanged();
        }
    }
}
