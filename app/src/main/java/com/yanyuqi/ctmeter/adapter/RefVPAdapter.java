package com.yanyuqi.ctmeter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by yanyuqi on 2017/2/17.
 */

public class RefVPAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;
    public RefVPAdapter(FragmentManager fm,List fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }



    @Override
    public int getCount() {
        return fragments.size();
    }
}
