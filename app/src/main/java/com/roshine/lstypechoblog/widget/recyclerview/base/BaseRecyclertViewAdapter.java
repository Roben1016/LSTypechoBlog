package com.roshine.lstypechoblog.widget.recyclerview.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/3 20:46
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public abstract class BaseRecyclertViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List listData;
    public BaseRecyclertViewAdapter(Context context,List listData){
        mContext = context;
        this.listData = listData;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = getLayoutIdFromType(viewType);
        return ViewHolder.getViewHolder(mContext,layoutId,parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        onBindViewHolder(holder,position,getItemViewType(position),listData.get(position));
    }

    @Override
    public int getItemCount() {
        if (listData != null) {
            return listData.size();
        }
        return 0;
    }

    //子类实现这两个方法
    protected abstract int getLayoutIdFromType(int itemType);

    protected abstract void onBindViewHolder(ViewHolder holder,int position,int itemType,Object itemBean);

    @Override
    public abstract int getItemViewType(int position);
}
