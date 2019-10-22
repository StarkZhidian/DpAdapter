package com.vane.percent_layout;

import android.content.Context;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.vane.percent_layout.test", appContext.getPackageName());
    }

    @Test
    public void getScreenSizeInfo() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Log.d(TAG, metrics.toString() + ", densityDpi: " + metrics.densityDpi);
    }

    @Test
    public void testViewUtils() {
        ViewUtils.init(InstrumentationRegistry.getInstrumentation().getContext());
        float matchWidthValue = ViewUtils.dp2px(360, 360);
        Log.d(TAG, "Got px value = [" + matchWidthValue + "]");
    }
}
