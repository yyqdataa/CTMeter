package com.yanyuqi.ctmeter.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.fragment.AutoTestFragment;
import com.yanyuqi.ctmeter.fragment.ReferenceFragment;
import com.yanyuqi.ctmeter.fragment.ResultFragment;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout dotsLinearLayout;
    public VerticalTabLayout tabLayout;
    private Thread dotsThread;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    for (int k = 0; k < dotsLinearLayout.getChildCount(); k++) {
                        dotsLinearLayout.getChildAt(k).setSelected(false);
                    }
                    dotsLinearLayout.getChildAt(msg.arg1).setSelected(true);
                    break;
            }
        }
    };
    private ArrayList<Fragment> fragments=new ArrayList<>();
    private String[] titles = {"参数", "结果", "自测"};
    private ReferenceFragment referenceFragment;
    private ResultFragment resultFragment;
    private AutoTestFragment autoTestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        initView();
        startDotsThread();
        setData();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public ReferenceFragment getReferenceFragment() {
        return referenceFragment;
    }

    public void setReferenceFragment(ReferenceFragment referenceFragment) {
        this.referenceFragment = referenceFragment;
    }

    public ResultFragment getResultFragment() {
        return resultFragment;
    }

    public void setResultFragment(ResultFragment resultFragment) {
        this.resultFragment = resultFragment;
    }

    public AutoTestFragment getAutoTestFragment() {
        return autoTestFragment;
    }

    public void setAutoTestFragment(AutoTestFragment autoTestFragment) {
        this.autoTestFragment = autoTestFragment;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }

    private void setData() {
        referenceFragment = ReferenceFragment.newInstance();
        resultFragment = ResultFragment.newInstance();
        autoTestFragment = AutoTestFragment.newInstance();
        fragments.add(referenceFragment);
        fragments.add(resultFragment);
        fragments.add(autoTestFragment);
        tabLayout.setupWithFragment(getSupportFragmentManager(), R.id.main_fragment, fragments, new TabAdapter() {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public TabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public TabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public TabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder().setContent(titles[position]).setTextSize(25).build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });
    }

    private void startDotsThread() {
        dotsThread = new Thread(dotsRunnable);
        if (dotsThread != null) {
            dotsThread.start();
        }

    }

    private void initView() {
        dotsLinearLayout = (LinearLayout) findViewById(R.id.main_dots_ll);
        tabLayout = (VerticalTabLayout) findViewById(R.id.tab_layout);
        dotsLinearLayout.getChildAt(0).setSelected(true);
    }

    //测试点自动变化线程
    private Runnable dotsRunnable = new Runnable() {
        @Override
        public void run() {
            if (!Thread.interrupted()) {
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    int j = i % 3;
                    Message message = handler.obtainMessage();
                    message.arg1 = j;
                    message.what = 0;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dotsThread != null) {
            dotsThread.interrupt();
            dotsThread = null;
        }
    }
}
