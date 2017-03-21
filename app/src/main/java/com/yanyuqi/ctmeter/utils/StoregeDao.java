package com.yanyuqi.ctmeter.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanyuqi on 2017/3/21.
 */

public class StoregeDao {

    private static String DIR = "CTPMeter";

    /**
     * 判断sd卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExsit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 写入sd文件
     *
     * @param fileName
     * @param content
     * @return
     */
    public static File writeSDFile(String fileName, String content) {
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir.toString() + File.separator + DIR + File.separator + fileName);
        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(content);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取应用目录下的file列表
     *
     * @return
     */
    public static List<File> getSDFiles() {
        List<File> list = new ArrayList<>();
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir.toString() + File.separator + DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }
        return list;
    }

    public static List<File> getSDFiles(String testDir) {
        List<File> list = new ArrayList<>();
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir.toString() + File.separator + DIR + File.separator + testDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            list.add(files[i]);
        }
        return list;
    }

    /**
     * 读取sd卡中的文件
     *
     * @param fileName
     * @return
     */
    public static String readSDFile(String fileName) {
        String content = "";
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir.toString() + File.separator + DIR + File.separator + fileName);
        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            return null;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = "";
            while ((str = br.readLine()) != null) {
                content += str;
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 删除文件
     *
     * @param fileName
     * @return
     */
    public static boolean deleteSDFile(String fileName) {
        File sdDir = Environment.getExternalStorageDirectory();
        File file = new File(sdDir.toString() + File.separator + DIR + File.separator + fileName);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

}
