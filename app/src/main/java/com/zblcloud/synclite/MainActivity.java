package com.zblcloud.synclite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zblcloud.synclite.support.network.LANExplorer;
import com.zblcloud.synclite.support.network.SyncServerBean;
import com.zblcloud.synclite.support.util.DeviceIDUtil;
import com.zblcloud.synclite.support.util.ZLog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_content;
    private Button btn_search_server;
    private LANExplorer explorer = new LANExplorer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_content = findViewById(R.id.tv_content);
        btn_search_server = findViewById(R.id.btn_search_server);
        btn_search_server.setOnClickListener(this);
        initData();
    }

    private void initData() {
        tv_content.setText(DeviceIDUtil.getDeviceID() + "\n");
    }

    @Override
    public void onClick(View view) {
        if (view == btn_search_server) {
            tv_content.append("ServerList:\n");
            explorer.startSearch(new LANExplorer.SearchGatewayCallback() {
                @Override
                public void find(final SyncServerBean bean) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_content.append("server: " + bean.host + ",name:" + bean.name + ",id:" + bean.id + "\n");
                        }
                    });
                }

                @Override
                public void result(final List<SyncServerBean> list) {
                    if (list != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_content.append("ServerList: " + list.size() + " total count.\n");
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        explorer.destroy();
        super.onDestroy();
    }
}
