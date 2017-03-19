package com.example.myscreenoriention;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;

public class MainActivity extends AppCompatActivity {

    private SreenOrientationListener sreenOrientationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        sreenOrientationListener = new SreenOrientationListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        Log.i("yyqdata","onCreate");
    }


    @Override
    protected void onPause() {
        super.onPause();
//        sreenOrientationListener.disable();
        Log.i("yyqdata","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        sreenOrientationListener.enable();
        Log.i("yyqdata","onResume");

    }

    class SreenOrientationListener extends OrientationEventListener {

        private AppCompatActivity activity;
        public SreenOrientationListener(Context context) {
            super(context);
            activity = (AppCompatActivity) context;
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                return; // 手机平放时，检测不到有效的角度
            }
            // 只检测是否有四个角度的改变
            if (orientation > 350 || orientation < 10) {
                // 0度：手机默认竖屏状态（home键在正下方）
                orientation = 0;
            } else if (orientation > 80 && orientation < 100) {
                // 90度：手机顺时针旋转90度横屏（home建在左侧）
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            } else if (orientation > 170 && orientation < 190) {
                // 手机顺时针旋转180度竖屏（home键在上方）
                orientation = 180;
            } else if (orientation > 260 && orientation < 280) {
                // 手机顺时针旋转270度横屏，（home键在右侧）
//                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

            }
        }
    }
}
