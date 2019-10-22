package com.vane.percent_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Create by vane on 2019/10/20
 * Email: 1532033525@qq.com
 */
public class PercentLinearLayout extends LinearLayout {
    private static final String TAG = "PercentLinearLayout";

    public PercentLinearLayout(Context context) {
        super(context);
    }

    public PercentLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PercentLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        boolean isMeasured = false;
        // 如果宽度和高度有一个模式为 AT_MOST，则使用父类的测量方法，先确定当前 ViewGroup 的宽高
        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST ||
                widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            isMeasured = true;
        }
        Log.d(TAG, "got width: " + width + ", height: " + height);
        // 根据得到的容器宽高和子 View 的宽高比例更改子 View 的布局参数
        int childCount = getChildCount();
        View childView;
        LayoutParams lp;
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            lp = (LayoutParams) childView.getLayoutParams();
            if (lp.widthPercent > 0) {
                lp.width = (int) (width * lp.widthPercent);
            }
            if (lp.heightPercent > 0) {
                lp.height = (int) (height * lp.heightPercent);
            }
        }
        int gotWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int gotHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // 如果调用过 super.onMeasure，则直接调用 measureChildren 方法
        if (isMeasured) {
            measureChildren(gotWidthMeasureSpec, gotHeightMeasureSpec);
        } else {
            super.onMeasure(gotWidthMeasureSpec, gotHeightMeasureSpec);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) lp);
        }
        return new LayoutParams(super.generateLayoutParams(lp));
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {

        public float widthPercent;
        public float heightPercent;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.PercentLinearLayout_Layout);
            widthPercent = a.getFloat(R.styleable.PercentLinearLayout_Layout_width_percent, 0);
            heightPercent = a.getFloat(R.styleable.PercentLinearLayout_Layout_height_percent, 0);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            this(width, height, 0);
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
        }

        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            widthPercent = source.widthPercent;
            heightPercent = source.heightPercent;
        }
    }
}
