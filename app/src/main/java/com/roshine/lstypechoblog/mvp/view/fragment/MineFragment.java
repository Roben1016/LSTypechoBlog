package com.roshine.lstypechoblog.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.utils.ActivityUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

/**
 * @author Roshine
 * @date 2017/7/27 18:36
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc Fragment 我的
 */
public class MineFragment extends Fragment {
    private Toolbar toolbar;
    private TextView tvTitle;
    private String title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.tb_base_tool_bar);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    public static MineFragment newInstance(Bundle bundle) {
        Bundle args = new Bundle();
        args.putBundle("bundle", bundle);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            Bundle bundle = arguments.getBundle("bundle");
            title = bundle.getString("title");
        }
    }
}
