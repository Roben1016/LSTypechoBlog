package com.roshine.lstypechoblog.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.view.activity.ArticleListActivity;
import com.roshine.lstypechoblog.mvp.view.activity.MediaListActivity;
import com.roshine.lstypechoblog.mvp.view.fragment.base.BaseFragment;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Roshine
 * @date 2017/7/27 18:36
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc Fragment 我的
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar toolbar;
    @BindView(R.id.rl_my_articles)
    RelativeLayout rlMyArticles;
    @BindView(R.id.rl_my_pages)
    RelativeLayout rlMyPages;
    @BindView(R.id.rl_my_medias)
    RelativeLayout rlMyMedias;
    @BindView(R.id.rl_my_comments)
    RelativeLayout rlMyComments;
    private String title;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        tvTitle.setText(title);
        toolbar.setBackgroundColor(getActivity().getResources().getColor(ThemeColorUtil.getThemeColor()));
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
    @OnClick(R.id.rl_my_articles)
    void onArticleClick(){
        startActivity(ArticleListActivity.class);
    }

    @OnClick(R.id.rl_my_medias)
    void onMediaClick(){
        startActivity(MediaListActivity.class);
    }
}

