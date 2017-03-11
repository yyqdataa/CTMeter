package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.bean.LCQXBean;
import com.yanyuqi.ctmeter.widget.Graphs;

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
public class LCQXFragment extends Fragment {

    private static List<LCQXBean> resultData;
    private static ResultFragment resultFragment;
    private static List<Fragment> fragments;
    private Button backBtn;
    private Graphs lcqxGraphs;

    /**
     *  1_0.001399_1.67381_-0.86037_0.00000_0.00560_0.00000_0.00280_0.001979_0.54129
     2_0.001816_2.50021_-0.86593_0.00000_0.00726_0.00000_0.00363_0.002568_0.59957
     3_0.002104_3.33033_-1.01713_78.26898_0.00842_158.08128_0.00421_0.002975_0.67836
     4_0.002567_4.15910_-0.93055_80.16180_0.01027_161.88026_0.00513_0.003629_0.57985
     5_0.002892_4.98787_-0.92710_85.39320_0.01157_172.37985_0.00578_0.004089_0.58483
     6_0.003555_6.64880_-0.91732_92.66026_0.01422_186.96510_0.00711_0.005027_0.56167
     7_0.003702_7.47927_-0.93928_100.18851_0.01481_202.07455_0.00740_0.005234_0.65821
     8_0.004103_8.30973_-0.90026_100.42826_0.01641_202.55574_0.00821_0.005802_0.62482
     9_0.004637_9.97066_-0.88760_106.68433_0.01855_215.11189_0.00927_0.006557_0.62692
     10_0.005106_11.63159_-0.86612_113.09568_0.02042_227.97971_0.01021_0.007219_0.67528
     11_0.006071_14.95514_-0.83669_122.35838_0.02429_246.57025_0.01214_0.008585_0.67494
     12_0.006473_16.61438_-0.81953_127.54743_0.02589_256.98486_0.01295_0.009153_0.71043
     13_0.007338_20.77009_-0.78559_140.75197_0.02935_283.48682_0.01468_0.010376_0.75399
     14_0.008205_23.25640_-0.76469_140.95409_0.03282_283.89249_0.01641_0.011601_0.72734
     15_0.009070_27.40533_-0.73669_150.32095_0.03628_302.69211_0.01814_0.012825_0.76044
     16_0.010027_32.37117_-0.70918_160.68404_0.04011_323.49118_0.02005_0.014178_0.78604
     17_0.012698_44.82814_-0.64811_175.79089_0.05079_353.81119_0.02540_0.017955_0.83182
     18_0.015886_57.25122_-0.63373_179.47972_0.06354_361.21481_0.03177_0.022463_0.83195
     19_0.017868_63.04752_-0.63421_175.70468_0.07147_353.63812_0.03574_0.025265_0.85334
     20_0.020142_68.82687_-0.66891_170.12843_0.08057_342.44641_0.04028_0.028480_0.85386
     21_0.022614_73.79272_-0.71867_162.41994_0.09045_326.97522_0.04523_0.031976_0.85424
     22_0.025299_77.92809_-0.77399_153.25937_0.10120_308.58960_0.05060_0.035773_0.85436
     23_0.028595_82.04652_-0.83717_142.69298_0.11438_287.38251_0.05719_0.040433_0.82741
     24_0.031952_85.35143_-0.89978_132.77725_0.12781_267.48132_0.06390_0.045180_0.83925
     25_0.036987_89.45291_-0.96842_120.11971_0.14795_242.07718_0.07397_0.052300_0.81912
     26_0.042206_92.74087_-1.02671_109.04671_0.16882_219.85329_0.08441_0.059679_0.80495
     27_0.045120_94.36790_-1.04368_103.74515_0.18048_209.21286_0.09024_0.063800_0.79228
     28_0.050751_96.82540_-1.10101_94.55066_0.20300_190.75920_0.10150_0.071762_0.79733
     29_0.056473_99.28290_-1.12987_87.04962_0.22589_175.70435_0.11295_0.079853_0.78570
     30_0.064438_101.75735_-1.18366_78.09059_0.25775_157.72325_0.12888_0.091115_0.77443
     */

    public LCQXFragment() {
        // Required empty public constructor
    }


    public static LCQXFragment newInstance(List<Fragment> fragments,ResultFragment resultFragment,List<LCQXBean> resultData) {

        Bundle args = new Bundle();

        LCQXFragment fragment = new LCQXFragment();
        fragment.setArguments(args);
        LCQXFragment.resultData = resultData;
        LCQXFragment.resultFragment = resultFragment;
        LCQXFragment.fragments = fragments;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lcqx, container, false);
        initView(view);
        initListener();
        getFile();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
            resultData.clear();
            while ((s = reader.readLine())!=null){
                creatBeanByString(s);
            }
            lcqxGraphs.setResultData(resultData);
            lcqxGraphs.setGDPoint(0.0395f,91.0524f);
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

    }

    private void showResultFrag() {
        MainActivity activity = (MainActivity) getActivity();
        fragments.set(1,resultFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments,this.getClass().getName());
    }

    private void initView(View view) {
        backBtn = (Button) view.findViewById(R.id.btn_back);
        lcqxGraphs = (Graphs) view.findViewById(R.id.lcqx_graphs);
    }

}
