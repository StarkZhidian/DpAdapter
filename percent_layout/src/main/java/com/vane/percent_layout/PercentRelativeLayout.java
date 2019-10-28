package com.vane.percent_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class PercentRelativeLayout extends RelativeLayout implements IPercentLayout {

    private MeasureDelegate measureDelegate = new MeasureDelegate(this);

    public PercentRelativeLayout(Context context) {
        super(context);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PercentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    /**
     * 布局参数
     */
    public static class LayoutParams extends RelativeLayout.LayoutParams implements IPercentLayoutParams {

        private PercentInfo percentInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            percentInfo = PercentInfo.makeFromLayoutAttrs(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
            percentInfo = new PercentInfo();
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
            percentInfo = new PercentInfo();
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
            percentInfo = new PercentInfo();
        }

        public LayoutParams(RelativeLayout.LayoutParams source) {
            super(source);
            percentInfo = new PercentInfo();
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            percentInfo = new PercentInfo(source.percentInfo);
        }

        @Override
        public PercentInfo getPercentInfo() {
            return percentInfo;
        }

        @Override
        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public void setMarginLeft(int marginLeft) {
            leftMargin = marginLeft;
        }

        @Override
        public void setMarginTop(int marginTop) {
            topMargin = marginTop;
        }

        @Override
        public void setMarginRight(int marginRight) {
            rightMargin = marginRight;
        }

        @Override
        public void setMarginBottom(int marginBottom) {
            bottomMargin = marginBottom;
        }

        @Override
        public void setViewPadding(View view, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
    }
}
