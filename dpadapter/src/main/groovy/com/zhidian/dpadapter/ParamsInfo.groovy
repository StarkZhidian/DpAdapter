package com.zhidian.dpadapter

/**
 * 输入的屏幕尺寸、设计稿尺寸和需要生成尺寸的相关信息
 */
class ParamsInfo {
    /* 默认的开始生成尺寸值 */
    private static final float DEFAULT_START_GENERATE_VALUE = 0
    /* 默认的结束生成尺寸值 */
    private static final float DEFAULT_END_GENERATE_VALUE = 500
    /* 默认的尺寸生成间隔值 */
    private static final float DEFAULT_GENERATE_INTERVAL = 0.5

    /* 屏幕尺寸宽度 dp 值 */
    int screenDp
    /* 设计稿的尺寸宽度 dp 值 */
    int designerDp
    /* 开始生成值，默认为 0 */
    float startGenerateValue = DEFAULT_START_GENERATE_VALUE
    /* 结束生成值，默认为 500 */
    float endGenerateValue = DEFAULT_END_GENERATE_VALUE
    /* 尺寸生成的值间隔 */
    float generateInterval = DEFAULT_GENERATE_INTERVAL

    ParamsInfo clone() {
        ParamsInfo paramsInfo = new ParamsInfo()
        paramsInfo.screenDp = screenDp
        paramsInfo.designerDp = designerDp
        paramsInfo.startGenerateValue = startGenerateValue
        paramsInfo.endGenerateValue = endGenerateValue
        paramsInfo.generateInterval = generateInterval
        return paramsInfo
    }

    @Override
    String toString() {
        return "screenDp: " + screenDp + ", designerDp: " + designerDp + ", startGenerateValue: " + startGenerateValue + ", endGenerateValue: " + endGenerateValue + ", generateInterval: " + generateInterval
    }
}