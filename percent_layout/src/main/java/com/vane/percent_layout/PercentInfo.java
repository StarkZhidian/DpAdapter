package com.vane.percent_layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;

public class PercentInfo {
    private static final String TAG = "PercentInfo";

    public float widthPercent;
    public float heightPercent;

    public PercentInfo() {
    }

    public PercentInfo(float widthPercent, float heightPercent) {
        this.widthPercent = widthPercent;
        this.heightPercent = heightPercent;
        Log.d(TAG, "Got widthPercent=[" + widthPercent + "], heightPercent=[" + heightPercent + "]");
    }

    public PercentInfo(@NonNull PercentInfo info) {
        if (info == null) {
            return ;
        }
        widthPercent = info.widthPercent;
        heightPercent = info.heightPercent;
    }

    @Override
    public String toString() {
        return "{\"widthPercent\": " + widthPercent + ", \"heightPercent\": " + heightPercent + "}";
    }

    public static PercentInfo makeFromLayoutAttrs(@NonNull Context context, AttributeSet attrs,
                                                  int[] attrsId,
                                                  int widthPercentResId, int heightPercentResId) {
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsId);
        PercentInfo info = new PercentInfo(
                ta.getFloat(widthPercentResId, 0),
                ta.getFloat(heightPercentResId, 0));
        ta.recycle();
        return info;
    }
}
