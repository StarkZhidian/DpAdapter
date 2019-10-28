package com.zhidian.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vane.dpadapter.DisplayMetricsUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetricsUtils.adjustDisplayMetricsDensity(this);
        setContentView(R.layout.activity_main);
    }
}
