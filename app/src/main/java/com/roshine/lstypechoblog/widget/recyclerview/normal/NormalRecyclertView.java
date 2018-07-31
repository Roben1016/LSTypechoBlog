package com.roshine.lstypechoblog.widget.recyclerview.normal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;

import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemLongClickListener;

/**
 * @author Roshine
 * @date 2017/8/3 21:19
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 普通的RecyclerView
 */
public class NormalRecyclertView extends RecyclerView {

    protected NormalRecyclertViewAdapter mAdapter;
    private Adapter mRealAdapter;
    private DataObserver mDataObserver = new DataObserver();

    public NormalRecyclertView(Context context) {
        super(context);
        clearItemAnimator();
    }

    public NormalRecyclertView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        clearItemAnimator();
    }

    public NormalRecyclertView(Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        if (adapter instanceof NormalRecyclertViewAdapter) {
            mAdapter = (NormalRecyclertViewAdapter) adapter;
        } else {
            mAdapter = new NormalRecyclertViewAdapter(getContext(),adapter);
        }
        super.setAdapter(mAdapter);
        mRealAdapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();
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

    private class DataObserver extends AdapterDataObserver{
        @Override
        public void onChanged() {
            if (mAdapter == null) return;
            if (mRealAdapter != mAdapter) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
