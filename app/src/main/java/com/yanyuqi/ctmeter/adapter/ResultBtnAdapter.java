package com.yanyuqi.ctmeter.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.bean.LCQXBean;
import com.yanyuqi.ctmeter.fragment.BZCBFragment;
import com.yanyuqi.ctmeter.fragment.LCQXFragment;
import com.yanyuqi.ctmeter.fragment.LCSJFragment;
import com.yanyuqi.ctmeter.fragment.ResultFragment;
import com.yanyuqi.ctmeter.fragment.WCQXFragment;
import com.yanyuqi.ctmeter.fragment.WCSJFragment;
import com.yanyuqi.ctmeter.fragment.XWCBFragment;

import java.util.List;

/**
 * Created by yanyuqi on 2017/2/17.
 */

public class ResultBtnAdapter extends BaseAdapter {

    private final LCQXFragment lcqxFragment;
    private final WCQXFragment wcqxFragment;
    private final LCSJFragment lcsjFragment;
    private final WCSJFragment wcsjFragment;
    private final BZCBFragment bzcbFragment;
    private final XWCBFragment xwcbFragment;
    private List<String> data;
    private LayoutInflater inflater;
    private ResultFragment resultFragment;
    private List<LCQXBean> resultData;
    private FragmentManager manager;
    private List<Fragment> fragments;
    private Context context;

    public ResultBtnAdapter(List<Fragment> fragments, ResultFragment resultFragment, List<LCQXBean> resultData, List<String> data, Context context, FragmentManager manager) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.resultData = resultData;
        this.resultFragment = resultFragment;
        this.manager = manager;
        this.context = context;
        this.fragments = fragments;
        lcqxFragment = LCQXFragment.newInstance(fragments, resultFragment, resultData);
        wcqxFragment = WCQXFragment.newInstance(fragments, resultFragment, resultData);
        lcsjFragment = LCSJFragment.newInstance(fragments,resultFragment,resultData);
        wcsjFragment = WCSJFragment.newInstance(fragments,resultFragment,resultData);
        bzcbFragment = BZCBFragment.newInstance(fragments,resultFragment,resultData);
        xwcbFragment = XWCBFragment.newInstance(fragments,resultFragment,resultData);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? 0 : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data == null ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_btn, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.button.setText(data.get(position));
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCount() == 2) {
                    switch (position) {
                        case 0:
                            showLCData();
                            break;
                        case 1:
                            showLCGraphs();
                            break;
                    }
                } else if (getCount() == 4) {
                    switch (position) {
                        case 0:
                            showWCData();
                            break;
                        case 1:
                            showWCGraphs();
                            break;
                        case 2:
                            showLCData();
                            break;
                        case 3:
                            showLCGraphs();
                            break;
                    }
                } else if (getCount() == 6) {
                    switch (position) {
                        case 0:
                            showXWCB();
                            break;
                        case 1:
                            showBZCB();
                            break;
                        case 2:
                            showWCData();
                            break;
                        case 3:
                            showWCGraphs();
                            break;
                        case 4:
                            showLCData();
                            break;
                        case 5:
                            showLCGraphs();
                            break;
                    }
                }
            }
        });
        return convertView;
    }

    private void showXWCB() {
        MainActivity activity = (MainActivity) context;
        fragments.set(1, xwcbFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());
    }

    /**
     * 比值差表
     */
    private void showBZCB() {
        MainActivity activity = (MainActivity) context;
        fragments.set(1, bzcbFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());
    }

    /**
     * 误差数据
     */
    private void showWCData() {
        MainActivity activity = (MainActivity) context;
        fragments.set(1, wcsjFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());
    }

    /**
     * 励磁数据
     */
    private void showLCData() {
        MainActivity activity = (MainActivity) context;
        fragments.set(1, lcsjFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());
    }

    /**
     * 误差曲线
     */
    private void showWCGraphs() {
        MainActivity activity = (MainActivity) context;
        fragments.set(1, wcqxFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());
    }

    /**
     * 励磁曲线
     */
    private void showLCGraphs() {
//        FragmentTransaction transaction = manager.beginTransaction();
//        List<Fragment> fragments = manager.getFragments();
//        if (!fragments.contains(lcqxFragment)) {
//            transaction.add(R.id.main_fragment, lcqxFragment);
//        }
//        transaction.hide(resultFragment);
//        transaction.show(lcqxFragment);
//        transaction.commit();
        MainActivity activity = (MainActivity) context;
        fragments.set(1, lcqxFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, resultFragment.getClass().getName());

    }

    static class ViewHolder {
        Button button;

        public ViewHolder(View view) {
            button = (Button) view.findViewById(R.id.result_btn);
            view.setTag(this);
        }
    }
}
