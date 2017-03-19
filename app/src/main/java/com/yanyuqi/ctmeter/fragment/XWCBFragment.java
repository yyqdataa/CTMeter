package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.adapter.BZCBAdapter;
import com.yanyuqi.ctmeter.bean.LCQXBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class XWCBFragment extends Fragment {

    private static List<Fragment> fragments;
    private static ResultFragment resultFragment;
    private static List<LCQXBean> resultData;
    private ListView dataListView;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private TextView tv_four;
    private TextView tv_five;
    private String temp = "1_4.177_3.844_3.509_3.172_2.969_5_3.993_3.714_3.425_3.126_2.942_10_3.671_3.481_3.267_3.030_2.877_20_2.756_2.801_2.786_2.714_2.645_50_2.155_2.148_2.051_1.947_1.901_100_1.644_1.638_1.621_1.598_1.585_120_1.513_1.511_1.506_1.512_1.498_";
    private String[] tvOne = {"15.00", "11.25", "7.50", "3.75", "1.50"};
    private String[] tvTwo = {"/0.80", "/0.80", "/0.80", "/0.80", "/0.80"};
    private List<LCQXBean> list = new ArrayList<>();
    private Button backBtn;
    private String[] str = new String[6 * 7];

    public XWCBFragment() {
        // Required empty public constructor
    }

    public static XWCBFragment newInstance(List<Fragment> fragments, ResultFragment resultFragment, List<LCQXBean> resultData) {

        Bundle args = new Bundle();

        XWCBFragment fragment = new XWCBFragment();
        fragment.setArguments(args);
        XWCBFragment.fragments = fragments;
        XWCBFragment.resultFragment = resultFragment;
        XWCBFragment.resultData = resultData;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xwcb, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }
    private void initListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResultFrag();
            }
        });
    }

    private void showResultFrag() {
        MainActivity activity = (MainActivity) getActivity();
        fragments.set(1, resultFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, this.getClass().getName());
    }

    private void initData() {
        tv_one.setText(tvOne[0] + "\n" + tvTwo[0]);
        tv_two.setText(tvOne[1] + "\n" + tvTwo[1]);
        tv_three.setText(tvOne[2] + "\n" + tvTwo[2]);
        tv_four.setText(tvOne[3] + "\n" + tvTwo[3]);
        tv_five.setText(tvOne[4] + "\n" + tvTwo[4]);
        createBean();
    }

    private void createBean() {
        int n = 0;
        while (temp.length() > 0) {
            int pos = 0;
            for (int i = 0; i < 6; i++) {
                pos = temp.indexOf("_");
                str[i + 6 * n] = temp.substring(0, pos);
                temp = temp.substring(pos + 1);
            }
            LCQXBean lcqxBean = new LCQXBean(str[0 + 6 * n], Float.parseFloat(str[1 + 6 * n]), Float.parseFloat(str[2 + 6 * n]), Float.parseFloat(str[3 + 6 * n]), Float.parseFloat(str[4 + 6 * n]), Float.parseFloat(str[5 + 6 * n]), 0.0f);
            list.add(lcqxBean);
            n++;
        }
        BZCBAdapter bzcbAdapter = new BZCBAdapter(getContext(), list);
        dataListView.setAdapter(bzcbAdapter);
    }

    private void initView(View view) {
        dataListView = (ListView) view.findViewById(R.id.data_lv);
        tv_one = (TextView) view.findViewById(R.id.tv_one);
        tv_two = (TextView) view.findViewById(R.id.tv_two);
        tv_three = (TextView) view.findViewById(R.id.tv_three);
        tv_four = (TextView) view.findViewById(R.id.tv_four);
        tv_five = (TextView) view.findViewById(R.id.tv_five);
        backBtn = (Button) view.findViewById(R.id.btn_back);
    }

}
