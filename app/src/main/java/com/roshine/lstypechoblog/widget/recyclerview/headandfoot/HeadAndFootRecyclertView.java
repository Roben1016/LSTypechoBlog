package com.roshine.lstypechoblog.widget.recyclerview.headandfoot;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.view.View;

import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemLongClickListener;


/**
 * @author Roshine
 * @date 2017/8/4 19:01
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 可添加头部和尾部的RecyclerView
 */
public class HeadAndFootRecyclertView extends RecyclerView {
    private Adapter mRealAdapter;
    private HeadAndFootAdapter mAdapter;
    private final DataObserver mDataObserver = new DataObserver();
    //   当有header和footer的时候，是否显示emptyview
    private boolean showEmptyViewHasHF = false;
    //填充的空view;
    private View mEmptyView;
    //    第一次加载时，如果有覆盖整块的加载中，第一次完成后就需要隐藏
    private View mLoadingView;

    public HeadAndFootRecyclertView(Context context) {
        super(context);
        clearItemAnimator();
    }

    public HeadAndFootRecyclertView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        clearItemAnimator();
    }

    public HeadAndFootRecyclertView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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

    @Override
    public void setAdapter(Adapter adapter) {
        mRealAdapter = adapter;
        if(adapter instanceof HeadAndFootAdapter){
            mAdapter = (HeadAndFootAdapter)adapter;
        }else{
            mAdapter = new HeadAndFootAdapter(getContext(),adapter);
        }
        super.setAdapter(mAdapter);
        mRealAdapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
    }

    private class DataObserver extends AdapterDataObserver{
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            if (mRealAdapter != mAdapter)
                mAdapter.notifyDataSetChanged();
            if (mEmptyView == null) {
                return;
            }
            int itemCount = 0;
            if (!showEmptyViewHasHF) {
                Adapter adapter = getAdapter();
                if (adapter instanceof HeadAndFootAdapter) {
                    itemCount += ((HeadAndFootAdapter) adapter).getHeadCount() + ((HeadAndFootAdapter) adapter).getFootCount();
                }
//                若是下拉刷新，需减掉下拉头
//                if (HeadAndFootRecyclertView.this instanceof PullToRefreshRecyclerView) {
//                    itemCount -= ((PullToRefreshRecyclerView) HeadAndFootRecyclertView.this).getRefreshViewCount();
//                }
            }
            itemCount += mRealAdapter.getItemCount();
            if (itemCount == 0) {
                mEmptyView.setVisibility(VISIBLE);
                if (getVisibility() != INVISIBLE)
                    setVisibility(INVISIBLE);
            } else {
                mEmptyView.setVisibility(GONE);
                if (getVisibility() != VISIBLE)
                    setVisibility(VISIBLE);
            }
        }
    }

    /**获得真正的adapter*/
    public Adapter getRealAdapter() {
        return mRealAdapter;
    }

    /**设置空白view*/
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        mDataObserver.onChanged();
    }

    /**设置空白view*/
    public void setEmptyView(View emptyView,boolean showEmptyViewHasHF) {
        this.mEmptyView = emptyView;
        this.showEmptyViewHasHF = showEmptyViewHasHF;
        mDataObserver.onChanged();
    }

    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    public void setOnItemClick(OnItemClickListener onItemClickListener){
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


    public void addHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the headview must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.addHeadView(view);
        }
    }

    public void addFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the footview must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.addFootView(view);
        }
    }

    public void removeHeaderView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the headview must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.removeHeadView(view);
        }
    }
    public void removeFooterView(View view) {
        if (null == view) {
            throw new IllegalArgumentException("the footview must not be null");
        } else if (mAdapter == null) {
            throw new IllegalStateException("must set an adapter");
        } else {
            mAdapter.removeFootView(view);
        }
    }
}
