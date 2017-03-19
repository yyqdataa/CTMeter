package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.bean.LCQXBean;
import com.yanyuqi.ctmeter.widget.Graphs;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WCQXFragment extends Fragment {


    private static ResultFragment resultFragment;
    private static List<Fragment> fragments;
    private static List<LCQXBean> resultData;
    private Graphs wxqxGraphs5;
    private Graphs wxqxGraphs10;
    private Button backBtn;
    private Button changeBtn;

    public WCQXFragment() {
        // Required empty public constructor
    }

    public static WCQXFragment newInstance(List<Fragment> fragments, ResultFragment resultFragment,List<LCQXBean> resultData) {

        Bundle args = new Bundle();

        WCQXFragment fragment = new WCQXFragment();
        fragment.setArguments(args);
        WCQXFragment.resultFragment = resultFragment;
        WCQXFragment.fragments = fragments;
        WCQXFragment.resultData = resultData;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wcqx, container, false);
        initView(view);
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
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = changeBtn.getText().toString();
                if ("5%曲线".equals(text)){
                    changeBtn.setText("10%曲线");
                    wxqxGraphs5.setVisibility(View.VISIBLE);
                    wxqxGraphs10.setVisibility(View.GONE);
                }else if ("10%曲线".equals(text)){
                    changeBtn.setText("5%曲线");
                    wxqxGraphs10.setVisibility(View.VISIBLE);
                    wxqxGraphs5.setVisibility(View.GONE);
                }
            }
        });
    }

    private void showResultFrag() {
        MainActivity activity = (MainActivity) getActivity();
        fragments.set(1,resultFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments,this.getClass().getName());
    }

    private void initView(View view) {
        wxqxGraphs5 = (Graphs) view.findViewById(R.id.lcqx_graphs_five);
        wxqxGraphs10 = (Graphs) view.findViewById(R.id.lcqx_graphs_ten);
        backBtn = (Button) view.findViewById(R.id.btn_back);
        changeBtn = (Button) view.findViewById(R.id.btn_change);
    }

}
