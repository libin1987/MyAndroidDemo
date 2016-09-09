package com.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {


    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
    }

    public MyScrollView(Context context) {
            super(context);
    }
    
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            
    return false;
    }


}
