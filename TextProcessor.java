public class TextProcessor {
    public static String processText(String content) {
        // 将内容标准化：转小写，替换标点符号和非字母字符为空格
        return content.toLowerCase().replaceAll("[^a-z\\s]", " ");
    }
}
