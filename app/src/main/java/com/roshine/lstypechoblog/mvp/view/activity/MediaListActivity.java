package com.roshine.lstypechoblog.mvp.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.imageloader.ImageLoaderManager;
import com.roshine.lstypechoblog.imageloader.ImageLoaderOptions;
import com.roshine.lstypechoblog.mvp.bean.MediaListBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.MediaListPresenter;
import com.roshine.lstypechoblog.mvp.view.activity.base.MvpBaseActivity;
import com.roshine.lstypechoblog.widget.recyclerview.base.SimpleRecyclertViewAdater;
import com.roshine.lstypechoblog.widget.recyclerview.base.ViewHolder;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnLoadMoreListener;
import com.roshine.lstypechoblog.widget.recyclerview.refreshandload.LSAutoRecyclertView;
import com.roshine.lstypechoblog.widget.recyclerview.refreshandload.SwipeRecyclertView;
import com.roshine.lstypechoblog.utils.LogUtil;
import com.roshine.lstypechoblog.utils.ThemeColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Roshine
 * @date 2017/8/10 13:54
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 媒体文件列表界面
 */
public class MediaListActivity extends MvpBaseActivity<ContractUtil.IMediaListView, MediaListPresenter> implements ContractUtil.IMediaListView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tb_base_tool_bar)
    Toolbar tbBaseToolBar;
    @BindView(R.id.swipe_recyclert_view)
    SwipeRecyclertView swipeRecyclertView;
    private MediaListPresenter mPresenter;
    private int pageSize = 10;
    private int pageNum = 0;
    private int currentPageNum;

    private LSAutoRecyclertView recyclertView;
    private SimpleRecyclertViewAdater<MediaListBean> mAdapter;
    private List<MediaListBean> listDatas = new ArrayList<>();
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }
        super.onCreate(savedInstanceState);
        getListData(pageNum);
    }

    private void getListData(int pageNum) {
        mPresenter.getMediaList(this,pageSize,pageNum);
    }

    @Override
    public MediaListPresenter getPresenter() {
        mPresenter = new MediaListPresenter();
//        getLifecycle().addObserver(mPresenter);
        return mPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_media_list;
    }

    @Override
    protected void initViewData(Bundle savedInstanceState) {
        ivBack.setVisibility(View.VISIBLE);
        tbBaseToolBar.setBackgroundColor(getResources().getColor(ThemeColorUtil.getThemeColor()));
        tvTitle.setText(getResources().getString(R.string.medial_list_title));
        recyclertView = swipeRecyclertView.getRecyclertView();
        swipeRecyclertView.setColorSchemeColors(getResources().getColor(ThemeColorUtil.getThemeColor()));
        swipeRecyclertView.setLoadMoreProgressBarDrawbale(getResources().getDrawable(ThemeColorUtil.getThemeDrawable()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false);
        recyclertView.setLayoutManager(gridLayoutManager);
        mAdapter = new SimpleRecyclertViewAdater<MediaListBean>(this,listDatas,R.layout.item_media_list_layout) {
            @Override
            protected void onBindViewHolder(ViewHolder holder, int itemType, MediaListBean itemBean, int position) {
                imageView = holder.getView(R.id.iv_media);
                ImageLoaderOptions.Builder builder = new ImageLoaderOptions.Builder(imageView,itemBean.getLink());
                ImageLoaderOptions options = builder.asGif(false)
                        .override(screenWidth / 2, screenWidth / 2)
                        .build();
                ImageLoaderManager.getInstance().showImage(options);
            }
        };
        recyclertView.setAdapter(mAdapter);
        swipeRecyclertView.setRefreshing(true);
        swipeRecyclertView.setOnloadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                currentPageNum = ++pageNum ;
                getListData(currentPageNum);
            }

            @Override
            public void onReLoadMore() {
                getListData(currentPageNum);
            }
        });
        swipeRecyclertView.setOnRefreshListener(() -> {
            swipeRecyclertView.setRefreshing(true);
            pageNum = 0;
            getListData(pageNum);
        });
        recyclertView.setOnItemClick((position, holder) -> {
            if(listDatas != null && position < listDatas.size()){
                MediaListBean mediaListBean = listDatas.get(position);
                Intent intent = new Intent(this,PreImageActivity.class);
                intent.putExtra("imgTitle",mediaListBean.getTitle());
                intent.putExtra("imgUrl",mediaListBean.getLink());
//                startActivity(PreImageActivity.class,intent);
//                if(Build.VERSION.SDK_INT >= 16){
                    //第一次进入时使用
//                    getWindow().setExitTransition(new Explode());  //设置页面切换效果
                    //再次进入时使用
//                    getWindow().setReenterTransition(new Explode());

//                    Transition ts = new ChangeClipBounds();         //设置元素动画
//                    ts.setDuration(3000);
//                    getWindow().setSharedElementExitTransition(ts);

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(this, holder.getView(R.id.iv_media), "profile");
                    startActivity(intent, options.toBundle());
//                }else{
//                    startActivity(intent);
//                }
            }
        });
    }

    @Nullable
    @Override
    public void loadSuccess(List<MediaListBean> listDatas) {
        swipeRecyclertView.setRefreshing(false);
        if(pageNum == 0 && this.listDatas != null){
            this.listDatas.clear();
        }
        if (listDatas != null && listDatas.size() > 0) {
            if(listDatas.size() < pageSize){
                LogUtil.showI("Roshine","没有更多哇");
                swipeRecyclertView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
            }else{
                swipeRecyclertView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_SUC);
            }
            this.listDatas.addAll(listDatas);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }else{
            swipeRecyclertView.setLoadMoreFinish(SwipeRecyclertView.LOAD_NO_MORE);
        }
    }

    @Override
    public void loadFail(String message) {
        swipeRecyclertView.setLoadMoreFinish(SwipeRecyclertView.LOAD_MORE_FAIL);
        swipeRecyclertView.setRefreshing(false);
        toast(message);
    }

    @OnClick(R.id.iv_back)
    void onBackClick(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
