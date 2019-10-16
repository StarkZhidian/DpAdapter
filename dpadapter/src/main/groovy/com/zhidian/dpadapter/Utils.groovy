class Utils {

    /**
     * 通过 float 类型的尺寸值转换得到对应的字符串
     * @param dimen
     * @param replaceDotStr 指定替换小数点的字符串，比如对 12.12 操作，此参数传入 _ 结果就为 "12_12"
     * @return
     */
    static String getStrByFloatDimen(float dimen, String replaceDotStr) {
        if (dimen < 0) {
            return ""
        }
        if (replaceDotStr == null || replaceDotStr.isEmpty()) {
            replaceDotStr = "."
        }
        String res = String.valueOf((int) dimen)
        // 判断是否存在小数部分，存在则需要需要处理小数部分
        float floatFix = dimen - (int) dimen
        if (floatFix > 0) {
            while (floatFix - (int) floatFix > 0f) {
                floatFix *= 10
            }
            return res + replaceDotStr + String.valueOf((int) floatFix)
        }
        return res
    }

    /**
     * 将 text 文本写入 destFile 所代表的文件中
     * @param text
     * @param destFile
     */
    static void writeTextToFile(String text, File destFile) {
        if (text == null || text.isEmpty() || destFile == null) {
            return;
        }
        if (!destFile.exists() && !destFile.createNewFile()) {
            System.err.println("Dest file:" + destFile.absolutePath + " create failed!")
            return
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(destFile)
            fw.write(text)
        } catch (IOException e) {
            e.printStackTrace()
        } finally {
            if (fw != null) {
                fw.close()
            }
        }
    }
}