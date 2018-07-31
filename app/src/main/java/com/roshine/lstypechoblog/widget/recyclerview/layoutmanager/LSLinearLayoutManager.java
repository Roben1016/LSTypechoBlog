package com.roshine.lstypechoblog.widget.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class LSLinearLayoutManager extends StaggeredGridLayoutManager {


    public LSLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setGapStrategy(GAP_HANDLING_NONE);
    }

    private LSLinearLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public LSLinearLayoutManager() {
        super(1,VERTICAL);
        setGapStrategy(GAP_HANDLING_NONE);
    }

    public LSLinearLayoutManager(int orientation) {
        super(1,orientation);
        setGapStrategy(GAP_HANDLING_NONE);
    }

    public LSLinearLayoutManager(int orientation, boolean reverseLayout) {
        super(1,orientation);
        setReverseLayout(reverseLayout);
        setGapStrategy(GAP_HANDLING_NONE );
    }



}
