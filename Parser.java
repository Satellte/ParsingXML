package Parcer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser {
    private static final Pattern tagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>");
    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\">");

    private static final Pattern noValueTagPattern = Pattern.compile("<([a-zA-Z0-9]+)([^>]*)/>");

    public static final Pattern noValuePattern = Pattern.compile("<([a-zA-Z\\s=\"0-9])*/>");


    public static StringBuilder parseTags(String input, ArrayList<String> parentPath) {
        boolean again = false;
        StringBuilder outPut = new StringBuilder();

        Matcher matcher = tagPattern.matcher(input);
        while (matcher.find()) {
            boolean haveBlankValue = false;
            boolean haveAttribute = false;

            StringBuilder attributes = new StringBuilder();

            String tagName = matcher.group(1);  //извлекаем имя тега
            String attribute = matcher.group(2); //извлекаем атрибуты тэга
            String tagValue = matcher.group(3);

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

            if (tagValue.equals("")) {
                again = false;
                haveBlankValue = true;
            }

            if (!haveAttribute) {
                outPut.append("Element:\n" + "path = ")
                        .append(parentPath)
                        .append(tagName)
                        .append("\n");
            } else if (again) {
                outPut.append("Element:\n" + "path = ")
                        .append(parentPath)
                        .append(tagName)
                        .append("\n")
                        .append("attributes: \n")
                        .append(attributes)
                        .append("\n");
            }  else if (!again && !haveBlankValue) {
            outPut.append("Element:\n" + "path = ")
                    .append(parentPath)
                    .append(tagName)
                    .append("\n")
                    .append("value = \"")
                    .append(tagValue)
                    .append("\"\nattributes: \n")
                    .append(attributes)
                    .append("\n");
        } else if (!again && haveBlankValue) {
            outPut.append("Element:\n" + "path = ")
                    .append(parentPath)
                    .append(tagName)
                    .append("\n")
                    .append("attributes: \n")
                    .append(attributes)
                    .append("\n");
        }
            parentPath.add(tagName);
            outPut.append(parseTags(tagValue, parentPath));
            parentPath.remove(tagName);
        }

        Matcher matcher1 = noValueTagPattern.matcher(input);
        while (matcher1.find()){
            StringBuilder attributes = new StringBuilder();
            boolean haveAttribute = false;

            String tagName = matcher1.group(1);  //извлекаем имя тега
            String attribute = matcher1.group(2); //извлекаем атрибуты тэга

            Matcher attributeMatcher1 = noValuePattern.matcher(attribute);
            while (attributeMatcher1.find()) {
                haveAttribute = true;
                String attributeName = attributeMatcher1.group(1);
                String attributeValue = attributeMatcher1.group(2);
                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
            }
            if(!haveAttribute){
                outPut.append("Element:\n" + "path = ")
                        .append(parentPath)
                        .append(tagName)
                        .append("\nvalue = null")
                        .append("\n\n");
            } else {
                outPut.append("Element:\n" + "path = ")
                        .append(parentPath)
                        .append(tagName)
                        .append("\nvalue = null")
                        .append("\nattributes: \n")
                        .append(attributes);
            }
        }
        return outPut;
    }
}