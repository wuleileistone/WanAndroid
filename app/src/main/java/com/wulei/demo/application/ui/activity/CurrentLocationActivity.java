package com.wulei.demo.application.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.MapView;
import com.wulei.demo.application.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrentLocationActivity extends AppCompatActivity {

    @BindView(R.id.currentview)
    MapView currentview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        ButterKnife.bind(this);
    }




}
