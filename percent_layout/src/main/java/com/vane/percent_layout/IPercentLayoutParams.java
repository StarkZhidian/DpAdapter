package com.vane.percent_layout;

import android.view.View;

public interface IPercentLayoutParams {

    PercentInfo getPercentInfo();

    void setWidth(int width);

    void setHeight(int height);

    void restoreWidthHeight();

    void setMarginLeft(int marginLeft);

    void setMarginTop(int marginTop);

    void setMarginRight(int marginRight);

    void setMarginBottom(int marginBottom);

    void setViewPadding(View view, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);
}
