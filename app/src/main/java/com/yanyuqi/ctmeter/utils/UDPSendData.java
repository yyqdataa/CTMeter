package com.yanyuqi.ctmeter.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * UDP发送类
 * Created by yanyuqi on 2017/3/21.
 */

public class UDPSendData {

    private Thread sendThread;
    private byte[] sendBuff = new byte[1024];
    private int port;
    private InetAddress serverAddress;
    private DatagramSocket sendSocket = null;
    private DatagramPacket sendPacket = null;
    private Runnable sendRun = new Runnable() {
        @Override
        public void run() {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(port);
                DatagramPacket datagramPacket = new DatagramPacket(sendBuff, sendBuff.length, serverAddress, port);
                datagramSocket.send(datagramPacket);
                sendBuff = null;
                datagramSocket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 设置网络ip和端口
     * @param port
     * @param ip
     * @return
     */
    public boolean SetNetParam(int port, String ip) {
        try {
            serverAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (serverAddress == null) {
            return false;
        }
        this.port = port;
        if (port < 2000 || port > 65535) {
            return false;
        }
        return true;
    }

    /**
     * 开启发送线程
     */
    public void setSendData(){
        sendThread = new Thread(sendRun);
        sendThread.start();
    }
}
