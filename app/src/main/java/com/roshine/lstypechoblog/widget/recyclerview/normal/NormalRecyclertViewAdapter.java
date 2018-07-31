package com.roshine.lstypechoblog.widget.recyclerview.normal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.roshine.lstypechoblog.widget.recyclerview.base.ViewHolder;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemLongClickListener;


/**
 * @author Roshine
 * @date 2017/8/4 15:18
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 普通RecyclerView的Adapter
 */
public class NormalRecyclertViewAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter {

    private T mRealAdapter;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private boolean singleClicFlag = true;//单击事件和长单击事件的屏蔽标识

    public NormalRecyclertViewAdapter(Context context, T adapter){
        super();
        mRealAdapter = adapter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (ViewHolder)mRealAdapter.onCreateViewHolder(parent,viewType);
    }

    public T getRealAdapter() {
        return mRealAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        return mRealAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder realHolder = (ViewHolder)holder;
        mRealAdapter.onBindViewHolder(holder, position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                if(singleClicFlag){
                    mOnItemClickListener.OnItemClick(holder.getLayoutPosition(),realHolder);
                }
                singleClicFlag = true;
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                mOnItemLongClickListener.onItemLongClick(holder.getLayoutPosition(),realHolder);
                singleClicFlag = false;
                return false;
            });

        }
    }

    @Override
    public int getItemCount() {
        return mRealAdapter.getItemCount();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
