package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DLHGQFragment extends Fragment {


    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private ResultFragment resultFragment;
    private static final int NO_BTN = 0;
    public static final int FOUR_BTN = 4;
    public static final int SIX_BTN = 6;
    private int currentBtnNum = 0;
    private LinearLayout jb_ll;
    private LinearLayout edyc_ll;
    private LinearLayout ll_fh;
    private LinearLayout ll_glys;
    private LinearLayout ll_edecdl;
    private LinearLayout ll_edgl;
    private LinearLayout ll_dqwd;
    private Button jbButton;
    public static final String[] JB_NAME = {"P", "TPY", "计量", "PR", "PX", "TPS", "TPX", "TPZ"};
    private int currentJBNum = 2;
    private LinearLayout ll_edybbaxs;
    private LinearLayout ll_zqxzxs;
    private LinearLayout ll_kssc;
    private LinearLayout ll_gzxh;
    private TextView gzxhButton;
    private LinearLayout ll_jsxs;
    private LinearLayout ll_ztmjxs;
    private LinearLayout ll_t1;
    private LinearLayout ll_tal1;
    private LinearLayout ll_gdds;
    private LinearLayout ll_edual;
    private LinearLayout ll_tp;
    private LinearLayout ll_tfr;
    private LinearLayout ll_t2;
    private LinearLayout ll_ekdyie;
    private LinearLayout ll_scial;
    private LinearLayout ll_ecsjcs;
    private LinearLayout ll_tal2;
    private int gz_statue = 0;

    public DLHGQFragment() {
        // Required empty public constructor
    }

    public static DLHGQFragment newInstance() {

        Bundle args = new Bundle();

        DLHGQFragment fragment = new DLHGQFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dlhgq, container, false);
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
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox4.setClickable(false);
                    checkBox1.setChecked(false);
                    checkBox1.setClickable(true);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    resultFragment.changeRightbtn(NO_BTN);
                    currentBtnNum = NO_BTN;
                    updateView(3);
                    setClassView(2);
                }
            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setClickable(false);
                    checkBox4.setClickable(true);
                    checkBox4.setChecked(false);
                    resultFragment.changeRightbtn(NO_BTN);
                    currentBtnNum = NO_BTN;
                    updateView(1);
                    setClassView(2);
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(true);
                    checkBox1.setClickable(false);
                    checkBox4.setChecked(false);
                    checkBox4.setClickable(true);
                    resultFragment.changeRightbtn(FOUR_BTN);
                    currentBtnNum = FOUR_BTN;
                    updateView(2);
                    setClassView(currentJBNum);
                } else {
                    resultFragment.changeRightbtn(NO_BTN);
                    currentBtnNum = NO_BTN;
                    updateView(1);
                    setClassView(2);
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox1.setChecked(true);
                    checkBox1.setClickable(false);
                    checkBox2.setChecked(true);
                    checkBox2.setClickable(false);
                    checkBox4.setChecked(false);
                    checkBox4.setClickable(true);
                    resultFragment.changeRightbtn(SIX_BTN);
                    currentBtnNum = SIX_BTN;
                    setClassView(currentJBNum);
                } else {
                    checkBox2.setClickable(true);
                    resultFragment.changeRightbtn(FOUR_BTN);
                    currentBtnNum = FOUR_BTN;
                }
                updateView(2);
            }
        });

        jbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentJBNum++;
                int i = currentJBNum % 8;
                jbButton.setText(JB_NAME[i]);
                if (i==1||i==2){
                    resultFragment.changeRightbtn(SIX_BTN);
                    currentBtnNum = SIX_BTN;
                }else {
                    resultFragment.changeRightbtn(FOUR_BTN);
                    currentBtnNum = FOUR_BTN;
                }
                setClassView(i);
            }
        });

        gzxhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = gzxhButton.getText().toString();
                if ("C-t1-O".equals(text)) {
                    gzxhButton.setText("C-t1-O-tfr-C-t2-O");
                    gz_statue = 1;
                    ll_t2.setVisibility(View.VISIBLE);
                    ll_tfr.setVisibility(View.VISIBLE);
                    ll_tal2.setVisibility(View.VISIBLE);
                } else if ("C-t1-O-tfr-C-t2-O".equals(text)) {
                    gzxhButton.setText("C-t1-O");
                    gz_statue = 0;
                    ll_t2.setVisibility(View.INVISIBLE);
                    ll_tfr.setVisibility(View.INVISIBLE);
                    ll_tal2.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setClassView(int currentBtnNum) {
        resultFragment.setResultView(currentBtnNum);
        switch (currentBtnNum) {
            case 0:
                ll_edybbaxs.setVisibility(View.GONE);
                ll_zqxzxs.setVisibility(View.VISIBLE);
                ll_kssc.setVisibility(View.INVISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.INVISIBLE);
                ll_ztmjxs.setVisibility(View.INVISIBLE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.INVISIBLE);
                ll_edual.setVisibility(View.INVISIBLE);
                ll_tp.setVisibility(View.INVISIBLE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.INVISIBLE);
                ll_scial.setVisibility(View.INVISIBLE);
                ll_ecsjcs.setVisibility(View.INVISIBLE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
            case 1:
                ll_edybbaxs.setVisibility(View.INVISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.VISIBLE);
                ll_gzxh.setVisibility(View.VISIBLE);
                ll_jsxs.setVisibility(View.GONE);
                ll_ztmjxs.setVisibility(View.VISIBLE);
                ll_t1.setVisibility(View.VISIBLE);
                ll_tal1.setVisibility(View.VISIBLE);
                ll_gdds.setVisibility(View.GONE);
                ll_edual.setVisibility(View.GONE);
                ll_tp.setVisibility(View.VISIBLE);
                ll_ekdyie.setVisibility(View.GONE);
                ll_scial.setVisibility(View.GONE);
                ll_ecsjcs.setVisibility(View.VISIBLE);
                if (gz_statue == 0) {
                    ll_t2.setVisibility(View.INVISIBLE);
                    ll_tfr.setVisibility(View.INVISIBLE);
                    ll_tal2.setVisibility(View.INVISIBLE);
                } else if (gz_statue == 1) {
                    ll_t2.setVisibility(View.VISIBLE);
                    ll_tfr.setVisibility(View.VISIBLE);
                    ll_tal2.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                ll_edybbaxs.setVisibility(View.VISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.INVISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.INVISIBLE);
                ll_ztmjxs.setVisibility(View.INVISIBLE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.INVISIBLE);
                ll_edual.setVisibility(View.INVISIBLE);
                ll_tp.setVisibility(View.INVISIBLE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.INVISIBLE);
                ll_scial.setVisibility(View.INVISIBLE);
                ll_ecsjcs.setVisibility(View.INVISIBLE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
            case 3:
                ll_edybbaxs.setVisibility(View.GONE);
                ll_zqxzxs.setVisibility(View.VISIBLE);
                ll_kssc.setVisibility(View.INVISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.INVISIBLE);
                ll_ztmjxs.setVisibility(View.INVISIBLE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.INVISIBLE);
                ll_edual.setVisibility(View.INVISIBLE);
                ll_tp.setVisibility(View.INVISIBLE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.GONE);
                ll_scial.setVisibility(View.GONE);
                ll_ecsjcs.setVisibility(View.VISIBLE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
            case 4:
                ll_edybbaxs.setVisibility(View.INVISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.INVISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.VISIBLE);
                ll_ztmjxs.setVisibility(View.GONE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.VISIBLE);
                ll_edual.setVisibility(View.GONE);
                ll_tp.setVisibility(View.GONE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.VISIBLE);
                ll_scial.setVisibility(View.GONE);
                ll_ecsjcs.setVisibility(View.GONE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
            case 5:
                ll_edybbaxs.setVisibility(View.INVISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.VISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.VISIBLE);
                ll_ztmjxs.setVisibility(View.GONE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.GONE);
                ll_edual.setVisibility(View.VISIBLE);
                ll_tp.setVisibility(View.GONE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.GONE);
                ll_scial.setVisibility(View.VISIBLE);
                ll_ecsjcs.setVisibility(View.GONE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
            case 6:
                ll_edybbaxs.setVisibility(View.INVISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.VISIBLE);
                ll_gzxh.setVisibility(View.VISIBLE);
                ll_jsxs.setVisibility(View.GONE);
                ll_ztmjxs.setVisibility(View.VISIBLE);
                ll_t1.setVisibility(View.VISIBLE);
                ll_tal1.setVisibility(View.VISIBLE);
                ll_gdds.setVisibility(View.GONE);
                ll_edual.setVisibility(View.GONE);
                ll_tp.setVisibility(View.VISIBLE);
                ll_ekdyie.setVisibility(View.INVISIBLE);
                ll_scial.setVisibility(View.INVISIBLE);
                ll_ecsjcs.setVisibility(View.INVISIBLE);
                if (gz_statue == 0) {
                    ll_t2.setVisibility(View.INVISIBLE);
                    ll_tfr.setVisibility(View.INVISIBLE);
                    ll_tal2.setVisibility(View.INVISIBLE);
                } else if (gz_statue == 1) {
                    ll_t2.setVisibility(View.VISIBLE);
                    ll_tfr.setVisibility(View.VISIBLE);
                    ll_tal2.setVisibility(View.VISIBLE);
                }
                break;
            case 7:
                ll_edybbaxs.setVisibility(View.INVISIBLE);
                ll_zqxzxs.setVisibility(View.GONE);
                ll_kssc.setVisibility(View.VISIBLE);
                ll_gzxh.setVisibility(View.INVISIBLE);
                ll_jsxs.setVisibility(View.GONE);
                ll_ztmjxs.setVisibility(View.VISIBLE);
                ll_t1.setVisibility(View.INVISIBLE);
                ll_tal1.setVisibility(View.INVISIBLE);
                ll_gdds.setVisibility(View.GONE);
                ll_edual.setVisibility(View.GONE);
                ll_tp.setVisibility(View.VISIBLE);
                ll_tfr.setVisibility(View.INVISIBLE);
                ll_t2.setVisibility(View.INVISIBLE);
                ll_ekdyie.setVisibility(View.GONE);
                ll_scial.setVisibility(View.GONE);
                ll_ecsjcs.setVisibility(View.VISIBLE);
                ll_tal2.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void updateView(int i) {
        switch (i) {
            case 1:
                ll_dqwd.setVisibility(View.VISIBLE);
                jb_ll.setVisibility(View.INVISIBLE);
                edyc_ll.setVisibility(View.INVISIBLE);
                ll_fh.setVisibility(View.INVISIBLE);
                ll_glys.setVisibility(View.INVISIBLE);
                ll_edgl.setVisibility(View.INVISIBLE);
                ll_edecdl.setVisibility(View.INVISIBLE);
                break;
            case 2:
                ll_dqwd.setVisibility(View.VISIBLE);
                jb_ll.setVisibility(View.VISIBLE);
                edyc_ll.setVisibility(View.VISIBLE);
                ll_fh.setVisibility(View.VISIBLE);
                ll_glys.setVisibility(View.VISIBLE);
                ll_edgl.setVisibility(View.VISIBLE);
                ll_edecdl.setVisibility(View.VISIBLE);
                break;
            case 3:
                ll_dqwd.setVisibility(View.INVISIBLE);
                jb_ll.setVisibility(View.INVISIBLE);
                edyc_ll.setVisibility(View.INVISIBLE);
                ll_fh.setVisibility(View.INVISIBLE);
                ll_glys.setVisibility(View.INVISIBLE);
                ll_edgl.setVisibility(View.VISIBLE);
                ll_edecdl.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initView(View view) {
        checkBox1 = (CheckBox) view.findViewById(R.id.check_tv1);
        checkBox2 = (CheckBox) view.findViewById(R.id.check_tv2);
        checkBox3 = (CheckBox) view.findViewById(R.id.check_tv3);
        checkBox4 = (CheckBox) view.findViewById(R.id.check_tv4);
        MainActivity activity = (MainActivity) getActivity();
        ArrayList<Fragment> fragments = activity.getFragments();
        resultFragment = (ResultFragment) fragments.get(1);
        jb_ll = (LinearLayout) view.findViewById(R.id.jb_ll);
        edyc_ll = (LinearLayout) view.findViewById(R.id.ll_edycdl);
        ll_fh = (LinearLayout) view.findViewById(R.id.ll_fh);
        ll_glys = (LinearLayout) view.findViewById(R.id.ll_glys);
        ll_edecdl = (LinearLayout) view.findViewById(R.id.ll_edecdl);
        ll_edgl = (LinearLayout) view.findViewById(R.id.ll_edgl);
        ll_dqwd = (LinearLayout) view.findViewById(R.id.ll_dqwd);
        jbButton = (Button) view.findViewById(R.id.jb_btn);
        ll_edybbaxs = (LinearLayout) view.findViewById(R.id.ll_edybbaxs);
        ll_zqxzxs = (LinearLayout) view.findViewById(R.id.ll_zqxzxs);
        ll_kssc = (LinearLayout) view.findViewById(R.id.ll_kssc);
        ll_gzxh = (LinearLayout) view.findViewById(R.id.ll_gzxh);
        gzxhButton = (TextView) view.findViewById(R.id.gzxh_btn);
        ll_jsxs = (LinearLayout) view.findViewById(R.id.ll_jsxs);
        ll_ztmjxs = (LinearLayout) view.findViewById(R.id.ll_ztmjxs);
        ll_t1 = (LinearLayout) view.findViewById(R.id.ll_t1);
        ll_tal1 = (LinearLayout) view.findViewById(R.id.ll_tal1);
        ll_gdds = (LinearLayout) view.findViewById(R.id.ll_gdds);
        ll_edual = (LinearLayout) view.findViewById(R.id.ll_edual);
        ll_tp = (LinearLayout) view.findViewById(R.id.ll_tp);
        ll_tfr = (LinearLayout) view.findViewById(R.id.ll_tfr);
        ll_t2 = (LinearLayout) view.findViewById(R.id.ll_t2);
        ll_ekdyie = (LinearLayout) view.findViewById(R.id.ll_ekdyie);
        ll_scial = (LinearLayout) view.findViewById(R.id.ll_scial);
        ll_ecsjcs = (LinearLayout) view.findViewById(R.id.ll_ecsjcs);
        ll_tal2 = (LinearLayout) view.findViewById(R.id.ll_tal2);
    }

}
