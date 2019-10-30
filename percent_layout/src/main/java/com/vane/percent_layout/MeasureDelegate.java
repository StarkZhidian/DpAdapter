package com.vane.percent_layout;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
        Log.d(TAG, "got width: " + width + ", height: " + height +
                ", widthMode: " + widthMode + ", heightMode: " + heightMode);
        printChildViewLayoutParams();
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
        changeChildViewLayoutParams(width, height);
        // 重新构造父布局的宽高 MeasureSpec
        int gotWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int gotHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // 如果调用过 super.onMeasure，则直接调用 measureChildren 方法
        if (isMeasured) {
            viewGroup.callMeasureChildren(gotWidthMeasureSpec, gotHeightMeasureSpec);
        } else {
            viewGroup.callSuperOnMeasure(gotWidthMeasureSpec, gotHeightMeasureSpec);
        }
        // 测量完成后恢复子 View 的布局参数，以免影响下一次 measure
        restoreChildViewLayoutParams();
    }

    /**
     * 根据得到的容器宽高和子 View 的宽高比例更改子 View 的布局参数
     *
     * @param measuredWidth
     * @param measuredHeight
     */
    private void changeChildViewLayoutParams(int measuredWidth, int measuredHeight) {
        int childCount = viewGroup.getChildCount();
        View childView;
        IPercentLayoutParams lp;
        PercentInfo childPercentInfo;
        float childSize;
        float leftSize, topSize, rightSize, bottomSize;
        for (int i = 0; i < childCount; i++) {
            childView = viewGroup.getChildAt(i);
            lp = (IPercentLayoutParams) childView.getLayoutParams();
            childPercentInfo = lp.getPercentInfo();
            if (childPercentInfo == null) {
                continue;
            }
            // 处理宽高
            if ((childSize = childPercentInfo.getWidthValue(measuredWidth, measuredHeight)) > 0) {
                Log.d(TAG, "got child view width: " + childSize);
                lp.setWidth((int) childSize);
            }
            if ((childSize = childPercentInfo.getHeightValue(measuredWidth, measuredHeight)) > 0) {
                Log.d(TAG, "got child view height: " + childSize);
                lp.setHeight((int) childSize);
            }
            // 处理 Margin
            if ((childSize = childPercentInfo.getMarginLeftValue(measuredWidth, measuredHeight)) > 0) {
                lp.setMarginLeft((int) childSize);
            }
            if ((childSize = childPercentInfo.getMarginTopValue(measuredWidth, measuredHeight)) > 0) {
                lp.setMarginTop((int) childSize);
            }
            if ((childSize = childPercentInfo.getMarginRightValue(measuredWidth, measuredHeight)) > 0) {
                lp.setMarginRight((int) childSize);
            }
            if ((childSize = childPercentInfo.getMarginBottomValue(measuredWidth, measuredHeight)) > 0) {
                lp.setMarginBottom((int) childSize);
            }
            // 处理 Padding
            leftSize = filterPaddingValue(
                    childPercentInfo.getPaddingLeftValue(measuredWidth, measuredHeight),
                    childView.getPaddingLeft());
            topSize = filterPaddingValue(
                    childPercentInfo.getPaddingTopValue(measuredWidth, measuredHeight),
                    childView.getPaddingTop());
            rightSize = filterPaddingValue(
                    childPercentInfo.getPaddingRightValue(measuredWidth, measuredHeight),
                    childView.getPaddingRight());
            bottomSize = filterPaddingValue(
                    childPercentInfo.getPaddingBottomValue(measuredWidth, measuredHeight),
                    childView.getPaddingBottom());
            lp.setViewPadding(childView, (int) leftSize, (int) topSize, (int) rightSize, (int) bottomSize);
        }
    }

    /**
     * 恢复子 View 的布局参数，以免影响下一次 measure
     */
    private void restoreChildViewLayoutParams() {
        int childCount = viewGroup.getChildCount();
        IPercentLayoutParams lp;
        for (int i = 0; i < childCount; i++) {
            lp = (IPercentLayoutParams) viewGroup.getChildAt(i).getLayoutParams();
            lp.restoreWidthHeight();
        }
    }

    private void printChildViewLayoutParams() {
        int childCount = viewGroup.getChildCount();
        View childView;
        ViewGroup.LayoutParams lp;
        for (int i = 0; i < childCount; i++) {
            childView = viewGroup.getChildAt(i);
            lp = childView.getLayoutParams();
            Log.d(TAG, "childView: " + childView + ", lp.wdith: " + lp.width + ", lp.height: " + lp.height);
        }
    }

    private float filterPaddingValue(float... paddingValues) {
        if (paddingValues != null) {
            for (float padding : paddingValues) {
                if (padding != 0) {
                    return padding;
                }
            }
        }
        return 0;
    }
}
