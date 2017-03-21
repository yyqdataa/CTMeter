package com.yanyuqi.ctmeter.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 监测和控制wifi状态
 * Created by yanyuqi on 2017/3/21.
 */

public class WifiAdmin {

    private final WifiManager mWifiManager;
    private final WifiInfo mWifiInfo;

    public WifiAdmin(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    /**
     * 打开网卡
     */
    public void openNetCard() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭网卡
     */
    public void closeNetCard() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 是否打开wifi
     * @return
     */
    public boolean isWifiOpen() {
        int wifiState = mWifiManager.getWifiState();
        if (wifiState == 2 || wifiState == 3) {
            return true;
        }
        return false;
    }

    /**
     * 获得IP地址
     * @return
     */
    public int getIPAddress(){
        return (mWifiInfo==null)?0:mWifiInfo.getIpAddress();
    }
}
