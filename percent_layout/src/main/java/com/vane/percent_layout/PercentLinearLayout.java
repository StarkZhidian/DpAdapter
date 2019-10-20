package com.vane.percent_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Create by vane on 2019/10/20
 * Email: 1532033525@qq.com
 */
public class PercentLinearLayout extends LinearLayout {

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
        // 如果宽度和高度有一个模式为 WRAP_CONTENT，则使用父类的测量方法，先确定当前类的宽高
        if (widthMode == LayoutParams.WRAP_CONTENT || heightMode == LayoutParams.WRAP_CONTENT) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            width = getMeasuredWidth();
            height = getMeasuredHeight();
        }
        // 开始测量子 View
        int childCount = getChildCount();
        View childView;
        LayoutParams lp;
        int childWidthSpec;
        int childHeightSpec;
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            lp = (LayoutParams) childView.getLayoutParams();
            if (lp.widthPercent != 0) {
                childWidthSpec = getChildMeasureSpec(
                        MeasureSpec.makeMeasureSpec((int) (width * lp.widthPercent), MeasureSpec.EXACTLY),
                        getPaddingLeft() + getPaddingRight(),
                        (int) (width * lp.widthPercent));
            } else {
                childWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight(),
                        lp.width);
            }
            if (lp.heightPercent != 0) {
                childHeightSpec = getChildMeasureSpec(
                        MeasureSpec.makeMeasureSpec((int) (height * lp.heightPercent), MeasureSpec.EXACTLY),
                        getPaddingTop() + getPaddingBottom(),
                        (int) (height * lp.heightPercent));
            } else {
                childHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                                getPaddingTop() + getPaddingBottom(),
                                lp.height);
            }
            measureChild(childView, childWidthSpec, childHeightSpec);
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
            widthPercent = a.getFloat(R.styleable.PercentLinearLayout_Layout_width, 0);
            heightPercent = a.getFloat(R.styleable.PercentLinearLayout_Layout_height, 0);
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
