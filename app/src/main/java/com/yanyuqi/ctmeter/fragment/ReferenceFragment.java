package com.yanyuqi.ctmeter.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.StorageManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.GetChars;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.activity.HtmlActivity;
import com.yanyuqi.ctmeter.activity.MainActivity;
import com.yanyuqi.ctmeter.adapter.RefVPAdapter;
import com.yanyuqi.ctmeter.adapter.ReportListAdapter;
import com.yanyuqi.ctmeter.utils.ChangePixel;
import com.yanyuqi.ctmeter.utils.StoregeDao;
import com.yanyuqi.ctmeter.utils.WifiAdmin;
import com.yanyuqi.ctmeter.widget.ScrollChangeIndicator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferenceFragment extends Fragment implements ViewPager.OnPageChangeListener {


    public static final String DIR = "ReportXls";
    private ScrollChangeIndicator indicator;
    private ViewPager viewPager;
    private List fragments = new ArrayList();
    private ResultFragment resultFragment;
    private DLHGQFragment dlhgqFragment;
    private DYHGQFragment dyhgqFragment;
    private Button btnTest;
    private Button btnReport;
    private Button btnTool;
    private Button btnHelp;
    private Dialog dialog;
    private EditText ipEditText;
    private EditText dkEditText;
    private CheckBox setBlockCheck;
    private TextView wifiStateTv;
    private TextView wifiAcceptTv;
    private Button wifiClearBtn;
    private Button wifiTestBtn;
    private SharedPreferences configSharePre;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    wifiStateTv.setText("未连接");
                    break;
                case 1:
                    wifiStateTv.setText("wifi未开");
                    break;
            }
        }
    };
    private TextView reportPathTv;
    private ListView reportListView;


    public ReferenceFragment() {
        // Required empty public constructor
    }

    public static ReferenceFragment newInstance() {

        Bundle args = new Bundle();

        ReferenceFragment fragment = new ReferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reference, container, false);
        initView(view);
        initData();
        initdapter();
        initListener();
        return view;
    }

    private void initListener() {
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 开始测试
                Toast.makeText(getContext(), "尚未建立合法网络链接！！请检查！！", Toast.LENGTH_SHORT).show();
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = showReportDialog();
                initReportView(view);
                setReport();
            }
        });
        btnTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = showToolDialog();
                initSetting(view);
            }
        });
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream is = getContext().getClassLoader().getResourceAsStream("assets/Help.doc");
                File demoFile = writeFile(is, "/mnt/sdcard/CTPMeter/Help.doc");
                openDoc(demoFile);
            }
        });
    }


    /**
     * 设置报告模板功能
     */
    private void setReport() {
        File directory = Environment.getExternalStorageDirectory();
        String str = directory.toString() + File.separator + "CTPMeter" + File.separator + DIR;
        reportPathTv.setText(str);
        List<File> sdFiles = StoregeDao.getSDFiles(DIR);
        for (int i = 0; i < 20; i++) {
            File file = new File("CTP_Test" + i + ".xls");
            sdFiles.add(file);
        }
        final ReportListAdapter adapter = new ReportListAdapter(getContext(), sdFiles);
        reportListView.setAdapter(adapter);
        reportListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setCheckedItem(position);
                InputStream is = getContext().getClassLoader().getResourceAsStream("assets/CTP.doc");
                File file = writeFile(is, "/mnt/sdcard/CTPMeter/ReportXls/CTP.doc");
                openDoc(file);
            }
        });
    }

    private void initReportView(View view) {
        reportPathTv = (TextView) view.findViewById(R.id.report_path_tv);
        reportListView = (ListView) view.findViewById(R.id.report_lv);
    }

    /**
     * 打开报告界面
     */
    private View showReportDialog() {
        dialog = new Dialog(getContext(), R.style.Theme_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.report_dialog, null);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager manager = getActivity().getWindowManager();
        Display defaultDisplay = manager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = defaultDisplay.getWidth() / 8 * 7;
        attributes.height = ChangePixel.dp2px(getContext(), 270);
        dialog.getWindow().setAttributes(attributes);
        dialog.show();
        return contentView;
    }

    /**
     * 初始化系统设置
     *
     * @param view
     */
    private void initSetting(View view) {
        initSettingView(view);
        configSharePre = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = configSharePre.edit();
        initSettingListener(edit);

    }

    private void initSettingListener(final SharedPreferences.Editor edit) {
        setBlockCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ipEditText.setEnabled(false);
                    dkEditText.setEnabled(false);
                } else {
                    ipEditText.setEnabled(true);
                    dkEditText.setEnabled(true);
                }
                edit.putBoolean("isBlock", isChecked);
                edit.commit();
            }
        });
        setBlockCheck.setChecked(configSharePre.getBoolean("isBlock", true));
        ipEditText.setOnFocusChangeListener(new RegxEditTextFocusListener(edit, "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$"));
        dkEditText.setOnFocusChangeListener(new RegxEditTextFocusListener(edit, "^\\d{4,4}$"));
        wifiClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiAcceptTv.setText("");
            }
        });

        new Thread() {
            @Override
            public void run() {
                while (dialog.isShowing()) {
                    super.run();
                    WifiAdmin wifiAdmin = new WifiAdmin(getContext());
                    Message message = handler.obtainMessage();
                    if (wifiAdmin.isWifiOpen()) {
                        message.what = 0;
                    } else {
                        message.what = 1;
                    }
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    private void initSettingView(View view) {
        ipEditText = (EditText) view.findViewById(R.id.set_ip_edit);
        dkEditText = (EditText) view.findViewById(R.id.set_dk_edit);
        setBlockCheck = (CheckBox) view.findViewById(R.id.set_check_block);
        wifiStateTv = (TextView) view.findViewById(R.id.wifi_state_tv);
        wifiAcceptTv = (TextView) view.findViewById(R.id.wifi_accept_tv);
        wifiClearBtn = (Button) view.findViewById(R.id.wifi_clear_btn);
        wifiTestBtn = (Button) view.findViewById(R.id.wifi_test_btn);
    }

    /**
     * 系统设置dialog
     */
    private View showToolDialog() {
        dialog = new Dialog(getContext(), R.style.Theme_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.setting_dialog, null);
        dialog.getWindow().setContentView(contentView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager manager = getActivity().getWindowManager();
        Display defaultDisplay = manager.getDefaultDisplay();
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = defaultDisplay.getWidth() / 8 * 7;
        dialog.getWindow().setAttributes(attributes);
        dialog.show();
        return contentView;
    }

    /**
     * 刻录模板文件
     *
     * @param is
     * @param s  模板路径
     * @return
     */
    private File writeFile(InputStream is, String s) {
        File file = new File(s);
        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            OutputStream os = new FileOutputStream(file, false);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            os.flush();
            os.close();
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 三方打开
     *
     * @param file
     */
    private void openDoc(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        String type = "application/msword";
        intent.setDataAndType(Uri.fromFile(file), type);
        getContext().startActivity(intent);
    }

    /**
     * html待完善，不使用
     *
     * @param file
     */
    private void openHtml(File file) {
        Intent intent = new Intent();
        intent.putExtra("name", file.getName());
        intent.putExtra("path", file.getParent());
        intent.setClass(getContext(), HtmlActivity.class);
        startActivity(intent);
    }

    private void initdapter() {
        RefVPAdapter refVPAdapter = new RefVPAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(refVPAdapter);
        viewPager.setOffscreenPageLimit(2);
        indicator.setViewPager(viewPager);
        indicator.setAngel(30);
        indicator.setLeftBackColor(Color.parseColor("#FF026029"));
        indicator.setRightBackColor(Color.parseColor("#ffffff"));
        indicator.setLeftColorValue(Color.parseColor("#FF4081"));
        indicator.setRightColorValue(Color.parseColor("#6c6a6a"));
        indicator.setOnPageChangeListener(this);
    }

    private void initData() {
        dlhgqFragment = DLHGQFragment.newInstance();
        dyhgqFragment = DYHGQFragment.newInstance();
        fragments.add(dlhgqFragment);
        fragments.add(dyhgqFragment);
    }

    private void initView(View view) {
        indicator = (ScrollChangeIndicator) view.findViewById(R.id.scroll_indicator);
        viewPager = (ViewPager) view.findViewById(R.id.ref_vp);
        MainActivity activity = (MainActivity) getActivity();
        resultFragment = activity.getResultFragment();
        btnTest = (Button) view.findViewById(R.id.ref_btn_test);
        btnReport = (Button) view.findViewById(R.id.ref_btn_report);
        btnTool = (Button) view.findViewById(R.id.ref_btn_tool);
        btnHelp = (Button) view.findViewById(R.id.ref_btn_help);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state == ViewPager.SCROLL_STATE_IDLE) {
            switch (indicator.getCurrentItem()) {
                case 0:
                    if (dlhgqFragment.getCurrentBtnNum() != -1) {
                        resultFragment.changeRightBtn(dlhgqFragment.getCurrentBtnNum());
                    }
                    break;
                case 1:
                    if (dyhgqFragment.getCurrentBtnNum() != -1) {
                        resultFragment.changeRightBtn(dyhgqFragment.getCurrentBtnNum());
                    }
                    break;
            }
        }
    }

    /**
     * Edittext输入文本监听，正则表达式
     */
    class RegxEditTextFocusListener implements View.OnFocusChangeListener {

        private SharedPreferences.Editor edit;
        private String regx;

        public RegxEditTextFocusListener(SharedPreferences.Editor editor, String regx) {
            this.edit = editor;
            this.regx = regx;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v;
            String text = editText.getText().toString();
            if (hasFocus) {
                editText.setSelection(text.length());
            } else {
                Pattern pattern = Pattern.compile(regx);
                Matcher matcher = pattern.matcher(text);
                boolean matches = matcher.matches();
                switch (editText.getId()) {
                    case R.id.set_ip_edit:
                        if (matches) {
                            edit.putString("ipAddress", ipEditText.getText().toString());
                            edit.commit();
                        } else {
                            String s = configSharePre.getString("ipAddress", "");
                            editText.setText(s);
                        }
                        break;
                    case R.id.set_dk_edit:
                        if (matches) {
                            edit.putString("dkValue", dkEditText.getText().toString());
                            edit.commit();
                        } else {
                            String s = configSharePre.getString("dkValue", "");
                            editText.setText(s);
                        }
                        break;
                }

            }
        }
    }
}
