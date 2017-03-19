package com.yanyuqi.ctmeter.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;

public class SplashActivity extends AppCompatActivity {

    public static final String COMPANY = "武汉市豪迈电力自动化技术有限责任公司";
    private TextView textVersion;
    private TextView textCompany;
    private Handler handler;
    private boolean isFirst;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            if (isFirst){
                intent.setClass(SplashActivity.this,GuidActivity.class);
                isFirst = false;
                saveConfig();
            }else {
                intent.setClass(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            SplashActivity.this.finish();
        }

    };

    private void saveConfig() {
        SharedPreferences.Editor edit = sharePreferences.edit();
        edit.putBoolean("isFirst",isFirst);
        edit.commit();
    }

    private SharedPreferences sharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        initView();
        check();
        startActiv();
    }

    /**
     * 跳转
     */
    private void startActiv() {
        handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    /**
     * 检查版本号，是否第一次打开app等
     */
    private void check() {
        textCompany.setText("版权所有："+COMPANY);
        textVersion.setText("版本号："+getVersion());
        sharePreferences = getSharedPreferences("config",MODE_PRIVATE);
        isFirst = sharePreferences.getBoolean("isFirst",true);
    }

    /**
     * 获取软件版本号
     * @return  版本号
     */
    private String getVersion() {
        String version = "";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private void initView() {
        textVersion = (TextView) findViewById(R.id.splash_version_tv);
        textCompany = (TextView) findViewById(R.id.splash_company_tv);
    }


}
