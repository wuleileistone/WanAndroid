package com.wulei.demo.application.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.wulei.demo.application.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wulei on 2019/2/4.
 */
public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    @BindView(R.id.web_contain)
    LinearLayout webContain;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.web_view_activity);
        ButterKnife.bind(this);

        String link = getIntent().getStringExtra("link");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContain, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(link);
        Log.i(TAG, "onCreate: " + link);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();

    }
}
