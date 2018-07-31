package com.roshine.lstypechoblog.mvp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.mvp.bean.ThemeColorBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Roshine
 * @date 2017/7/18 16:04
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 设置主题颜色适配器
 */
public class ThemeColorChooseAdapter extends BaseAdapter {
    private ThemeColorBean[] colors;
    private Context mContext;

    public ThemeColorChooseAdapter(Context context, ThemeColorBean[] colors) {
        mContext = context;
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public Object getItem(int i) {
        return colors[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_theme_color, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.viewThemeColor.setBackgroundResource(colors[position].getColorValue());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.view_theme_color)
        View viewThemeColor;
        @BindView(R.id.iv_theme_color_sure)
        ImageView ivThemeColorSure;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
