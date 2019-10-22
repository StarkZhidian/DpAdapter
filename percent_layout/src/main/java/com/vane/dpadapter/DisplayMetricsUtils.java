package com.vane.dpadapter;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class DisplayMetricsUtils {

    private static final int DEFAULT_DESIGNER_SCREEN_WIDTH_DP = 360;

    public static void adjustDisplayMetricsDensity(@NonNull Context context,
                                            int designerScreenWidthDp) {
        if (context == null) {
            return ;
        }
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float beforeDensity = metrics.density;
        metrics.density = 1f * metrics.widthPixels / designerScreenWidthDp;
        // metrics.scaledDensity 在进行 sp 转换为 px 时会用到，一般是和 metrics.density 相等
        // 因为用户在调节系统字体大小时这个值会改变，因此这里需要等比例缩放，不能直接赋值为 metrics.density
        metrics.scaledDensity *= (metrics.density / beforeDensity);
    }

    public static void adjustDisplayMetricsDensity(@NonNull Context context) {
        adjustDisplayMetricsDensity(context, DEFAULT_DESIGNER_SCREEN_WIDTH_DP);
    }
}
