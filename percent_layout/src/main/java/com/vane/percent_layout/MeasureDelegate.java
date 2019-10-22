package com.vane.percent_layout;

import android.util.Log;
import android.view.View;

import static android.view.View.MeasureSpec;

public class MeasureDelegate {
    private static final String TAG = "MeasureDelegate";

    private final IPercentLayout viewGroup;

    public MeasureDelegate(IPercentLayout viewGroup) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("MeasureDelegate() called with: viewGroup = [" + viewGroup + "]");
        }
        this.viewGroup = viewGroup;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        boolean isMeasured = false;
        // 如果宽度和高度有一个模式为 AT_MOST，则使用父类的测量方法，先确定 ViewGroup 的宽高
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST ||
                widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED) {
            viewGroup.callSuperOnMeasure(widthMeasureSpec, heightMeasureSpec);
            width = viewGroup.getMeasuredWidth();
            height = viewGroup.getMeasuredHeight();
            isMeasured = true;
        }
        Log.d(TAG, "got width: " + width + ", height: " + height);
        // 根据得到的容器宽高和子 View 的宽高比例更改子 View 的布局参数
        int childCount = viewGroup.getChildCount();
        View childView;
        IPercentLayoutParams lp;
        float percent;
        for (int i = 0; i < childCount; i++) {
            childView = viewGroup.getChildAt(i);
            lp = (IPercentLayoutParams) childView.getLayoutParams();
            if ((percent = lp.getWidthPercent()) > 0) {
                lp.setWidth((int) (percent * width));
            }
            if ((percent = lp.getHeightPercent()) > 0) {
                lp.setHeight((int) (percent * height));
            }
        }
        int gotWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int gotHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // 如果调用过 super.onMeasure，则直接调用 measureChildren 方法
        if (isMeasured) {
            viewGroup.callMeasureChildren(gotWidthMeasureSpec, gotHeightMeasureSpec);
        } else {
            viewGroup.callSuperOnMeasure(gotWidthMeasureSpec, gotHeightMeasureSpec);
        }
    }

}
