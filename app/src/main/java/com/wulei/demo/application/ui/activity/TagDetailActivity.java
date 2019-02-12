package com.wulei.demo.application.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wulei.demo.application.R;
import com.wulei.demo.application.been.TagDetailBean;
import com.wulei.demo.application.been.TiXiBean;
import com.wulei.demo.application.utils.OKGO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TagDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.recyclerview_tag_detail)
    RecyclerView recyclerviewTagDetail;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    private TiXiBean.DataBean.ChildrenBean childbean;
    private int cid;
    private int pageNum = 0;
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_detail);
        ButterKnife.bind(this);

        childbean = (TiXiBean.DataBean.ChildrenBean) getIntent().getSerializableExtra("childbean");
        cid = childbean.getId();

        initView();
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerviewTagDetail.setLayoutManager(linearLayoutManager);

        smartrefreshlayout.autoRefresh();
        smartrefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                refresh(refreshLayout, false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refresh(refreshLayout, true);
            }
        });
    }

    private void refresh(RefreshLayout refreshLayout, boolean b) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (b) {
                    getData();
                } else {
                    loadMore();
                }
            }
        }, 2000);
    }

    private void loadMore() {
        pageNum++;
        String url = "http://www.wanandroid.com/article/list/" + pageNum + "/json?cid=" + cid;
        OKGO.get(url, "tagdetail", new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                TagDetailBean tagDetailBean = JSONObject.parseObject(response.body(), TagDetailBean.class);
                if (tagDetailBean != null) {
                    if (tagDetailBean.getData().getDatas().size() > 0) {
                        List<TagDetailBean.DataBean.DatasBean> datas = tagDetailBean.getData().getDatas();
                        adapter.addData(datas);
                    } else {
                        View footer = View.inflate(TagDetailActivity.this, R.layout.tixi_footer, null);
                        if (adapter.getFooterLayout() == null) {
                            adapter.addFooterView(footer);
                            smartrefreshlayout.setEnableLoadMore(false);
                        }
                    }
                    smartrefreshlayout.finishLoadMore();
                }
            } @Override
            public void onFinish() {
                super.onFinish();

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

            }


        });
    }

    private void getData() {
        pageNum = 0;
        String url = "http://www.wanandroid.com/article/list/" + pageNum + "/json?cid=" + cid;
        OKGO.get(url, "tagdetail", new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                TagDetailBean tagDetailBean = JSONObject.parseObject(response.body(), TagDetailBean.class);
                List<TagDetailBean.DataBean.DatasBean> datas = tagDetailBean.getData().getDatas();
                adapter = new DetailAdapter(R.layout.item_home, datas);
                recyclerviewTagDetail.setAdapter(adapter);
                smartrefreshlayout.setEnableLoadMore(true);
                smartrefreshlayout.finishRefresh();
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

            private class DetailAdapter extends BaseQuickAdapter<TagDetailBean.DataBean.DatasBean, BaseViewHolder> {

                public DetailAdapter(int layoutResId, @Nullable List<TagDetailBean.DataBean.DatasBean> data) {
                    super(layoutResId, data);
                }

                @Override
                protected void convert(BaseViewHolder holder, TagDetailBean.DataBean.DatasBean item) {
                    holder.setText(R.id.tv_item_title, item.getTitle().trim());
                    holder.setText(R.id.tv_itemt_desc, item.getDesc());
                    holder.setText(R.id.tv_item_author_name, item.getAuthor());
                    holder.setText(R.id.tv_item_time_, item.getNiceDate());
                    holder.setText(R.id.tv_chaptername, item.getChapterName());

                    holder.getView(R.id.rl_rootview).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("link", item.getLink());
                            startActivity(intent);
                        }
                    });


                }
            }

}
