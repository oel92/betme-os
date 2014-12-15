package com.example.betme;

import android.content.Context;
import android.view.View.MeasureSpec;
import android.widget.ExpandableListView;

public class CustExpListview extends ExpandableListView {
	 
    int intGroupPosition, intChildPosition, intGroupid;

    public CustExpListview(Context context) {
           super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
           widthMeasureSpec = MeasureSpec.makeMeasureSpec(960,
                        MeasureSpec.AT_MOST);
           heightMeasureSpec = MeasureSpec.makeMeasureSpec(600,
                        MeasureSpec.AT_MOST);
           super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}