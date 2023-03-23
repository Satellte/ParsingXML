package Parcer;

import java.util.List;

public class Tag {
    String parent;
    String level;
    String tagName;
    String tagValue;
    String attributeName;
    String attributeValue;
    List<Tag> nestedTagList;


    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    public void setAttributeName1(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setGetAttributeValue1(String AttributeValue) {
        this.attributeValue = attributeValue;
    }

    public void addAttribute(String attributeName, String attributeValue) {
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    public void setNestedTags(List<Tag> nestedTagList) {
        this.nestedTagList = nestedTagList;
    }
}
