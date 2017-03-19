package com.yyq.mypoidemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //模板地址
    public static final String demoPath = "/mnt/sdcard/doc/GLZJ.doc";

    public static final String newPath = "/mnt/sdcard/doc/GLZJS.doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v) {
        InputStream is = getClassLoader().getResourceAsStream("assets/GLZJ.doc");
        writeFile(new File(demoPath), is);//写入模板
        doScan();//扫描模板生成新文件
    }

    private void doScan() {
        File demoFile = new File(demoPath);
        File newFile = new File(newPath);
        Map<String,String> map = new HashMap<>();
        map.put("$MX1$","#1母线");
        map.put("$MX2$","#12母线");
        map.put("$1P1$","5");
        map.put("$2P1$","3");
        map.put("$1Q1$","7");
        map.put("$2Q1$","9");
        map.put("$ZB1$","#1主变");

        writeDoc(demoFile,newFile,map);
        openDoc();
    }

    private void openDoc() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        String type = "application/msword";
        intent.setDataAndType(Uri.fromFile(new File(newPath)),type);
        MainActivity.this.startActivity(intent);
    }

    /**
     * 写入新文件
     * @param demoFile
     * @param newFile
     * @param map
     */
    private void writeDoc(File demoFile, File newFile, Map<String, String> map) {
        InputStream in = null;
        HWPFDocument hpt = null;
        OutputStream os = null;
        try {
            in = new FileInputStream(demoFile);
            POIFSFileSystem pos = new POIFSFileSystem(in);
            hpt = new HWPFDocument(pos);
            Range range = hpt.getRange();
            for (Map.Entry<String, String> entry:map.entrySet()){
                range.replaceText(entry.getKey(),entry.getValue());
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            os = new FileOutputStream(newFile);
            Log.i("yyqdata",hpt.getText().toString());
            hpt.write(newFile);
//            os.write(bos.toByteArray());
//            pos.writeFilesystem(os);
            os.close();
            bos.close();
            in.close();
            pos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建并写入模板文件
     *
     * @param file
     * @param is
     */
    private void writeFile(File file, InputStream is) {


        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }

        if (file.exists()) {
//            return;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            OutputStream os = new FileOutputStream(file, false);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
