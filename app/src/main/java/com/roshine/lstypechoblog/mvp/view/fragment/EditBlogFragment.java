package com.roshine.lstypechoblog.mvp.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.event.ArticleDetailReceivedEvent;
import com.roshine.lstypechoblog.event.PreBlogDataEvent;
import com.roshine.lstypechoblog.event.PreBlogSelectedEvent;
import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.EditFragmentPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.ArticleListActivity;
import com.roshine.lstypechoblog.mvp.view.fragment.base.BasePageFragment;
import com.roshine.lstypechoblog.mvp.view.fragment.base.MvpBaseFragment;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ToastUtil;
import com.roshine.lstypechoblog.widget.markdown.PerformEditable;
import com.roshine.lstypechoblog.widget.markdown.PerformInputAfter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import ren.qinc.edit.PerformEdit;

/**
 * @author Roshine
 * @date 2017/9/9 16:10
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditBlogFragment extends MvpBaseFragment<ContractUtil.IEditFragmentView,EditFragmentPresenter> implements ContractUtil.IEditFragmentView {

    @BindView(R.id.title)
    EditText mName;
    @BindView(R.id.content)
    EditText mContent;
    private PerformEditable mPerformEditable;
    private PerformEdit mPerformEdit;
    private PerformEdit mPerformNameEdit;
    private String enterType;//从哪里进入  "blogList"  博客列表;
    private String postId;
    private ArticleDetailBean articleDetailBean;

    public static EditBlogFragment newInstance() {
        return new EditBlogFragment();
    }

    private MenuItem mActionSave;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_blog;
    }

    @Override
    protected boolean getEventBusEnable() {
        return true;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor_frag, menu);
        mActionSave = menu.findItem(R.id.action_save);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share://分享
//                shareMenu();
                toast("分享");
                return true;
            case R.id.action_undo://撤销
                mPerformEdit.undo();
                return true;
            case R.id.action_redo://重置
                mPerformEdit.redo();
                return true;
            case R.id.action_save://保存
                saved();
//                mPresenter.save(mName.getText().toString().trim(), mContent.getText().toString().trim());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PerformEditable getPerformEditable() {
        return mPerformEditable;
    }

    public void noSave() {
        if (mActionSave == null) return;
        mActionSave.setIcon(R.drawable.ic_action_unsave);
    }

    public void saved() {
        if (mActionSave == null) return;
        if(TextUtils.isEmpty(mContent.getText()) || TextUtils.isEmpty(mName.getText())){
            ToastUtil.showShort(getActivity().getResources().getString(R.string.no_content_title));
            return;
        }
//        if(ArticleListActivity.BLOG_LIST.equals(enterType)){//如果从博客列表进入
            if (mPresenter != null) {
                if (articleDetailBean != null) {
                    showProgress(getActivity().getResources().getString(R.string.updating));
                } else {
                    showProgress(getActivity().getResources().getString(R.string.submit_bloging));
                }
                mPresenter.postEditBlog(getActivity(),mName.getText().toString(),mContent.getText().toString(),articleDetailBean);
//            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveBlogDatas(ArticleDetailReceivedEvent event) {
        if (event != null) {
            articleDetailBean = event.getArticleDetailBean();
            String title = articleDetailBean.getTitle();
            String content = articleDetailBean.getDescription() + articleDetailBean.getMt_text_more();
            mPerformNameEdit.setDefaultText(title);
            mPerformEdit.setDefaultText(content);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelectedPreBlog(PreBlogSelectedEvent event) {
        if (event != null) {
            if(event.isHasSelectedPre()){
                EventBus.getDefault().post(new PreBlogDataEvent(mName.getText().toString(), mContent.getText().toString()));
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
        setHasOptionsMenu(true);
        onCreateAfter();
    }

    private void onCreateAfter() {
        enterType = getActivity().getIntent().getStringExtra("enterType");
        postId = getActivity().getIntent().getStringExtra(ArticleListActivity.POST_ID);
        //代码格式化或者插入操作
        mPerformEditable = new PerformEditable(mContent);
        //撤销和恢复初始化
        mPerformEdit = new PerformEdit(mContent) {
            @Override
            protected void onTextChanged(Editable s) {
                //文本改变
//                mPresenter.textChange();
                noSave();
            }
        };
        mPerformNameEdit = new PerformEdit(mName) {
            @Override
            protected void onTextChanged(Editable s) {
                //文本改变
//                mPresenter.textChange();
                noSave();
            }
        };

        //文本输入监听(用于自动输入)
        PerformInputAfter.start(mContent);
    }

    @Nullable
    @Override
    public void loadSuccess(String datas) {
        hideProgress();
        ToastUtil.showShort(getActivity().getResources().getString(R.string.update_blog_success));
        mActionSave.setIcon(R.drawable.ic_action_save);
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void loadFail(String message) {
        hideProgress();
        ToastUtil.showShort(getActivity().getResources().getString(R.string.update_blog_fail));
    }

    @Override
    public EditFragmentPresenter getPresenter() {
        return new EditFragmentPresenter();
    }
}
