package com.roshine.lstypechoblog.widget.markdown;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.roshine.lstypechoblog.R;


public class TabIconView extends HorizontalScrollView {

    private LinearLayout mLayout;
    private LayoutInflater mInflater;

    public TabIconView(Context context) {
        super(context);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        this.setHorizontalScrollBarEnabled(false);

        mInflater = LayoutInflater.from(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout = new LinearLayout(getContext());
        mLayout.setPadding(1, 0, 1, 0);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mLayout, params);
    }


    public void addTab(@DrawableRes int iconId, @IdRes int id, OnClickListener onClickListener) {
        ImageButton view = (ImageButton) mInflater.inflate(R.layout.item_tab_icon, mLayout, false);
        view.setImageResource(iconId);
        view.setId(id);
        view.setOnClickListener(onClickListener);
        mLayout.addView(view, mLayout.getChildCount());
        //滑到最右边
        this.postDelayed(() -> this.smoothScrollBy(1000, 0), 5);
    }


    public void removeTab() {
        int count = mLayout.getChildCount();
        //移除最后一个
        mLayout.removeViewAt(count - 1);
    }

}
