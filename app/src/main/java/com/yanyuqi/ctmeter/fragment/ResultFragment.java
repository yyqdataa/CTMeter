package com.yanyuqi.ctmeter.fragment;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.adapter.ResultBtnAdapter;
import com.yanyuqi.ctmeter.bean.LCQXBean;
import com.yanyuqi.ctmeter.interf.ChangeRightBtn;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment implements ChangeRightBtn {


    private ListView btnListView;
    public static final String[] BTN_ZERO = {};
    private static final String[] BTN_TWO = {"励磁数据", "励磁曲线"};
    private static final String[] BTN_FOUR = {"误差数据", "误差曲线", "励磁数据", "励磁曲线"};
    private static final String[] BTN_SIX = {"相位差表", "比值差表", "误差数据", "误差曲线", "励磁数据", "励磁曲线"};
    private TextView textChangeOne;
    private TextView textChangeTwo;
    private TextView textChangeThree;
    private TextView textChangeFour;
    private TextView textChangeFive;
    private List resultData = new ArrayList<>();
    private ResultBtnAdapter resultBtnAdapter;
    private List<String> data = new ArrayList<>();
    private TextView valueTextOne;
    private TextView valueTextTwo;
    private TextView valueTextThree;
    private TextView valueTextFour;
    private TextView valueTextFive;
    private String temp = "___0.8239 Ω_0.9857 Ω_91.05 V_0.03952 A_79.287 V_0.02638 A_0.05 ％_9.921 H_0.685_?_ ?_ ?_1.0_0.05 ％_16.9__6.770 s_4000.0 : 4.9890_801.30_反极性/＋_-0.22 ％_1.644 '_ ";
    private String[] str = new String[25];
    private TextView textScfh;
    private TextView textGlys;
    private TextView textZk;
    private TextView textDqzz;
    private TextView textDz;
    private TextView textGddy;
    private TextView textBbhdg;
    private TextView textGddl;
    private TextView textScxs;
    private TextView textBb;
    private TextView textZsb;
    private TextView textBzc;
    private TextView textJx;
    private TextView textXwc;
    private TextView textZbwc;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance() {

        Bundle args = new Bundle();

        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        initView(view);
        initListener();
        initData();
        return view;
    }

    //初始化数据

    private void initData() {
        int pos;
        for (int i = 0; i < str.length; i++) {
            pos = temp.indexOf("_");
            if (pos == -1) {
                return;
            }
            str[i] = temp.substring(0, pos);
            temp = temp.substring(pos + 1);
        }
    }

    //3333444
    private void initListener() {
        MainActivity activity = (MainActivity) getActivity();
        resultBtnAdapter = new ResultBtnAdapter(activity.getFragments(), this, resultData, data, getContext(), getFragmentManager());
        btnListView.setAdapter(resultBtnAdapter);
    }

    private void initView(View view) {
        btnListView = (ListView) view.findViewById(R.id.btn_lv);
        textChangeOne = (TextView) view.findViewById(R.id.text_change_one);
        textChangeTwo = (TextView) view.findViewById(R.id.text_change_two);
        textChangeThree = (TextView) view.findViewById(R.id.text_change_three);
        textChangeFour = (TextView) view.findViewById(R.id.text_change_four);
        textChangeFive = (TextView) view.findViewById(R.id.text_change_five);
        valueTextOne = (TextView) view.findViewById(R.id.value_text_one);
        valueTextTwo = (TextView) view.findViewById(R.id.value_text_two);
        valueTextThree = (TextView) view.findViewById(R.id.value_text_three);
        valueTextFour = (TextView) view.findViewById(R.id.value_text_four);
        valueTextFive = (TextView) view.findViewById(R.id.value_text_five);
        textScfh = (TextView) view.findViewById(R.id.text_scfh);
        textGlys = (TextView) view.findViewById(R.id.text_glys);
        textZk = (TextView) view.findViewById(R.id.text_zk);
        textDqzz = (TextView) view.findViewById(R.id.text_dqzz);
        textDz = (TextView) view.findViewById(R.id.text_dz);
        textGddy = (TextView) view.findViewById(R.id.text_gddy);
        textBbhdg = (TextView) view.findViewById(R.id.text_bbhdg);
        textGddl = (TextView) view.findViewById(R.id.text_gddl);
        textScxs = (TextView) view.findViewById(R.id.text_scxs);
        textBb = (TextView) view.findViewById(R.id.text_bb);
        textZsb = (TextView) view.findViewById(R.id.text_zsb);
        textBzc = (TextView) view.findViewById(R.id.text_bzc);
        textJx = (TextView) view.findViewById(R.id.text_jx);
        textXwc = (TextView) view.findViewById(R.id.text_xwc);
        textZbwc = (TextView) view.findViewById(R.id.text_zbwc);
    }

    @Override
    public void changeRightBtn(int testType) {

        switch (testType) {
            case 0:
                setBtnListView(BTN_ZERO);
                break;
            case 2:
                setBtnListView(BTN_TWO);
                break;
            case 4:
                setBtnListView(BTN_FOUR);
                break;
            case 6:
                setBtnListView(BTN_SIX);
                break;
        }
    }

    @Override
    public void setResultView(int jbNum) {
        switch (jbNum) {
            case 0:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.VISIBLE);
                textChangeThree.setText("准确限值系数");
                textChangeFour.setVisibility(View.INVISIBLE);
                textChangeFive.setVisibility(View.INVISIBLE);
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 1:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.INVISIBLE);
                textChangeThree.setText("Kssc");
                textChangeFour.setVisibility(View.VISIBLE);
                textChangeFour.setText("暂态面积系数");
                textChangeFive.setVisibility(View.VISIBLE);
                textChangeFive.setText("峰顺误差");
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 2:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.VISIBLE);
                textChangeThree.setText("仪表保安系数");
                textChangeFour.setVisibility(View.INVISIBLE);
                textChangeFive.setVisibility(View.INVISIBLE);
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 3:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.VISIBLE);
                textChangeThree.setText("准确限值系数");
                textChangeFour.setVisibility(View.INVISIBLE);
                textChangeFive.setVisibility(View.INVISIBLE);
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 4:
                textChangeOne.setVisibility(View.INVISIBLE);
                textChangeTwo.setVisibility(View.VISIBLE);
                textChangeThree.setText("计算系数Kx");
                textChangeFour.setVisibility(View.VISIBLE);
                textChangeFour.setText("拐点电势Ek");
                textChangeFive.setVisibility(View.VISIBLE);
                textChangeFive.setText("Ek对应Ie");
                break;
            case 5:
                textChangeOne.setVisibility(View.INVISIBLE);
                textChangeTwo.setVisibility(View.INVISIBLE);
                textChangeThree.setText("Kssc");
                textChangeFour.setVisibility(View.VISIBLE);
                textChangeFour.setText("额定Ual");
                textChangeFive.setVisibility(View.VISIBLE);
                textChangeFive.setText("实测Ial");
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 6:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.INVISIBLE);
                textChangeThree.setText("Kssc");
                textChangeFour.setVisibility(View.VISIBLE);
                textChangeFour.setText("暂态面积系数");
                textChangeFive.setVisibility(View.VISIBLE);
                textChangeFive.setText("峰顺误差");
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
            case 7:
                textChangeOne.setVisibility(View.VISIBLE);
                textChangeTwo.setVisibility(View.INVISIBLE);
                textChangeThree.setText("Kssc");
                textChangeFour.setVisibility(View.VISIBLE);
                textChangeFour.setText("暂态面积系数");
                textChangeFive.setVisibility(View.VISIBLE);
                textChangeFive.setText("峰顺误差");
                textGddy.setText(str[5]);
                textGddl.setText(str[6]);
                break;
        }
    }

    private void setBtnListView(String[] btnTwo) {
        data.clear();
        for (int i = 0; i < btnTwo.length; i++) {
            data.add(btnTwo[i]);
        }
        resultBtnAdapter.notifyDataSetChanged();
    }


}
