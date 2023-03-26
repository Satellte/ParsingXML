//package Parcer;
//
//import java.util.ArrayList;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class Parser {
//    private static final Pattern TAG_PATTERN = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
//    public static final Pattern AttrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s=\\s?\"([^\"]+)\"");
//    public static void parseTags(String input) {
//        Matcher matcher = TAG_PATTERN.matcher(input);
//        ArrayList<StringBuilder> stringList = new ArrayList<>();
//        while (matcher.find()) {
//            boolean haveAttribute = false;
//            boolean haveValue = false;
//            StringBuilder attributes = new StringBuilder();
//            StringBuilder value = new StringBuilder();
//            StringBuilder tagName = new StringBuilder();
//            tagName.append(matcher.group(1));  //извлекаем имя тега
//            String attribute = matcher.group(2); //извлекаем атрибуты тэга
//            String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега
//
//            Matcher attributeMatcher = AttrPattern.matcher(attribute);
//            while (attributeMatcher.find()) {
//                haveAttribute = true;
//                String attributeName = attributeMatcher.group(1);
//                String attributeValue = attributeMatcher.group(2);
//                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
//            }
//
//            if (tagValue.contains("<")) {
//                parseSubTags(tagValue, tagName);
//            } else {
//                haveValue = true;
//                value.append(tagValue);
//            }
//
//            if (haveValue && haveAttribute){
//                stringList.add(new StringBuilder("Element:\n" +
//                        "path = " + tagName +
//                        "\n" + "value = \"" +
//                        value + "\"\nattributes: \n" +
//                        attributes));
//            } else if (haveAttribute && !haveValue) {
//                System.out.println("Element:\n" +
//                        "path = " + tagName +
//                        "\n" + "attributes: \n" +
//                        attributes);
//            } else if (!haveAttribute && haveValue) {
//                System.out.println("Нет атрибутов");
//            } else if (!haveAttribute && !haveValue){
//                System.out.println("Element:\n" +
//                        "path = " + tagName);
//            }
//        }
//    }
//
//    public static void parseSubTags(String input, StringBuilder parentsName) {
//        parentsName.append(", ");
//        Matcher matcher = TAG_PATTERN.matcher(input);
//        while (matcher.find()) {
//            boolean haveAttribute = false;
//            boolean haveValue = false;
//            StringBuilder attributes = new StringBuilder();
//            StringBuilder value = new StringBuilder();
//            String tagName = matcher.group(1);  //извлекаем имя тега
//            String attribute = matcher.group(2); //извлекаем атрибуты тэга
//            String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега
//
//            Matcher attributeMatcher = AttrPattern.matcher(attribute);
//            while (attributeMatcher.find()) {
//                haveAttribute = true;
//                String attributeName = attributeMatcher.group(1);
//                String attributeValue = attributeMatcher.group(2);
//                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
//            }
//
//            if (tagValue.contains("<")) {
//                parentsName.append(tag);
//                parseSubTags(tagValue, parentsName);
//            } else {
//                haveValue = true;
//                value.append(tagValue);
//            }
//
//            if (haveValue && haveAttribute){
//                System.out.println("Element:\n" +
//                        "path = " + parentsName + ", " + tagName +
//                        "\n" + "value = \"" +
//                        value + "\"\nattributes: \n" +
//                        attributes);
//            } else if (haveAttribute && !haveValue) {
//                System.out.println("Element:\n" +
//                        "path = " + parentsName + ", " + tagName +
//                        "\n" + "attributes: \n" +
//                        attributes);
//            } else if (!haveAttribute && haveValue) {
//                System.out.println("Нет атрибутов");
//            } else if (!haveAttribute && !haveValue){
//                System.out.println("Element:\n" +
//                        "path = " + tagName);
//            }
//        }
//
//    }
//}