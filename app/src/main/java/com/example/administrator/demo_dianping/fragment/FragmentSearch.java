package com.example.administrator.demo_dianping.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.demo_dianping.R;

/**
 * 搜索 的Fragment
 */

public class FragmentSearch extends Fragment {

    //创建Fragment的方法
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //将fragment_home.xml布局设置给Fragment
        View view = inflater.inflate(R.layout.fragment_search, null);
        return view;
    }
}
