package com.yanyuqi.ctmeter.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.adapter.GuideViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuidActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private static final int[] imgIds = {R.drawable.splash_one_img, R.drawable.splash_two_img, R.drawable.splash_three_img, R.drawable.splash_four_img};
    private List<ImageView> data = new ArrayList<>();
    private GuideViewPagerAdapter adapter;
    private Button button;
    private float MIN_SCALE = 0.75f;
    private LinearLayout dotLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        initView();
        initData();
        setAdapter();
    }

    private void setAdapter() {
        adapter = new GuideViewPagerAdapter(data);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                button.setVisibility(View.INVISIBLE);
                if (position==(data.size()-1)){
                    button.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < imgIds.length; i++) {
                    dotLinearLayout.getChildAt(i).setSelected(false);
                }
                dotLinearLayout.getChildAt(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);

                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    page.setAlpha(1);
                    page.setTranslationX(0);
                    page.setScaleX(1);
                    page.setScaleY(1);

                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    page.setAlpha(1 - position);

                    // Counteract the default slide transition
                    page.setTranslationX(pageWidth * -position);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);
                }
            }
        });
    }


    private void initData() {
        for (int imgId: imgIds
        ){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imgId);
            data.add(imageView);
        }

        for (int i = 0;i<imgIds.length;i++){
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.dot, null);
            view.setSelected(false);
            dotLinearLayout.addView(view);
        }
        dotLinearLayout.getChildAt(0).setSelected(true);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.guide_vp);
        button = (Button) findViewById(R.id.guid_btn);
        dotLinearLayout = (LinearLayout) findViewById(R.id.guide_dot_ll);
    }

    public void click(View view) {
        Intent intent = new Intent();
        intent.setClass(GuidActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
