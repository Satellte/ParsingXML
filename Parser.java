package Parcer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser {

    private static final Pattern tagPattern = Pattern
            .compile("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>|<([a-zA-Z0-9]+)([^>]*)/>");
    public static final Pattern attrPattern = Pattern.compile("([a-zA-Z0-9]+)\\s?=\\s?\"([^\"]+)\"");

    public static StringBuilder parseTags(String input, ArrayList<String> parentPath) {
        boolean again = false;

        StringBuilder outPut = new StringBuilder();

        Matcher matcher = tagPattern.matcher(input);
        while (matcher.find()) {
            boolean haveBlankValue = false;
            boolean haveAttribute = false;
            boolean haveAttribute2 = false;

            StringBuilder tagName = new StringBuilder();
            StringBuilder attribute = new StringBuilder();
            StringBuilder tagValue = new StringBuilder();
            StringBuilder attributes = new StringBuilder();


            if (matcher.group(4) == null){
                tagName.append(matcher.group(1));
                attribute.append(matcher.group(2));
                tagValue.append(matcher.group(3));
            } else if (matcher.group(1) == null) {
                haveAttribute2 = true;
                tagName.append(matcher.group(4));
                attribute.append(matcher.group(5));
                tagValue.append("");
            }

            Matcher attributeMatcher = attrPattern.matcher(attribute);
            while (attributeMatcher.find()) {
                haveAttribute = true;
                String attributeName = attributeMatcher.group(1);
                String attributeValue = attributeMatcher.group(2);
                attributes.append(attributeName).append(" = \"").append(attributeValue).append("\"\n");
            }

            String subValue = String.valueOf(tagValue);

            if (subValue.contains("<")) {
                again = true;
            }

            if (subValue.equals("")) {
                again = false;
                haveBlankValue = true;
            }

            if (!haveAttribute && !haveAttribute2) {
                outPut.append("Element:\n" + "path = " + tagName + "\n");
//                    System.out.println("No attributes");

            } else if (again) {
                outPut.append("Element:\n" + "path = ")
                        .append(parentPath).append(tagName).append("\n")
                        .append("attributes: \n")
                        .append(attributes)
                        .append("\n");
//                    System.out.println("again");
            } else if (!again && !haveBlankValue) {
//                    System.out.println("again 2");
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
//                    System.out.println("Value is blank");
            } else if (haveAttribute2) {
                outPut.append("Element:\n" +
                                "path = " + parentPath + "\n" +
                                "value = nullEEEEEEEEEEEEEEEEEEEEEEEEee" +
                                "attributes:\n")
                        .append(attributes)
                        .append("\n");}
//                    System.out.println("No attributes");
            parentPath.add(String.valueOf(tagName));
            outPut.append(parseTags(subValue, parentPath));
            parentPath.remove(String.valueOf(tagName));

        }

        return outPut;
    }
}