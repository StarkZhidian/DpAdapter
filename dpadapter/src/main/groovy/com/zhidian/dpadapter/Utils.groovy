

class Utils {

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