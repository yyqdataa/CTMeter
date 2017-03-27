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
    private String dataStr = "1_0.001399_1.67381_-0.86037_0.00000_0.00560_0.00000_0.00280_0.001979_0.54129\n" +
            "     2_0.001816_2.50021_-0.86593_0.00000_0.00726_0.00000_0.00363_0.002568_0.59957\n" +
            "     3_0.002104_3.33033_-1.01713_78.26898_0.00842_158.08128_0.00421_0.002975_0.67836\n" +
            "     4_0.002567_4.15910_-0.93055_80.16180_0.01027_161.88026_0.00513_0.003629_0.57985\n" +
            "     5_0.002892_4.98787_-0.92710_85.39320_0.01157_172.37985_0.00578_0.004089_0.58483\n" +
            "     6_0.003555_6.64880_-0.91732_92.66026_0.01422_186.96510_0.00711_0.005027_0.56167\n" +
            "     7_0.003702_7.47927_-0.93928_100.18851_0.01481_202.07455_0.00740_0.005234_0.65821\n" +
            "     8_0.004103_8.30973_-0.90026_100.42826_0.01641_202.55574_0.00821_0.005802_0.62482\n" +
            "     9_0.004637_9.97066_-0.88760_106.68433_0.01855_215.11189_0.00927_0.006557_0.62692\n" +
            "     10_0.005106_11.63159_-0.86612_113.09568_0.02042_227.97971_0.01021_0.007219_0.67528\n" +
            "     11_0.006071_14.95514_-0.83669_122.35838_0.02429_246.57025_0.01214_0.008585_0.67494\n" +
            "     12_0.006473_16.61438_-0.81953_127.54743_0.02589_256.98486_0.01295_0.009153_0.71043\n" +
            "     13_0.007338_20.77009_-0.78559_140.75197_0.02935_283.48682_0.01468_0.010376_0.75399\n" +
            "     14_0.008205_23.25640_-0.76469_140.95409_0.03282_283.89249_0.01641_0.011601_0.72734\n" +
            "     15_0.009070_27.40533_-0.73669_150.32095_0.03628_302.69211_0.01814_0.012825_0.76044\n" +
            "     16_0.010027_32.37117_-0.70918_160.68404_0.04011_323.49118_0.02005_0.014178_0.78604\n" +
            "     17_0.012698_44.82814_-0.64811_175.79089_0.05079_353.81119_0.02540_0.017955_0.83182\n" +
            "     18_0.015886_57.25122_-0.63373_179.47972_0.06354_361.21481_0.03177_0.022463_0.83195\n" +
            "     19_0.017868_63.04752_-0.63421_175.70468_0.07147_353.63812_0.03574_0.025265_0.85334\n" +
            "     20_0.020142_68.82687_-0.66891_170.12843_0.08057_342.44641_0.04028_0.028480_0.85386\n" +
            "     21_0.022614_73.79272_-0.71867_162.41994_0.09045_326.97522_0.04523_0.031976_0.85424\n" +
            "     22_0.025299_77.92809_-0.77399_153.25937_0.10120_308.58960_0.05060_0.035773_0.85436\n" +
            "     23_0.028595_82.04652_-0.83717_142.69298_0.11438_287.38251_0.05719_0.040433_0.82741\n" +
            "     24_0.031952_85.35143_-0.89978_132.77725_0.12781_267.48132_0.06390_0.045180_0.83925\n" +
            "     25_0.036987_89.45291_-0.96842_120.11971_0.14795_242.07718_0.07397_0.052300_0.81912\n" +
            "     26_0.042206_92.74087_-1.02671_109.04671_0.16882_219.85329_0.08441_0.059679_0.80495\n" +
            "     27_0.045120_94.36790_-1.04368_103.74515_0.18048_209.21286_0.09024_0.063800_0.79228\n" +
            "     28_0.050751_96.82540_-1.10101_94.55066_0.20300_190.75920_0.10150_0.071762_0.79733\n" +
            "     29_0.056473_99.28290_-1.12987_87.04962_0.22589_175.70435_0.11295_0.079853_0.78570\n" +
            "     30_0.064438_101.75735_-1.18366_78.09059_0.25775_157.72325_0.12888_0.091115_0.77443\n" +
            "     31_0.072693_104.19789_-1.21271_70.79186_0.29077_143.07445_0.14539_0.102788_0.75535\n" +
            "     32_0.079773_105.82493_-1.23984_65.44272_0.31909_132.33855_0.15955_0.112799_0.75560\n" +
            "     33_0.092789_108.28242_-1.27886_57.45083_0.37115_116.29853_0.18558_0.131203_0.74776\n" +
            "   34_0.103638_109.90945_-1.30494_52.11971_0.41455_105.59879_0.20728_0.146544_0.73979\n" +
            "   35_0.117050_111.51954_-1.33092_46.72330_0.46820_94.76801_0.23410_0.165509_0.73167\n" +
            "   36_0.147186_113.96009_-1.38030_37.78528_0.58875_76.82908_0.29437_0.208121_0.71679            \n" +
            "   37_0.176086_115.55323_-1.38605_31.87504_0.70435_64.96703_0.35217_0.248986_0.72306            \n" +
            "   38_0.195770_116.34979_-1.40148_28.77478_0.78308_58.74469_0.39154_0.276819_0.71413            \n" +
            "   39_0.251770_117.90903_-1.43552_22.46544_1.00708_46.08162_0.50354_0.356003_0.71324            \n" +
            "   40_0.292969_118.67171_-1.45345_19.29797_1.17188_39.72440_0.58594_0.414258_0.71269            \n" +
            "   41_0.343475_119.43438_-1.46984_16.42653_1.37390_33.96131_0.68695_0.485674_0.70323            \n" +
            "   42_0.406647_120.16315_-1.48480_13.81132_1.62659_28.71250_0.81329_0.574998_0.70206            \n" +
            "   43_0.485687_120.87498_-1.49793_11.47664_1.94275_24.02671_0.97137_0.686762_0.70078            \n" +
            "   44_0.583954_121.56985_-1.50819_9.43908_2.33582_19.93726_1.16791_1.765137_0.69024             \n" +
            "   45_0.707245_122.24779_-1.51270_7.66976_2.82898_16.38618_1.41449_2.303467_0.68664             \n" +
            "   46_0.865326_123.02740_-1.50436_6.13366_3.46130_13.30315_1.73065_3.025513_0.68819             \n" +
            "   47_1.037445_123.77313_-1.47674_4.98849_4.14978_11.00477_2.07489_3.816224_0.68937             \n" +
            "   48_1.219025_123.92567_-1.46351_4.10487_4.87610_9.23130_2.43805_4.489441_0.68528              \n" +
            "   49_1.529083_124.51885_-1.48183_3.09204_6.11633_7.19853_3.05817_4.999848_0.68717              \n" +
            "   50_2.851868_125.43406_-1.37186_1.21670_11.40747_3.43466_5.70374_10.697938_0.74794            \n" +
            "   51_3.384400_125.77303_-1.36591_0.87517_13.53760_2.74918_6.76880_12.547303_0.74464            \n" +
            "   52_3.959656_126.21368_-1.36198_0.61038_15.83862_2.21775_7.91931_14.289857_0.74145            \n" +
            "   53_4.544067_126.63739_-1.35834_0.40977_18.17627_1.81512_9.08813_15.914918_0.73856            \n" +
            "   54_5.151368_127.07804_-1.35489_0.24954_20.60547_1.49352_10.30274_17.539978_0.74398           \n" ;

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
        initData();
