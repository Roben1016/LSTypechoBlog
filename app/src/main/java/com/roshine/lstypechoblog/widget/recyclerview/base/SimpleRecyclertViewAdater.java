package com.roshine.lstypechoblog.widget.recyclerview.base;

import android.content.Context;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/3 21:23
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public abstract class SimpleRecyclertViewAdater<T> extends BaseRecyclertViewAdapter {
    private int mLayoutId;
    public SimpleRecyclertViewAdater(Context context, List<T> listData, int layoutId) {
        super(context, listData);
        mLayoutId = layoutId;
    }

    @Override
    protected int getLayoutIdFromType(int itemType) {
        return mLayoutId;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, int itemType, Object itemBean) {
        onBindViewHolder(holder,itemType,(T)itemBean,position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    //子类重写该方法
    protected abstract void onBindViewHolder(ViewHolder holder,int itemType, T itemBean, int position);
}
