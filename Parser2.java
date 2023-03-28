package Parcer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser2 {
//    private static final Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
//    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\"");

//    private static final Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
//    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\"(/?)");

    private static final Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\"(/?)");

    public static StringBuilder parseTags1(String input) {
        Matcher matcher = tagPattern.matcher(input);
        StringBuilder outPut = new StringBuilder(); //вывод строки

        while (matcher.find()) {
            boolean haveAttribute = false;
//            boolean haveValue = false;

            StringBuilder attributes = new StringBuilder(); //вырзанная строка с XML
//            StringBuilder value = new StringBuilder(); //значение между >< не XML

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
            outPut.append("Element:\n" + "path = ")
                    .append(tagName)
                    .append("\n\n");
            } else {
                outPut.append("Element:\n" + "path = ")
                        .append(tagName).append("\n")
                        .append("attributes: \n")
                        .append(attributes);
            }
            StringBuilder parent = new StringBuilder();
            parent.append(tagName).append(", ");
            outPut.append(parseSubTags(tagValue, parent));
        }
        return outPut;
    }

    public static StringBuilder parseSubTags(String input, StringBuilder parent) {
        StringBuilder outPut = new StringBuilder(); //вывод строки
        boolean again = false;
        Matcher matcher = tagPattern.matcher(input);
        {
            while (matcher.find()) {
                boolean haveAttribute = false;
                boolean haveBlankValue = false;

                StringBuilder attributes = new StringBuilder(); //вырзанная строка с XML
//            StringBuilder value = new StringBuilder(); //значение между >< не XML

                String tagName = matcher.group(1);  //извлекаем имя тега
                String attribute = matcher.group(2); //извлекаем атрибуты тэга
                String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега

                Matcher attributeMatcher = attrPattern.matcher(attribute);
                while (attributeMatcher.find()) {
                    haveAttribute = true;
                    String attributeName = attributeMatcher.group(1);
                    String attributeValue = attributeMatcher.group(2);
                    String closes = matcher.group(3); //извлекаем строку между открытием и закрытием тега
                    if (closes.equals("/")) {
                        System.out.println("IS CLOSED");
                    }
                    attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
                }

                if (tagValue.contains("<")) {
                    again = true;
                }

                if (tagValue.equals("")) {
                    again = false;
                    haveBlankValue = true;
                }

                if (!haveAttribute) {
                    outPut.append("Element:\n" + "path = ")
                            .append(parent)
                            .append(tagName)
                            .append("\n");
                } else if (again) {
                    outPut.append("Element:\n" + "path = ")
                            .append(parent)
                            .append(tagName)
                            .append("\n")
                            .append("attributes: \n")
                            .append(attributes)
                            .append("\n");
                } else if (!again && !haveBlankValue) {
                    outPut.append("Element:\n" + "path = ")
                            .append(parent)
                            .append(tagName)
                            .append("\n")
                            .append("value = \"")
                            .append(tagValue)
                            .append("\"\nattributes: \n")
                            .append(attributes)
                            .append("\n");
                } else if (!again && haveBlankValue) {
                    outPut.append("Element:\n" + "path = ")
                            .append(parent)
                            .append(tagName)
                            .append("\n")
                            .append("attributes: \n")
                            .append(attributes)
                            .append("\n");
                }
                if (again) {
                    parent.append(tagName)
                            .append(", ");
                    outPut.append(parseSubTags(tagValue, parent));
                }
            }
        }
        return outPut;
    }
}