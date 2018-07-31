package com.roshine.lstypechoblog.widget.recyclerview.refreshandload;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.roshine.lstypechoblog.widget.recyclerview.base.ViewHolder;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemClickListener;
import com.roshine.lstypechoblog.widget.recyclerview.interfaces.OnItemLongClickListener;
import com.roshine.lstypechoblog.utils.LogUtil;

/**
 * @author Roshine
 * @date 2017/8/4 18:24
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 可添加头部和尾部的adapter
 */
public class LSAutoLoadAdapter<T extends RecyclerView.Adapter> extends RecyclerView.Adapter {
    protected T mRealAdapter;
    protected Context mContext;
    protected SparseArrayCompat<View> mHeadViews = new SparseArrayCompat<>();
    protected SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private boolean singleClicFlag = true;//单击事件和长单击事件的屏蔽标识

    private static int BASE_ITEM_TYPE_HEADER = 10000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000;
    private static int ITEM_TYPE_LOAD_MORE = 4;

    private View mLoadMoreView;
    public LSAutoLoadAdapter(Context context, T adapter) {
        super();
        mContext = context;
        mRealAdapter = adapter;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeadViewItemType(viewType)) {
            int headViewPosition = mHeadViews.indexOfKey(viewType);
            View headView = mHeadViews.valueAt(headViewPosition);
            return onCreateHeadAndFootHodler(headView);
        } else if (isFootViewItemType(viewType)) {
            int footViewPosition = mFootViews.indexOfKey(viewType);
            View footView = mFootViews.valueAt(footViewPosition);
            return onCreateHeadAndFootHodler(footView);
        }
        else if (viewType == ITEM_TYPE_LOAD_MORE) {
            return onCreateHeadAndFootHodler(mLoadMoreView);
        } else {
            return mRealAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder realHolder = (ViewHolder)holder;
        if (isHeadViewItemPosition(position)
                || isFootViewItemPosition(position)
                || isLoadMorePosition(position)) {

        } else {
            int realPosition = position - getHeadCount();
            mRealAdapter.onBindViewHolder(holder, realPosition);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    if (singleClicFlag) {
                        mOnItemClickListener.OnItemClick(realPosition,realHolder);
                    }
                    singleClicFlag = true;
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(v -> {
                    mOnItemLongClickListener.onItemLongClick(realPosition,realHolder);
                    singleClicFlag = false;
                    return false;
                });

            }
        }
    }

    //为了解决GridLayoutManager设置头部尾部为整行
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRealAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeadViewItemPosition(position)
                            || isFootViewItemPosition(position)
                            || isLoadMorePosition(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mRealAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeadViewItemPosition(position)
                || isFootViewItemPosition(position)
                || isLoadMorePosition(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) lp;
                layoutParams.setFullSpan(true);
            }
        } else {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) lp;
                layoutParams.setFullSpan(false);
            }
        }
    }

    public void addHeadView(View headView) {
        mHeadViews.put(BASE_ITEM_TYPE_HEADER++, headView);
        int index = mHeadViews.indexOfValue(headView);
        notifyItemInserted(index);
    }

    public void addFootView(View footView) {
        mFootViews.put(BASE_ITEM_TYPE_FOOTER++, footView);
        int index = mFootViews.indexOfValue(footView) + getHeadCount() + getRealCount();
        LogUtil.showI("Roshine","index:"+index+"--"+getHeadCount()+"--"+getRealCount()+"--"+getLoadNoMoreCount());
        notifyItemInserted(index);
    }

    public void addLoadMoreView(View mLoadMoreView) {
        if(mLoadMoreView != null){
            removeLoadMoreView();
        }
        this.mLoadMoreView = mLoadMoreView;
        notifyItemInserted(getHeadCount() + getRealCount() + getFootCount());
    }


    public void removeHeadView(View headView) {
        int index = mHeadViews.indexOfValue(headView);
        if (index < 0) return;
        mHeadViews.removeAt(index);
        notifyItemRemoved(index);
    }

    public void removeFootView(View footView) {
        int index = mFootViews.indexOfValue(footView);
        if (index < 0) return;
        mFootViews.removeAt(index);
        notifyItemRemoved(index + getHeadCount() + getRealCount());
    }

    public void removeLoadMoreView(){
        this.mLoadMoreView = null;
        notifyItemRemoved(getHeadCount() + getRealCount() + getFootCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeadViewItemPosition(position)) {
            return mHeadViews.keyAt(position);
        }
        else if (isLoadMorePosition(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        else if (isFootViewItemPosition(position)) {
            return mFootViews.keyAt(position - getHeadCount() - getRealCount());
        }
        else {
            return mRealAdapter.getItemViewType(position - getHeadCount());
        }
    }

    private RecyclerView.ViewHolder onCreateHeadAndFootHodler(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    //判断是否是头部position
    private boolean isHeadViewItemPosition(int position) {
        return position < getHeadCount();
    }

    //判断是否是尾部position
    private boolean isFootViewItemPosition(int position) {
        boolean flag;
        if(isLoadMorePosition(position)){
            flag = position < getItemCount() - 1;
        }else{
            flag = position < getItemCount();
        }
        return position >= getHeadCount() + getRealCount() && flag;
    }

    private boolean isLoadMorePosition(int position) {
        if (mLoadMoreView != null) {
            return position == getItemCount() - 1;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return getHeadCount() + getFootCount() + getLoadNoMoreCount();
    }

    private int getLoadNoMoreCount() {
        if (mLoadMoreView != null) {
            return 1;
        }
        return 0;
    }

    //获取尾部view数量
    public int getFootCount() {
        return mFootViews.size();
    }

    //获取头部view数量
    public int getHeadCount() {
        return mHeadViews.size();
    }

    //获取实际item数量(除头部和尾部)
    public int getRealCount() {
        return mRealAdapter.getItemCount();
    }

    //判断是否是头部viewType
    private boolean isHeadViewItemType(int viewType) {
        return mHeadViews.indexOfKey(viewType) >= 0;
    }

    //判断是否是尾部viewType
    private boolean isFootViewItemType(int viewType) {
        return mFootViews.indexOfKey(viewType) >= 0;
    }

    //设置单击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //设置长按事件
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}
