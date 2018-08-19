package com.yc.cn.ycnotification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 直接弹出弹窗弹出弹窗 留下主界面痕迹
 */
public class NoTraceDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }
}
