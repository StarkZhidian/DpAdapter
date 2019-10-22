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
public class PercentLinearLayout extends LinearLayout implements IPercentLayout {
    private static final String TAG = "PercentLinearLayout";

    private MeasureDelegate measureDelegate = new MeasureDelegate(this);

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
        measureDelegate.onMeasure(widthMeasureSpec, heightMeasureSpec);
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

    @Override
    public void callSuperOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void callMeasureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams implements IPercentLayoutParams {

        private PercentInfo percentInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            percentInfo = PercentInfo.makeFromLayoutAttrs(c, attrs, R.styleable.PercentLayout_Layout,
                    R.styleable.PercentLayout_Layout_width_percent,
                    R.styleable.PercentLayout_Layout_height_percent);
        }

        public LayoutParams(int width, int height) {
            this(width, height, 0);
        }

        public LayoutParams(int width, int height, float weight) {
            super(width, height, weight);
        }

        public LayoutParams(LinearLayout.LayoutParams source) {
            super(source);
            percentInfo = new PercentInfo();
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            percentInfo = new PercentInfo(source.getPercentInfo());
        }

        @Override
        public PercentInfo getPercentInfo() {
            return percentInfo;
        }

        @Override
        public float getWidthPercent() {
            return getPercentInfo().widthPercent;
        }

        @Override
        public float getHeightPercent() {
            return getPercentInfo().heightPercent;
        }

        @Override
        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }
}
