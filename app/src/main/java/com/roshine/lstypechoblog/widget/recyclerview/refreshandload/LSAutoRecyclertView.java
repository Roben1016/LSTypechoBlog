package com.roshine.lstypechoblog.widget.recyclerview.refreshandload;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.widget.recyclerview.headandfoot.HeadAndFootAdapter;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.AutoLoadMoreListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemLongClickListener;
import com.roshine.lstypechoblog.utils.LogUtil;

/**
 * @author Roshine
 * @date 2017/8/8 15:48
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class LSAutoRecyclertView extends RecyclerView {
    public static final int LOAD_MORE_SUC = 20;
    public static final int LOAD_MORE_FAIL = 21;
    public static final int LOAD_NO_MORE = 22;
    private HeadAndFootAdapter mAdapter;
    private Adapter mRealAdapter;
    private boolean loadMoreEnable = true;

    private boolean hasLoadingMore;

    private AutoLoadMoreListener mLoadMoreListener;
    private final DataObserver mDataObserver = new DataObserver();
    private View mLoadMoreView;
    private TextView mTvLoadMore;
    private ProgressBar mPbLoadMore;
    private Drawable pbDrawable;

    public boolean getHasMore() {
        return hasMore;
    }

    public boolean getLoadFailed() {
        return loadFailed;
    }

    public void setLoadFailed(boolean loadFailed) {
        this.loadFailed = loadFailed;
    }

    private boolean hasMore = true;
    private boolean loadFailed = false;
    public LSAutoRecyclertView(Context context) {
        this(context,null);
//        clearItemAnimator();
    }

    public LSAutoRecyclertView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
//        clearItemAnimator();
    }

    public LSAutoRecyclertView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        clearItemAnimator();
    }

    private void clearItemAnimator() {
        ItemAnimator itemAnimator = getItemAnimator();
        itemAnimator.setAddDuration(0);
        itemAnimator.setRemoveDuration(0);
        itemAnimator.setChangeDuration(0);
        itemAnimator.setMoveDuration(0);
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
    }
    private void initLoadMoreView() {
        if (mLoadMoreView == null) {
            mLoadMoreView = LayoutInflater.from(getContext()).inflate(R.layout
                    .glorious_recyclerview_layout_load_more, this,false);
        }
//        mLoadMoreView.setBackgroundColor(mLoadMoreBackgroundColor);
        mTvLoadMore = (TextView) mLoadMoreView.findViewById(R.id.tv_loading_more);
        mPbLoadMore = (ProgressBar) mLoadMoreView.findViewById(R.id.pb_loading_more);
        if (pbDrawable != null) {
            mPbLoadMore.setIndeterminateDrawable(pbDrawable);
        }
        mLoadMoreView.setOnClickListener(v -> {
            if(loadFailed && mLoadMoreListener != null){
                loadFailed = false;
                hasLoadingMore = true;
                mTvLoadMore.setText(getContext().getString(R.string.glorious_recyclerview_loading_more));
                mPbLoadMore.setVisibility(View.VISIBLE);
                mLoadMoreListener.onReLoadMore();
            }
        });
//        if (null != mLoadMorePbIndeterminateDrawable) {
//            mPbLoadMore.setIndeterminateDrawable(mLoadMorePbIndeterminateDrawable);
//        }
//        mTvLoadMore.getPaint().setTextSize(mLoadMoreTextSize);
//        mTvLoadMore.setTextColor(mLoadMoreTextColor);
//        mTvLoadMore.setText(mLoadingMoreText);

        //Add reload strategy if load more failed
//        mLoadMoreView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!hasLoadingMore) {
//                    hasLoadingMore = true;
//                    mPbLoadMore.setVisibility(VISIBLE);
//                    mTvLoadMore.setText(mLoadingMoreText);
//                    mTvLoadMore.setVisibility(VISIBLE);
//                    mLoadMoreListener.onLoadMore();
//                }
//            }
//        });

        //when remove footerView will trigger onCreateViewHolder()
//        if (!TextUtils.isEmpty(mLastPbTvLoadMoreText)) {
//            mTvLoadMore.setText(mLastPbTvLoadMoreText);
//            mPbLoadMore.setVisibility(mLastPbLoadMoreVisibility);
//        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mRealAdapter = adapter;
        if (adapter instanceof LSAutoLoadAdapter) {
            mAdapter = (HeadAndFootAdapter) adapter;
        } else {
            mAdapter = new HeadAndFootAdapter(getContext(), adapter);
        }
        super.setAdapter(mAdapter);
//        initLoadMoreView();
        mRealAdapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
        initLoadMoreView();
    }

    /**
     * 获得真正的adapter
     */
    public Adapter getRealAdapter() {
        return mRealAdapter;
    }

    private class DataObserver extends AdapterDataObserver {


        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            if (mRealAdapter != mAdapter)
                mAdapter.notifyDataSetChanged();
//            if (mEmptyView == null) {
//                return;
//            }
        }
    }
    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!hasLoadingMore && loadMoreEnable && hasMore && !loadFailed && dy > 0) {
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition;
                if (layoutManager instanceof GridLayoutManager) {
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                    ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                    lastVisibleItemPosition = findMax(into);
                } else {
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                }
                if (layoutManager.getChildCount() > 0
                        && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                        && layoutManager.getItemCount() >= layoutManager.getChildCount()) {
                    if (mLoadMoreListener != null) {
                        hasLoadingMore = true;
                        loadFailed = false;
                        if (mPbLoadMore != null && mTvLoadMore != null) {
                            mPbLoadMore.setVisibility(View.VISIBLE);
                            mTvLoadMore.setText(getContext().getResources().getString(R.string.glorious_recyclerview_loading_more));
                        }
                        mAdapter.addFootView(mLoadMoreView);
                        mLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    };
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Called this also means that loadMore enabled
     *
     * @param loadMoreListener loadMoreListener
     */
    public void setLoadMoreListener(final AutoLoadMoreListener loadMoreListener) {
        if (null != loadMoreListener) {
            mLoadMoreListener = loadMoreListener;
            this.addOnScrollListener(mOnScrollListener);
        }
    }

    public void setLoadMoreFinish(int status) {
        hasLoadingMore = false;
        hasMore = true;
        loadFailed = false;
        switch (status) {
            case LOAD_MORE_SUC:
                LogUtil.showI("Roshine","移除没有更多");
                mAdapter.removeFootView(mLoadMoreView);
                break;
            case LOAD_MORE_FAIL:
                loadFailed = true;
                mTvLoadMore.setText(getContext().getString(R.string.load_failed_bottom));
                mPbLoadMore.setVisibility(View.GONE);
//                mAdapter.removeFootView(mLoadMoreView);
                break;
            case LOAD_NO_MORE:
                hasMore = false;
                LogUtil.showI("Roshine","没有更多"+hasMore);
                mAdapter.removeFootView(mLoadMoreView);
                mTvLoadMore.setText(getContext().getString(R.string.no_more_text));
                mPbLoadMore.setVisibility(View.GONE);
                mAdapter.addFootView(mLoadMoreView);
                break;
            default:
                mAdapter.removeFootView(mLoadMoreView);
                break;
        }
    }

    /**
     * @param view headerView
     */
    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the headview must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.addHeadView(view);
        }
    }

    /**
     * @param view footerView
     */
    public void addFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the footview to add must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            if(hasLoadingMore){
                removeFooterView(mLoadMoreView);
                mAdapter.addFootView(view);
            }else{
                mAdapter.addFootView(view);
            }
        }
    }

    /**
     */
    public void removeHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the headview to remove must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.removeHeadView(view);
        }
    }

    /**
     */
    public void removeFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the footview to remove must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.removeFootView(view);
        }
    }

    public void setOnItemClick(OnItemClickListener onItemClickListener) {
        if (null == mAdapter) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (null == mAdapter) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.setOnItemLongClickListener(onItemLongClickListener);
        }
    }

    public void setLoadMoreEnable(boolean loadEnable){
        this.loadMoreEnable = loadEnable;
    }

    public void setHasMore(boolean hasMore){
        LogUtil.showI("Roshine","设置hasMore为"+hasMore);
        this.hasMore = hasMore;
    }

    public void setLoadMoreFail(boolean loadFailed){
        this.loadFailed =loadFailed;
    }
    public boolean getHasLoadingMore() {
        return hasLoadingMore;
    }

    public void setHasLoadingMore(boolean hasLoadingMore) {
        this.hasLoadingMore = hasLoadingMore;
    }
    public void setProgressBarTheme(Drawable drawable){
        if (drawable != null ) {
            pbDrawable = drawable;
            if(mPbLoadMore != null){
                mPbLoadMore.setIndeterminateDrawable(drawable);
            }
        }
    }
}
