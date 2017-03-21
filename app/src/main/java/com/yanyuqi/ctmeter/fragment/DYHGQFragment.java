package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DYHGQFragment extends Fragment {


    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private ResultFragment resultFragment;
    private static final int NO_BTN = 0;
    private static final int TWO_BTN = 2;
    private int currentBtnNum = 0;
    private LinearLayout edyc_ll;
    private LinearLayout edec_ll;
    private LinearLayout edpl_ll;
    private LinearLayout zdcsdy_ll;
    private LinearLayout ll_edec;
    private LinearLayout ll_edyc;
    private LinearLayout ll_zdcsdl;
    private LinearLayout ll_zdcsdy;

    public DYHGQFragment() {
        // Required empty public constructor
    }

    public static DYHGQFragment newInstance() {

        Bundle args = new Bundle();

        DYHGQFragment fragment = new DYHGQFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dyhgq, container, false);
        initView(view);
        initListener();
        return view;
    }


    public int getCurrentBtnNum() {
        return currentBtnNum;
    }

    public void setCurrentBtnNum(int currentBtnNum) {
        this.currentBtnNum = currentBtnNum;
    }

    private void initListener() {

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox1.setClickable(true);
                    checkBox3.setClickable(false);
                    resultFragment.changeRightBtn(NO_BTN);
                    currentBtnNum = NO_BTN;
                    updataView(3);
                }
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setClickable(false);
                    checkBox3.setClickable(true);
                    checkBox3.setChecked(false);
                    resultFragment.changeRightBtn(NO_BTN);
                    currentBtnNum = NO_BTN;
                    updataView(1);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(true);
                    checkBox1.setClickable(false);
                    checkBox3.setChecked(false);
                    checkBox3.setClickable(true);
                    resultFragment.changeRightBtn(TWO_BTN);
                    currentBtnNum = TWO_BTN;
                    updataView(2);
                } else {
                    updataView(1);
                }
            }
        });
    }

    private void updataView(int i) {
        switch (i) {
            case 1:
                edyc_ll.setVisibility(View.INVISIBLE);
                edec_ll.setVisibility(View.INVISIBLE);
                edpl_ll.setVisibility(View.INVISIBLE);
                zdcsdy_ll.setVisibility(View.INVISIBLE);
                break;
            case 2:
                edyc_ll.setVisibility(View.VISIBLE);
                edec_ll.setVisibility(View.VISIBLE);
                edpl_ll.setVisibility(View.VISIBLE);
                zdcsdy_ll.setVisibility(View.VISIBLE);
                edyc_ll.setVisibility(View.INVISIBLE);
                ll_edec.setVisibility(View.GONE);
                ll_edyc.setVisibility(View.VISIBLE);
                ll_zdcsdl.setVisibility(View.VISIBLE);
                ll_zdcsdy.setVisibility(View.GONE);
                break;
            case 3:
                edyc_ll.setVisibility(View.VISIBLE);
                edec_ll.setVisibility(View.VISIBLE);
                edpl_ll.setVisibility(View.VISIBLE);
                zdcsdy_ll.setVisibility(View.VISIBLE);
                ll_edec.setVisibility(View.VISIBLE);
                ll_edyc.setVisibility(View.GONE);
                ll_zdcsdl.setVisibility(View.GONE);
                ll_zdcsdy.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void initView(View view) {
        checkBox1 = (CheckBox) view.findViewById(R.id.check_tv1);
        checkBox2 = (CheckBox) view.findViewById(R.id.check_tv2);
        checkBox3 = (CheckBox) view.findViewById(R.id.check_tv3);
        checkBox1.setClickable(false);
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<Fragment> fragments = activity.getFragments();
        resultFragment = (ResultFragment) fragments.get(1);
        edyc_ll = (LinearLayout) view.findViewById(R.id.edyc_ll);
        edec_ll = (LinearLayout) view.findViewById(R.id.edec_ll);
        edpl_ll = (LinearLayout) view.findViewById(R.id.edpl_ll);
        zdcsdy_ll = (LinearLayout) view.findViewById(R.id.zdcsdy_ll);
        ll_edec = (LinearLayout) view.findViewById(R.id.ll_edec);
        ll_edyc = (LinearLayout) view.findViewById(R.id.ll_edyc);
        ll_zdcsdl = (LinearLayout) view.findViewById(R.id.ll_zdcsdl);
        ll_zdcsdy = (LinearLayout) view.findViewById(R.id.ll_zdcsdy);
    }

}
