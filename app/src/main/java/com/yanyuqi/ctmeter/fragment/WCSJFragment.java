package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.adapter.LCSJAdapter;
import com.yanyuqi.ctmeter.bean.LCQXBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WCSJFragment extends Fragment {


    private static List<Fragment> fragments;
    private static ResultFragment resultFragment;
    private static List<LCQXBean> resultData;
    private TextView typeText;
    private Button backBtn;
    private ListView dataListView;
    private Button changeBtn;
    private LCSJAdapter wcsjAdapter;

    public WCSJFragment() {
        // Required empty public constructor
    }

    public static WCSJFragment newInstance(List<Fragment> fragments, ResultFragment resultFragment, List<LCQXBean> resultData) {

        Bundle args = new Bundle();

        WCSJFragment fragment = new WCSJFragment();
        fragment.setArguments(args);
        WCSJFragment.fragments = fragments;
        WCSJFragment.resultFragment = resultFragment;
        WCSJFragment.resultData = resultData;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wcsj, container, false);
        initView(view);
        initListener();
        getFile();
        return view;
    }

    private void getFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File storageDirectory = Environment.getExternalStorageDirectory();
            String path = storageDirectory.getAbsolutePath()+File.separator+"CTMeter"+File.separator+"CT.ctp";
            File file = new File(path);
            ReadFile(file);
        }
    }

    private void ReadFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = "";
            while ((s = reader.readLine())!=null){
                creatBeanByString(s);
            }
            wcsjAdapter = new LCSJAdapter(getContext(),resultData);
//            dataListView.setAdapter(wcsjAdapter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creatBeanByString(String s) {
        if (s==null||s.length()==0){
            return;
        }
        Log.i("yyq",s);
        try{
            int pos = s.indexOf("_");
            String e_no = s.substring(0,pos);
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fi = Float.parseFloat(s.substring(0,pos));
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fu = Float.parseFloat(s.substring(0,pos));
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fA = Float.parseFloat(s.substring(0,pos));
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fUc = Float.parseFloat(s.substring(0,pos));
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fIpe = Float.parseFloat(s.substring(0,pos));
            s= s.substring(pos+1);
            pos = s.indexOf("_");
            float e_fUKr = Float.parseFloat(s.substring(0,pos));
            LCQXBean lcqxBean = new LCQXBean(e_no,e_fu,e_fi,e_fUc,e_fA,e_fIpe,e_fUKr);
            resultData.add(lcqxBean);
        }catch (Exception e){
            return;
        }

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
                if ("5%误差数据".equals(text)){
                    changeBtn.setText("10%误差数据");
                    typeText.setText("-5%误差曲线数据");
                }else  if ("10%误差数据".equals(text)){
                    changeBtn.setText("5%误差数据");
                    typeText.setText("-10%误差曲线数据");
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
        typeText = (TextView) view.findViewById(R.id.data_type_tv);
        backBtn = (Button) view.findViewById(R.id.btn_back);
        dataListView = (ListView) view.findViewById(R.id.data_lv);
        changeBtn = (Button) view.findViewById(R.id.btn_change);
    }
}
