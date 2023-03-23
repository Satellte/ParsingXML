package Parcer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Pattern TAG_PATTERN = Pattern.compile("<([a-zA-Z]+)([^>]*)>(.*?)</\\1>");

    public static List<Tag> parseTags(String input) {
        List<Tag> tags = new ArrayList<>();
        Matcher matcher = TAG_PATTERN.matcher(input);
        while (matcher.find()) {
            String tagName = matcher.group(1);  //извлекаем имя тега
            String attributes = matcher.group(2); //извлекаем атрибуты тэга
            String tagValue = matcher.group(3); //извлекаем строку между открытием и закрытием тега

            Tag tag = new Tag(); //создание нового объекта Tag
            tag.setTagName(tagName); // заполняем имя тега в объекте Tag

            // Извлечение атрибутов и передача в объект Tag
            Pattern attributePattern = Pattern.compile("([a-zA-Z]+)=\"([^\"]+)\"");
            Matcher attributeMatcher = attributePattern.matcher(attributes);
            while (attributeMatcher.find()) {
                String attributeName = attributeMatcher.group(1);
                String attributeValue = attributeMatcher.group(2);
                tag.addAttribute(attributeName, attributeValue);
            }

            if (tagValue.contains("<")) {
                    parseTags(tagValue);
            } else tag.setTagValue(tagValue);

            // Рекурсивный вызов метода для обработки вложенных тегов

//            String nestedTags = matcher.group();
//            if (nestedTags.contains("<")) {
//                List<Tag> nestedTagList = parseTags(nestedTags);
//                tag.setNestedTags(nestedTagList);
//            }

            tags.add(tag);

        }
        return tags;
    }
}
