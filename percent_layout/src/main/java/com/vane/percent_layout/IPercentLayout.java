package com.vane.percent_layout;

import android.view.View;

public interface IPercentLayout {

    void callSuperOnMeasure(int widthMeasureSpec, int heightMeasureSpec);

    void callMeasureChildren(int widthMeasureSpec, int heightMeasureSpec);

    int getMeasuredWidth();

    int getMeasuredHeight();

    int getChildCount();

    View getChildAt(int index);
}
