package com.roshine.lstypechoblog.mvp.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.event.PreBlogDataEvent;
import com.roshine.lstypechoblog.mvp.view.fragment.base.BasePageFragment;
import com.roshine.lstypechoblog.widget.markdown.MarkdownPreviewView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Roshine
 * @date 2017/9/9 19:35
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class PreBlogFragment extends BasePageFragment {

    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.markdownView)
    MarkdownPreviewView markdownView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private boolean isPageFinish;
    private String mContent;
    private String title;


    public static PreBlogFragment newInstance() {
        return new PreBlogFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pre_blog;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        markdownView.setOnLoadingFinishListener(() -> {
            if (!isPageFinish && mContent != null)//
                markdownView.parseMarkdown(mContent, true);
            isPageFinish = true;
        });
    }

    @Override
    protected boolean getEventBusEnable() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor_preview_frag, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPreBlogData(PreBlogDataEvent event) {
        scrollView.setFocusable(false);
        if (event != null) {
            mContent = event.getContent();
            title = event.getTitle();
            tvTitle.setText(title);
            if (isPageFinish) {
                markdownView.parseMarkdown(mContent, true);
                markdownView.setFocusable(false);
            }
        }
    }

    @Override
    public void onPageStart() {
    }

    @Override
    public void onPageEnd() {

    }

    @Override
    public void loadNetData() {
        //包含菜单到所在Activity
        setHasOptionsMenu(true);
    }
}
