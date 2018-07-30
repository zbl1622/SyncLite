package com.zblcloud.synclite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zblcloud.synclite.support.util.DeviceIDUtil;

public class MainActivity extends AppCompatActivity {

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_content = findViewById(R.id.tv_content);
        tv_content.setText(DeviceIDUtil.getDeviceID());
    }
}
