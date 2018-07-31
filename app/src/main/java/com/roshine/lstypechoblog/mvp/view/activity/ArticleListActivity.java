package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.bean.ArticleListBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.ArticleListPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.MvpBaseActivity;
import com.roshine.lstypechoblog.utils.DateUtil;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;
import com.roshine.lstypechoblog.widget.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lstypechoblog.widget.recyclerview.base.ViewHolder;
import com.roshine.lstypechoblog.widget.recyclerview.headandfoot.HeadAndFootRecyclertView;
import com.roshine.lstypechoblog.widget.recyclerview.layoutmanager.LSLinearLayoutManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/7/17 23:53
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 我的文章列表界面
 */
public class ArticleListActivity extends MvpBaseActivity<ContractUtil.IArticleListView, ArticleListPresenter> implements ContractUtil.IArticleListView {

    public static final String POST_ID = "postId";
    public static final String BLOG_LIST = "blogList";
    private static final int EDIT_REQUEST_CODE = 101;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.recyclerview)
    HeadAndFootRecyclertView recyclerview;
    @BindView(R.id.swip_refresh_layout)
    SwipeRefreshLayout swipRefreshLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    private ArticleListPresenter articleListPresenter;
    private List<ArticleListBean> articleListBeanList;
    private SimpleRecyclertViewAdater mAdapter;
    private View footView;
    private int articleCount = Integer.MAX_VALUE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDatas(articleCount);
    }

    private void loadDatas(int count) {
        showProgress();
        recyclerview.removeFooterView(footView);
        articleListPresenter.getArticles(this, count);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_list;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        articleListBeanList = new ArrayList<>();
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipRefreshLayout.setColorSchemeColors(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvTitle.setText(this.getString(R.string.my_article_text));
        ivBack.setVisibility(View.VISIBLE);
        footView = LayoutInflater.from(this).inflate(R.layout.articles_bottom_view, null);
        footView.setOnClickListener(v -> {
            //TODO
        });

        LSLinearLayoutManager linearLayoutManager = new LSLinearLayoutManager(LSLinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SimpleRecyclertViewAdater<ArticleListBean>(this, articleListBeanList, R.layout.item_article_list_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, ArticleListBean itemBean, int position) {
                holder.setText(R.id.tv_article_title, itemBean.getTitle());
                holder.setText(R.id.tv_article_date, DateUtil.formatDate(itemBean.getDateCreated()));
                holder.setText(R.id.tv_article_summary, itemBean.getMt_excerpt());
            }
        };
        recyclerview.setAdapter(mAdapter);
        recyclerview.setOnItemClick((position, holder) -> {
            LogUtil.showD("Roshine", "点击的博客id:" + articleListBeanList.get(position).getPostid());
            Bundle bundle = new Bundle();
            bundle.putString(POST_ID, articleListBeanList.get(position).getPostid());
            bundle.putString("enterType",BLOG_LIST);
            startActivityForResult(EditBlogActivity.class, bundle,EDIT_REQUEST_CODE);
        });
//        recyclerview.setOnItemLongClickListener((position,holder) -> {
//            toast("长按:" + position + ":标题:" + articleListBeanList.get(position).getTitle());
//            return false;
//        });
        swipRefreshLayout.setOnRefreshListener(() ->
                loadDatas(articleCount)
        );
    }

    @OnClick(R.id.iv_back)
    void backClick() {
        finish();
    }

    @Override
    public ArticleListPresenter getPresenter() {
        articleListPresenter = new ArticleListPresenter();
//        getLifecycle().addObserver(articleListPresenter);
        return articleListPresenter;
    }

    @Override
    public void loadSuccess(List<ArticleListBean> listReturen) {
        hideProgress();
        articleListBeanList.clear();
        if (listReturen != null && listReturen.size() > 0) {
            articleListBeanList.addAll(listReturen);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
        recyclerview.addFooterView(footView);
    }

//    private View getBottomView() {
//        if(footView == null){
//            footView = LayoutInflater.from(this).inflate(R.layout.articles_bottom_view, null);
//        }
//        return footView;
//    }

    @Override
    public void loadFail(String message) {
        hideProgress();
        toast(message);
    }

    @Override
    public void showProgress() {
        swipRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipRefreshLayout != null && swipRefreshLayout.isRefreshing()) {
            swipRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(EDIT_REQUEST_CODE == requestCode){
            if(RESULT_OK == resultCode){
                loadDatas(articleCount);
            }
        }
    }
}
