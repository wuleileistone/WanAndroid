package com.wulei.demo.application.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wulei.demo.application.R;
import com.wulei.demo.application.been.HotBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wulei on 2019/2/1.
 */
public class LeftAdapter extends BaseQuickAdapter<HotBean.DataBean, BaseViewHolder> {

    private ArrayList<Boolean> selected = new ArrayList<>();

    public ArrayList<Boolean> getSelected() {
        return selected;
    }

    public void setSelected(ArrayList<Boolean> selected) {
        this.selected = selected;
    }

    public LeftAdapter(int layoutResId) {
        super(layoutResId);

    }

    public LeftAdapter(int layoutResId, @Nullable List<HotBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HotBean.DataBean item) {
        holder.setText(R.id.tv_title, item.getName());
        holder.getView(R.id.view).setSelected(selected.get(holder.getLayoutPosition()));
    }

    public void setSelect(ArrayList<Boolean> booleans) {
        this.selected = booleans;
        notifyDataSetChanged();
    }
}
