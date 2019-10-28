package com.vane.percent_layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PercentFrameLayout extends FrameLayout implements IPercentLayout {

    private MeasureDelegate measureDelegate = new MeasureDelegate(this);

    public PercentFrameLayout(@NonNull Context context) {
        super(context);
    }

    public PercentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PercentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (lp instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) lp);
        }
        return new LayoutParams((FrameLayout.LayoutParams) super.generateLayoutParams(lp));
    }

    @Override
    public void callSuperOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void callMeasureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams implements IPercentLayoutParams {

        private PercentInfo info;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
            info = PercentInfo.makeFromLayoutAttrs(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            info = new PercentInfo();
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
            info = new PercentInfo();
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
            info = new PercentInfo();
        }

        public LayoutParams(@NonNull MarginLayoutParams source) {
            super(source);
            info = new PercentInfo();
        }

        public LayoutParams(@NonNull FrameLayout.LayoutParams source) {
            super(source);
            info = new PercentInfo();
        }

        public LayoutParams(@NonNull LayoutParams source) {
            super(source);
            info = new PercentInfo(source.info);
        }

        public PercentInfo getInfo() {
            return info;
        }

        @Override
        public PercentInfo getPercentInfo() {
            return info;
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
            this.leftMargin = marginLeft;
        }

        @Override
        public void setMarginTop(int marginTop) {
            this.topMargin = marginTop;
        }

        @Override
        public void setMarginRight(int marginRight) {
            this.rightMargin = marginRight;
        }

        @Override
        public void setMarginBottom(int marginBottom) {
            this.bottomMargin = marginBottom;
        }

        @Override
        public void setViewPadding(View view, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            if (view == null) {
                return;
            }
            view.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
    }
}
