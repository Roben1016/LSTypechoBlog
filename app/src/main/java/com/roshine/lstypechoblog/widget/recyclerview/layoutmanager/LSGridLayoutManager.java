package com.roshine.lstypechoblog.widget.recyclerview.layoutmanager;

import android.content.Context;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

public class LSGridLayoutManager extends StaggeredGridLayoutManager {

    public LSGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setGapStrategy(GAP_HANDLING_NONE );
    }

    private LSGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    public LSGridLayoutManager(int spanCount) {
        super(spanCount,VERTICAL);
        setGapStrategy(GAP_HANDLING_NONE);
    }

    public LSGridLayoutManager(int spanCount, int orientation,
                               boolean reverseLayout) {
        super(spanCount, orientation);
        setReverseLayout(reverseLayout);
        setGapStrategy(GAP_HANDLING_NONE );
    }

}
