package com.vane.percent_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vane.Utils;

/**
 * Author: vane
 * Date: 2019/10/22
 * Describe: 保存布局中百分比的相关信息，这里的百分比取值有两种方式：
 * 1、百分比值为正数，则代表的是相对于布局宽度的值
 * 2、百分之值为负数，则代表的是相对于布局高度的值
 * 这里的正负号只是用来标识相对布局宽度/高度，实际大小是对应的百分的绝对值 * 布局宽/高
 * 同时屏蔽 xml 使用者，使用者通过 比例值w 来表示相对的是布局宽度，比如 0.5w，
 * 通过 比例值h 来表示相对布局高度，比如 0.5h
 */
public class PercentInfo {
    private static final String TAG = "PercentInfo";
    private static final char WIDTH_CHARACTER = 'w';
    private static final char HEIGHT_CHARACTER = 'h';

    public float widthPercent;
    public float heightPercent;
    public float marginPercent;
    public float marginLeftPercent;
    public float marginTopPercent;
    public float marginRightPercent;
    public float marginBottomPercent;
    public float paddingPercent;
    public float paddingLeftPercent;
    public float paddingTopPercent;
    public float paddingRightPercent;
    public float paddingBottomPercent;

    public PercentInfo() {
    }

    public PercentInfo(float widthPercent, float heightPercent) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        Log.d(TAG, "Got widthPercent=[" + widthPercent + "], heightPercent=[" + heightPercent + "]");
    }

    public PercentInfo(@NonNull PercentInfo info) {
        if (info == null) {
            return;
        }
        widthPercent = info.widthPercent;
        heightPercent = info.heightPercent;
    }

    public float getWidthValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, widthPercent);
    }

    public float getHeightValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, heightPercent);
    }

    public float getMarginLeftValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, marginPercent, marginLeftPercent);
    }

    public float getMarginTopValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, marginPercent, marginTopPercent);
    }

    public float getMarginRightValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, marginPercent, marginRightPercent);
    }

    public float getMarginBottomValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, marginPercent, marginBottomPercent);
    }

    public float getPaddingLeftValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, paddingPercent, paddingLeftPercent);
    }

    public float getPaddingTopValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, paddingPercent, paddingTopPercent);
    }

    public float getPaddingRightValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, paddingPercent, paddingRightPercent);
    }

    public float getPaddingBottomValue(int layoutWidth, int layoutHeight) {
        return getDimensionValue(layoutWidth, layoutHeight, paddingPercent, paddingBottomPercent);
    }

    @Override
    public String toString() {
        return Utils.toString(this);
    }

    public static PercentInfo makeFromLayoutAttrs(@NonNull Context context, AttributeSet attrs) {
        PercentInfo info = new PercentInfo();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PercentLayout_Layout);
        int attrsCount = ta.getIndexCount();
        int attr;
        float percentVal;
        for (int i = 0; i < attrsCount; i++) {
            attr = ta.getIndex(i);
            percentVal = getPercentData(ta.getString(attr));
            if (attr == R.styleable.PercentLayout_Layout_width_percent) {
                info.widthPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_height_percent) {
                info.heightPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_margin_percent) {
                info.marginPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_margin_left_percent) {
                info.marginLeftPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_margin_top_percent) {
                info.marginTopPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_margin_right_percent) {
                info.marginRightPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_margin_bottom_percent) {
                info.marginBottomPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_padding_percent) {
                info.paddingPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_padding_left_percent) {
                info.paddingLeftPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_padding_top_percent) {
                info.paddingTopPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_padding_right_percent) {
                info.paddingRightPercent = percentVal;
            } else if (attr == R.styleable.PercentLayout_Layout_padding_bottom_percent) {
                info.paddingBottomPercent = percentVal;
            }
        }
        ta.recycle();
        return info;
    }

    /**
     * 提取字符串中的比例数值，如果是相对宽度的值，返回正数，否则返回等绝对值的负数，默认是相对宽度
     *
     * @param percentStr
     * @return
     */
    private static float getPercentData(String percentStr) {
        if (TextUtils.isEmpty(percentStr)) {
            return 0;
        }
        percentStr = percentStr.trim();
        int len = percentStr.length();
        String floatStr;
        // 符号位，占宽度比为 1 ，高度比为 -1，默认为占宽度比
        int symbol = 1;
        if (len > 0) {
            // 判断是否以 w/h 结尾，例：0.5w, 0.5h
            // 如果既不以 w 也不以 h 结尾，则默认为 w，即占宽度的百分比
            if ((Character.toLowerCase(percentStr.charAt(len - 1))) == WIDTH_CHARACTER) {
                floatStr = percentStr.substring(0, len - 1);
            } else if ((Character.toLowerCase(percentStr.charAt(len - 1))) == HEIGHT_CHARACTER) {
                symbol = -1;
                floatStr = percentStr.substring(0, len - 1);
            } else {
                floatStr = percentStr;
            }
            try {
                return symbol * Float.parseFloat(floatStr);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    private static float getDimensionValue(int layoutWidth, int layoutHeight, @Nullable float... percents) {
        if (layoutWidth <= 0 || layoutHeight <= 0) {
            throw new IllegalArgumentException("layoutWidth and layoutHeight must greater then 0!");
        }
        if (percents != null) {
            for (float percent : percents) {
                if (percent != 0) {
                    return percent > 0 ? layoutWidth * percent : layoutHeight * percent * -1;
                }
            }
        }
        return 0;
    }
}
