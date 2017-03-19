package com.yanyuqi.ctmeter.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.adapter.RefVPAdapter;
import com.yanyuqi.ctmeter.widget.ScrollChangeIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferenceFragment extends Fragment implements ViewPager.OnPageChangeListener {


    private ScrollChangeIndicator indicator;
    private ViewPager viewPager;
    private List fragments = new ArrayList();
    private ResultFragment resultFragment;
    private static final String[] types = {"电流互感器", "电压互感器"};
    private DLHGQFragment dlhgqFragment;
    private DYHGQFragment dyhgqFragment;

    public ReferenceFragment() {
        // Required empty public constructor
    }

    public static ReferenceFragment newInstance() {

        Bundle args = new Bundle();

        ReferenceFragment fragment = new ReferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reference, container, false);
        initView(view);
        initData();
        initdapter();
        return view;
    }

    private void initdapter() {
        RefVPAdapter refVPAdapter = new RefVPAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(refVPAdapter);
        viewPager.setOffscreenPageLimit(2);
        indicator.setViewPager(viewPager);
        indicator.setTextLeft(types[0]);
        indicator.setTextRight(types[1]);
        indicator.setAngel(30);
        indicator.setLeftBackColor(Color.parseColor("#FF026029"));
        indicator.setRightBackColor(Color.parseColor("#ffffff"));
        indicator.setLeftColorValue(Color.parseColor("#FF4081"));
        indicator.setRightColorValue(Color.parseColor("#6c6a6a"));
        indicator.setOnPageChangeListener(this);
    }

    private void initData() {
        dlhgqFragment = DLHGQFragment.newInstance();
        dyhgqFragment = DYHGQFragment.newInstance();
        fragments.add(dlhgqFragment);
        fragments.add(dyhgqFragment);
    }

    private void initView(View view) {
        indicator = (ScrollChangeIndicator) view.findViewById(R.id.scroll_indicator);
        viewPager = (ViewPager) view.findViewById(R.id.ref_vp);
        MainActivity activity = (MainActivity) getActivity();
        resultFragment = activity.getResultFragment();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE) {
            switch (indicator.getCurrentItem()) {
                case 0:
                    if (dlhgqFragment.getCurrentBtnNum() != -1) {
                        resultFragment.changeRightbtn(dlhgqFragment.getCurrentBtnNum());
                    }
                    break;
                case 1:
                    if (dyhgqFragment.getCurrentBtnNum() != -1) {
                        resultFragment.changeRightbtn(dyhgqFragment.getCurrentBtnNum());
                    }
                    break;
            }
        }
    }
}