//        getFile();
        return view;
    }

    private void initData() {
        if (resultData.size() > 0) {
            resultData.clear();
        }
        while (dataStr.length() > 0) {
            int pos = dataStr.indexOf("\n");
            String s = dataStr.substring(0, pos);
            creatBeanByString(s);
            dataStr = dataStr.substring(pos + 1);
        }
        wcsjAdapter = new LCSJAdapter(getContext(), resultData);
        dataListView.setAdapter(wcsjAdapter);
    }

    private void getFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File storageDirectory = Environment.getExternalStorageDirectory();
            String path = storageDirectory.getAbsolutePath() + File.separator + "CTMeter" + File.separator + "CT.ctp";
            File file = new File(path);
            ReadFile(file);
        }
    }

    private void ReadFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String s = "";
            resultData.clear();
            while ((s = reader.readLine()) != null) {
                creatBeanByString(s);
            }
            wcsjAdapter = new LCSJAdapter(getContext(), resultData);
            dataListView.setAdapter(wcsjAdapter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creatBeanByString(String s) {
        if (s == null || s.length() == 0) {
            return;
        }
        Log.i("yyq", s);
        try {
            int pos = s.indexOf("_");
            String e_no = s.substring(0, pos);
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fi = Float.parseFloat(s.substring(0, pos));
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fu = Float.parseFloat(s.substring(0, pos));
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fA = Float.parseFloat(s.substring(0, pos));
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fUc = Float.parseFloat(s.substring(0, pos));
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fIpe = Float.parseFloat(s.substring(0, pos));
            s = s.substring(pos + 1);
            pos = s.indexOf("_");
            float e_fUKr = Float.parseFloat(s.substring(0, pos));
            LCQXBean lcqxBean = new LCQXBean(e_no, e_fu, e_fi, e_fUc, e_fA, e_fIpe, e_fUKr);
            resultData.add(lcqxBean);
        } catch (Exception e) {
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
                if ("5%误差数据".equals(text)) {
                    changeBtn.setText("10%误差数据");
                    typeText.setText("-5%误差曲线数据");
                } else if ("10%误差数据".equals(text)) {
                    changeBtn.setText("5%误差数据");
                    typeText.setText("-10%误差曲线数据");
                }
            }
        });
    }

    private void showResultFrag() {
        MainActivity activity = (MainActivity) getActivity();
        fragments.set(1, resultFragment);
        (activity.tabLayout.getTabFragmentManager()).setFragments(fragments, this.getClass().getName());
    }

    private void initView(View view) {
        typeText = (TextView) view.findViewById(R.id.data_type_tv);
        backBtn = (Button) view.findViewById(R.id.btn_back);
        dataListView = (ListView) view.findViewById(R.id.data_lv);
        changeBtn = (Button) view.findViewById(R.id.btn_change);
    }
}
