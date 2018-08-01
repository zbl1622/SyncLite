package com.zblcloud.synclite.support.network;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zblcloud.synclite.support.util.ZLog;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 局域网服务器发现
 */
public class LANExplorer {
    private static final String TAG = "LANExplorer";
    //"synclite"
    private byte[] keyArray = new byte[]{(byte) 115, (byte) 121, (byte) 110, (byte) 99, (byte) 108, (byte) 105, (byte) 116, (byte) 101};
    private String keyString = new String(keyArray);
    private static final String GROUP_IP = "224.0.0.1";

    //发送组播socket
    private MulticastSocket multicastSocket;

    private int socketTimeout = 1500;

    public interface SearchGatewayCallback {
        void find(SyncServerBean bean);

        void result(List<SyncServerBean> list);
    }

    public LANExplorer() {
        try {
            multicastSocket = new MulticastSocket(7302);//客户端组播接收端口
            multicastSocket.setSoTimeout(socketTimeout);
            InetAddress group = InetAddress.getByName(GROUP_IP);
            multicastSocket.joinGroup(group);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        try {
            multicastSocket.setSoTimeout(socketTimeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void startSearch(@NonNull final SearchGatewayCallback callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, SyncServerBean> serverMap = new HashMap();
                try {
                    //发送组播数据
                    byte[] buf = new byte[1024];
                    InetAddress group = InetAddress.getByName(GROUP_IP);
                    DatagramPacket sendDP = new DatagramPacket(keyArray, keyArray.length, group, 7301);//服务端组播接收端口
                    multicastSocket.send(sendDP);

                    //接收监听
                    while (true) {
                        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                        multicastSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态

                        byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
                        System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
                        String msg = new String(message).trim();
                        ZLog.i(TAG, datagramPacket.getAddress().toString());
                        ZLog.i(TAG, msg);
                        try {
                            JSONObject msgJson = new JSONObject(msg);
                            SyncServerBean bean = new SyncServerBean();
                            bean.host = datagramPacket.getAddress().toString().replace("/", "");
                            bean.key = msgJson.optString("key");
                            bean.name = msgJson.optString("name");
                            bean.id = msgJson.optString("id");
                            if (TextUtils.equals(keyString, bean.key)) {
                                if (serverMap.get(bean.id) == null) {
                                    callback.find(bean);
                                }
                                serverMap.put(bean.id, bean);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    ArrayList<SyncServerBean> list = new ArrayList<>();
                    list.addAll(serverMap.values());
                    serverMap.clear();
                    callback.result(list);
                }
            }
        }).start();
    }

    public void destroy() {
        if (multicastSocket != null) {
            multicastSocket.close();
        }
    }

}
