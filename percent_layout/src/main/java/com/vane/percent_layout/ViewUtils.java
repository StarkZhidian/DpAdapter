package com.vane.percent_layout;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Create by vane on 2019/10/21
 * Email: 1532033525@qq.com
 */
public class ViewUtils {
    private static final String TAG = "ViewUtils";
    private static Context app;

    public static void init(Context app) {
        if (app == null) {
            throw new IllegalArgumentException("init() called with: app = [" + app + "]");
        }
        ViewUtils.app = app;
    }

    public static float dp2px(float dpValue, int designerScreenWidth) {
        Log.d(TAG, "dp2px() called with: dpValue = [" + dpValue +
                "], designerScreenWidth = [" + designerScreenWidth + "]");
        if (dpValue < 0 || designerScreenWidth <= 0) {
            return 0;
        }
        if (app == null) {
            throw new IllegalArgumentException("Got context is null, are you call init method rightly?");
        }
        DisplayMetrics metrics = app.getResources().getDisplayMetrics();
        float pxValue = dpValue * metrics.widthPixels / designerScreenWidth;
        Log.d(TAG, "got pxValue = [" + pxValue + "]");
        return pxValue;
    }
}
