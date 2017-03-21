package com.yanyuqi.ctmeter.utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by yanyuqi on 2017/3/21.
 */

public class UDPServer {

    private String host;
    private final int LOCAL_PORT = 8080;
    private DatagramSocket recvSocket;
    private Handler rHandler;
    private Thread recvThread;
    private byte[] recvData = new byte[1024];
    private Runnable recvRun = new Runnable() {
        private DatagramPacket packet;

        @Override
        public void run() {
            int packetLen = 0;
            while (!Thread.interrupted()) {
                try {
                    packet = new DatagramPacket(recvData, recvData.length);
                    recvSocket.receive(packet);
                    packetLen = packet.getLength();
                    String strRecv = new String(packet.getData(), 0, packetLen, "utf-8");
                    Message message = rHandler.obtainMessage();
                    message.obj = strRecv;
                    rHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 开启接收线程
     *
     * @param handler
     * @param ip
     * @return
     */
    public boolean monitorPort(Handler handler, int ip) {
        if (recvSocket == null) {
            if (ip == 0) {
                return false;
            } else {
                host = String.format("%d.%d.%d.%d", ip & 0xff, ip >> 8 & 0xff, ip >> 16 & 0xff, ip >> 24 & 0xff);
                try {
                    InetAddress address = InetAddress.getByName(host);
                    recvSocket = new DatagramSocket(LOCAL_PORT, address);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    return false;
                } catch (SocketException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        rHandler = handler;
        recvThread = new Thread(recvRun);
        recvThread.start();
        return true;
    }

    /**
     * 断开连接
     */
    public void closeSocket() {
        stopRecvData();

        recvSocket.close();
        recvSocket = null;
    }

    private void stopRecvData() {

        if (recvThread != null) {
            recvThread.interrupt();
            recvThread = null;
        }
    }

}
