package com.zhidian.dpadapter

import org.gradle.api.Plugin
import org.gradle.api.Project

import java.text.DecimalFormat

/**
 * 自定义的 Gradle 插件，用于生成适配特定屏幕宽度机型（dp）的尺寸资源文件
 */
class DpAdapter implements Plugin<Project> {
//    注册的生成尺寸文件的任务名
    private static final String GENERATE_DIMEN_TASK_NAME = "generateDpx"
//    添加到目标 project 的扩展配置名
    private static final String DP_ADAPTER_EXTENSION_NAME = "dimenGenerateInfo"
//    生成的目标尺寸资源文件夹名 format 格式
    private static final String DIMEN_RESOURCE_DIR_NAME_FORMAT = "values-sw%sdp"
//    尺寸资源文件名
    private static final String DIMEN_FILE_NAME = "dimens.xml"
//    单个尺寸 item 值 format 格式
    private static final String DIMEN_ITEM_FORMAT = "\t<dimen name=\"%s\">%sdp</dimen>\n"
//    浮点数转换成字符串时保留两位小数的 decimal format
    private static final DecimalFormat TWO_DECIMAL_SAVE_FORMAT = new DecimalFormat("0.##")
//    整个 dimens.xml 尺寸资源内容的 format 格式
    private static final String DIMEN_RESOURCE_FORMAT = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n%s</resources>"
//    dimen item 资源名 format 格式，例：<dimen name="xx">12dp</dimen>，指的是里面的 xx
    private static final String DIMEN_ITEM_RESOURCE_NAME_FORMAT = "dpx_%s"

    /**
     * 当依赖该插件的某块 build / apply 等操作时会调用该方法
     * @param project 依赖该插件的模块在 Gradle 中对应的 Project 对象
     */
    @Override
    void apply(Project project) {
//        添加一个名为 dimenGenerateInfo 的额外配置到目标 project 中
        project.extensions.add(DP_ADAPTER_EXTENSION_NAME, ParamsInfo.class)
//        注册一个生成尺寸的任务到目标 Project 中
        project.task(GENERATE_DIMEN_TASK_NAME) {
            doLast {
                ParamsInfo paramsInfo = project[DP_ADAPTER_EXTENSION_NAME]
//                目标工程中对应 dimens.xml 的生成
                generateDimenResource(project, paramsInfo)
            }
        }
    }

    /**
     * 为 project 所在的 module 中生成参数 paramsInfo 所保存信息的尺寸相关文件（values-sw**dp/dimens.xml）
     * @param project
     * @param paramsInfo
     */
    private void generateDimenResource(Project project, ParamsInfo paramsInfo) {
        File targetDir = new File(project.getProjectDir().getAbsolutePath() + "/src/main/res")
        println "Got project resource dir path: " + targetDir
        File dimenDir = new File(targetDir,
                String.format(DIMEN_RESOURCE_DIR_NAME_FORMAT, paramsInfo.screenDp))
        if (!dimenDir.exists() && !dimenDir.mkdir()) {
            System.err.println("Create dimen dir failed!")
            return
        }
//        目标文件夹中 dimens.xml 的生成
        File dimenFile = new File(dimenDir, DIMEN_FILE_NAME)
        generateDimenResource(paramsInfo, dimenFile)
//        默认的 values / dimens.xml 文件的生成 ，
//        不生成默认的 dimens.xml 会有因为手机所有指定宽度尺寸都不匹配而找不到资源发生 crash 的可能性
        File defaultDimenFile = new File(targetDir.getAbsolutePath() + "/values", DIMEN_FILE_NAME)
        ParamsInfo defaultParamsInfo = paramsInfo.clone()
        defaultParamsInfo.screenDp = defaultParamsInfo.designerDp
        generateDimenResource(defaultParamsInfo, defaultDimenFile)
    }

    /**
     * 读取参数指定的 ParamsInfo 中的信息，生成尺寸资源字符串到 destDimenFile 中
     * @param paramsInfo
     * @param destDimenFile
     */
    private void generateDimenResource(ParamsInfo paramsInfo, File destDimenFile) {
        if (paramsInfo == null || destDimenFile == null) {
            System.err.println("Got generate params info or dest dimen file is null!")
            return
        }
        StringBuilder result = new StringBuilder()
        float scaleRatio = (paramsInfo.designerDp == 0 || paramsInfo.screenDp == 0) ? 1f :
                1f * paramsInfo.screenDp / paramsInfo.designerDp
        float start = paramsInfo.startGenerateValue
        float end = paramsInfo.endGenerateValue
        float interval = paramsInfo.generateInterval
        for (float i = start; i <= end; i += interval) {
            result.append(getDimenItem(i, scaleRatio))
        }
        String gotDimenResource = String.format(DIMEN_RESOURCE_FORMAT, result.toString())
        println "Got dimen resource: " + gotDimenResource
        Utils.writeTextToFile(gotDimenResource, destDimenFile)
    }

    /**
     * 生成单个 dimen 资源字符串，例：<dimen name="dpx_1">1dp</dimen>
     * @param dimen 要生成的尺寸值
     * @param scaleRatio 目标机型的屏幕宽度和设计稿宽度比例
     * @return
     */
    private static String getDimenItem(float dimen, float scaleRatio) {
        if (dimen >= 0 && scaleRatio > 0) {
            return String.format(DIMEN_ITEM_FORMAT,
                    String.format(DIMEN_ITEM_RESOURCE_NAME_FORMAT, Utils.getStrByFloatDimen(dimen, "_")),
                    TWO_DECIMAL_SAVE_FORMAT.format(scaleRatio * dimen))
        }
        return ""
    }
}


