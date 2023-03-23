package Parcer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser2 {
    private static final Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\"");

    public static StringBuilder parseTags(String input) {
        Matcher matcher = tagPattern.matcher(input);
        StringBuilder outPut = new StringBuilder(); //вывод строки
        while (matcher.find()) {
            boolean haveAttribute = false;
            boolean haveValue = false;

            StringBuilder attributes = new StringBuilder(); //вырзанная строка с XML
            StringBuilder value = new StringBuilder(); //значение между >< не XML

            String tagName = matcher.group(1);  //извлекаем имя тега
            String attribute = matcher.group(2); //извлекаем атрибуты тэга
            String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега

            Matcher attributeMatcher = attrPattern.matcher(attribute);
            while (attributeMatcher.find()) {
                haveAttribute = true;
                String attributeName = attributeMatcher.group(1);
                String attributeValue = attributeMatcher.group(2);
                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
            }
            if(!haveAttribute){
            outPut.append("Element:\n" +
                    "path = " + tagName + "\n\n");
            } else {
                outPut.append("Element:\n" +
                        "path = " + tagName +
                        "\n" + "attributes: \n" +
                        attributes);
            }
            StringBuilder parent = new StringBuilder();
            parent.append(tagName + ", ");
            outPut.append(parseSubTags(tagValue, parent));
        }
        return outPut;
    }

    public static StringBuilder parseSubTags(String input, StringBuilder parent) {
        StringBuilder outPut = new StringBuilder(); //вывод строки
        boolean again = false;
        Matcher matcher = tagPattern.matcher(input);
        while (matcher.find()) {
            boolean haveAttribute = false;
//            boolean haveValue = false;

            StringBuilder attributes = new StringBuilder(); //вырзанная строка с XML
            StringBuilder value = new StringBuilder(); //значение между >< не XML

            String tagName = matcher.group(1);  //извлекаем имя тега
            String attribute = matcher.group(2); //извлекаем атрибуты тэга
            String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега

            Matcher attributeMatcher = attrPattern.matcher(attribute);
            while (attributeMatcher.find()) {
                haveAttribute = true;
                String attributeName = attributeMatcher.group(1);
                String attributeValue = attributeMatcher.group(2);
                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
            }

            if (tagValue.contains("<")) {
                again = true;
            }

            if(!haveAttribute){
                outPut.append("Element:\n" +
                        "path = " + parent + tagName + "\n");
            } else if(again) {
                outPut.append("Element:\n" +
                        "path = " + parent + tagName +
                        "\n" + "attributes: \n" +
                        attributes + "\n");
            } else if(!again) {
                outPut.append("Element:\n" +
                        "path = " + parent + tagName +
                        "\n" + "value = \"" + tagValue + "\"\nattributes: \n" +
                        attributes + "\n");
            }
            if (again) {
                parent.append(tagName + ", ");
                outPut.append(parseSubTags(tagValue, parent));
            }
        }
        return outPut;
    }
}