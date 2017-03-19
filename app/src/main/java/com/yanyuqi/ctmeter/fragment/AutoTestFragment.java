package com.yanyuqi.ctmeter.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanyuqi.ctmeter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AutoTestFragment extends Fragment {


    public AutoTestFragment() {
        // Required empty public constructor
    }

    public static AutoTestFragment newInstance() {

        Bundle args = new Bundle();

        AutoTestFragment fragment = new AutoTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auto_test, container, false);
        return view;
    }

}
