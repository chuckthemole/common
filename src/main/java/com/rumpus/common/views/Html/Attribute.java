package com.rumpus.common.views.Html;

import com.rumpus.common.Builder.LogBuilder;
import com.rumpus.common.Log.ICommonLogger.LogLevel;
import com.rumpus.common.util.StringUtil;
import com.rumpus.common.views.Html.AbstractHtmlObject.HtmlTagType;

/**
 * Attribute class
 * <p>
 * Represents an HTML attribute
 * <p>
 * Example:
 * <p>
 * {@code
 * Attribute.createIdAttribute("myId");
 * Attribute.createClassAttribute("myClass");
 * }
 * <p>
 * Result:
 * <p>
 * {@code
 * id="myId"
 * class="myClass"
 * }
 */
public class Attribute extends com.rumpus.common.Manager.AbstractSetItem {

    // changed to enum below. may delete these in the future. keeping for now so as not to break anything - chuck
    public static final String CLASS_ATTRIBUTE_NAME = "class";
    public static final String ID_ATTRIBUTE_NAME = "id";
    public static final String STYLE_ATTRIBUTE_NAME = "style";
    public static final String HREF_ATTRIBUTE_NAME = "href";
    public static final String SRC_ATTRIBUTE_NAME = "src";
    public static final String ALT_ATTRIBUTE_NAME = "alt";
    public static final String TITLE_ATTRIBUTE_NAME = "title";
    public static final String TYPE_ATTRIBUTE_NAME = "type";
    public static final String NAME_ATTRIBUTE_NAME = "name";
    public static final String VALUE_ATTRIBUTE_NAME = "value";
    public static final String PLACEHOLDER_ATTRIBUTE_NAME = "placeholder";
    public static final String ROWS_ATTRIBUTE_NAME = "rows";
    public static final String COLS_ATTRIBUTE_NAME = "cols";
    public static final String TARGET_ATTRIBUTE_NAME = "target";
    public static final String FOR_ATTRIBUTE_NAME = "for";
    // TODO: add more maybe?

    public enum AttributeProperty {
        CLASS(CLASS_ATTRIBUTE_NAME),
        ID(ID_ATTRIBUTE_NAME),
        STYLE(STYLE_ATTRIBUTE_NAME),
        HREF(HREF_ATTRIBUTE_NAME),
        SRC(SRC_ATTRIBUTE_NAME),
        ALT(ALT_ATTRIBUTE_NAME),
        TITLE(TITLE_ATTRIBUTE_NAME),
        TYPE(TYPE_ATTRIBUTE_NAME),
        NAME(NAME_ATTRIBUTE_NAME),
        VALUE(VALUE_ATTRIBUTE_NAME),
        PLACEHOLDER(PLACEHOLDER_ATTRIBUTE_NAME),
        ROWS(ROWS_ATTRIBUTE_NAME),
        COLS(COLS_ATTRIBUTE_NAME),
        TARGET(TARGET_ATTRIBUTE_NAME),
        FOR(FOR_ATTRIBUTE_NAME);

        private String attributeProperty;

        AttributeProperty(String attributeProperty) {
            this.attributeProperty = attributeProperty;
        }

        public String getAttributeProperty() {
            return this.attributeProperty;
        }
    }

    /**
     * The property propertyName of the attribute
     */
    private String propertyName; // TODO: made an enum class for this. Do I want this member to be enum type or String type? - chuck
    /**
     * The value of the attribute as a Set of Strings
     * <p>
     * This is a list because some attributes can have multiple values
     * <p>
     * Example: class="class1 class2 class3" would be a list of 3 strings
     */
    private java.util.Set<String> value; // TODO: maybe if this is null do not add ="" to the toString method TODO: maybe change the name of this variable. Too vague and short.

    // Ctor use static factory methods instead
    private Attribute(String propertyName, java.util.Set<String> value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    ///////////////////////////
    // Static Factory Methods /
    ///////////////////////////
    public static Attribute create(String propertyName, String value) {
        return new Attribute(propertyName, java.util.Set.of(value));
    }

    public static Attribute create(String propertyName, java.util.Set<String> value) {
        return new Attribute(propertyName, value);
    }

    public static Attribute create(String propertyName, String... values) {
        return new Attribute(propertyName, java.util.Set.of(values));
    }

    public static Attribute createCopy(Attribute attribute) {
        return new Attribute(attribute.getPropertyName(), attribute.getValue());
    }

    /**
     * Create an empty attribute
     * 
     * @param useNullValues if true, use null values for the attribute propertyName and value, otherwise use empty strings
     * @return an empty attribute
     */
    public static Attribute createEmptyAttribute(boolean useNullValues) {
        return useNullValues ? new Attribute(null, null) : new Attribute("", java.util.Set.of());
    }

    public static Attribute createClassAttribute(java.util.Set<String> value) {
        return new Attribute(CLASS_ATTRIBUTE_NAME, value);
    }

    public static Attribute createIdAttribute(java.util.Set<String> value) {
        return new Attribute(ID_ATTRIBUTE_NAME, value);
    }

    public static Attribute createStyleAttribute(java.util.Set<String> value) {
        return new Attribute(STYLE_ATTRIBUTE_NAME, value);
    }

    public static Attribute createHrefAttribute(java.util.Set<String> value) {
        return new Attribute(HREF_ATTRIBUTE_NAME, value);
    }

    public static Attribute createSrcAttribute(java.util.Set<String> value) {
        return new Attribute(SRC_ATTRIBUTE_NAME, value);
    }

    public static Attribute createAltAttribute(java.util.Set<String> value) {
        return new Attribute(ALT_ATTRIBUTE_NAME, value);
    }

    public static Attribute createTitleAttribute(java.util.Set<String> value) {
        return new Attribute(TITLE_ATTRIBUTE_NAME, value);
    }

    public static Attribute createTypeAttribute(java.util.Set<String> value) {
        return new Attribute(TYPE_ATTRIBUTE_NAME, value);
    }

    public static Attribute createNameAttribute(java.util.Set<String> value) {
        return new Attribute(NAME_ATTRIBUTE_NAME, value);
    }

    public static Attribute createValueAttribute(java.util.Set<String> value) {
        return new Attribute(VALUE_ATTRIBUTE_NAME, value);
    }

    public static Attribute createPlaceholderAttribute(java.util.Set<String> value) {
        return new Attribute(PLACEHOLDER_ATTRIBUTE_NAME, value);
    }

    public static Attribute createRowsAttribute(java.util.Set<String> value) {
        return new Attribute(ROWS_ATTRIBUTE_NAME, value);
    }

    public static Attribute createColsAttribute(java.util.Set<String> value) {
        return new Attribute(COLS_ATTRIBUTE_NAME, value);
    }

    public static Attribute createTargetAttribute(java.util.Set<String> value) {
        return new Attribute(TARGET_ATTRIBUTE_NAME, value);
    }

    public static Attribute createForAttribute(java.util.Set<String> value) {
        return new Attribute(FOR_ATTRIBUTE_NAME, value);
    }

    /**
     * Add the given value to the attribute value set.
     * 
     * @param value the value to add
     * @return the attribute value as a string
     */
    public String addValue(String value) {
        if(this.value.add(value)) {
            LOG("Added value: " + value + " to attribute: " + this.propertyName);
        } else {
            LOG("Value: " + value + " already exists in attribute: " + this.propertyName);
        }
        return this.getValueAsString();
    }

    public String addValues(String... values) {
        for(String value : values) {
            if(this.value.add(value.strip())) {
                LOG("Added value: " + value + " to attribute: " + this.propertyName);
            } else {
                LOG("Value: " + value + " already exists in attribute: " + this.propertyName);
            }
        }
        return this.getValueAsString();
    }

    /**
     * Add the given values to the attribute value set.
     * 
     * @param values the values to add
     * @return the attribute value as a string
     */
    public String addValues(java.util.Set<String> values) {
        for(String value : values) {
            if(this.value.add(value.strip())) {
                LOG("Added value: " + value + " to attribute: " + this.propertyName);
            } else {
                LOG("Value: " + value + " already exists in attribute: " + this.propertyName);
            }
        }
        return this.getValueAsString();
    }

    /**
     * Remove the given value from the attribute value set.
     * 
     * @param value the value to remove
     * @return the attribute value as a string
     */
    public String removeValue(String value) {
        if(this.value.remove(value)) {
            LOG("Removed value: " + value + " from attribute: " + this.propertyName);
        } else {
            LOG("Value: " + value + " does not exist in attribute: " + this.propertyName);
        }
        return this.getValueAsString();
    }

    /**
     * Remove the given values from the attribute value set.
     * 
     * @param values the values to remove
     * @return the attribute value as a string
     */
    public String removeValues(java.util.Set<String> values) {
        for(String value : values) {
            if(this.value.remove(value)) {
                LOG("Removed value: " + value + " from attribute: " + this.propertyName);
            } else {
                LOG("Value: " + value + " does not exist in attribute: " + this.propertyName);
            }
        }
        return this.getValueAsString();
    }

    ///////////////////////
    // Helper Methods /////
    ///////////////////////

    /**
     * Get the attributes from a String.
     * <p>
     * Example: <code>getAttributesFromStringOfAttributes("class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * <p>
     * Result: <code>class="class1 class2 class3" id="id1" style="style1 style2 style3"</code>
     * 
     * @param attributes
     * @param delimiter
     * @return
     */
    public static java.util.List<Attribute> getAttributesFromStringOfAttributes(String attributes, String delimiter) {
        java.util.List<Attribute> attributeList = new java.util.LinkedList<>();
        if(attributes == null || attributes.isEmpty()) {
            LOG_THIS("attributes is null or empty, returning empty attribute list");
            return attributeList;
        }
        String[] attributesArray = attributes.split(delimiter);
        for(String attribute : attributesArray) {
            String[] attributePropAndValue = attribute.split("=");
            if(attributePropAndValue.length == 2) { // make sure this is 2 TODO: maybe this could be 1? something like 'active' or 'disabled'?
                java.util.HashSet<String> valueSet = new java.util.HashSet<>();
                String[] valuesArray = attributePropAndValue[1].split(" ");
                for(String value : valuesArray) {
                    if(valueSet.add(value.strip())) {
                        LOG_THIS("Added value: " + value + " to attribute: " + attributePropAndValue[0]);
                    } else {
                        LOG_THIS("Value: " + value + " already exists in attribute: " + attributePropAndValue[0]);
                    }
                }
                attributeList.add(new Attribute(attributePropAndValue[0].strip(), valueSet));
            } else {
                LOG_THIS(LogLevel.ERROR, "Invalid attribute: " + attribute + " (TODO: maybe this could be 1? something like 'active' or 'disabled'?)");
            }
        }
        return attributeList;
    }

    /**
     * Get the attribute from a String.
     * <p>
     * Example: <code>getAttributeFromString("class=class1 class2 class3")</code>
     * <p>
     * Result: <code>class="class1 class2 class3"</code>
     * 
     * @param attribute the String to parse
     * @return the {@link Attribute} object
     */
    public static Attribute getAttributeFromString(String attribute) {
        if(attribute == null || attribute.isEmpty()) {
            LOG_THIS("attribute is null or empty, returning empty attribute");
            return Attribute.createEmptyAttribute(false);
        }
        String[] attributePropAndValue = attribute.split("=");
        if(attributePropAndValue.length == 2) { // make sure this is 2 TODO: maybe this could be 1? something like 'active' or 'disabled'?
            java.util.HashSet<String> valueSet = new java.util.HashSet<>();
            String[] valuesArray = attributePropAndValue[1].split(" ");
            for(String value : valuesArray) {
                if(valueSet.add(value.strip())) {
                    LOG_THIS("Added value: " + value + " to attribute: " + attributePropAndValue[0]);
                } else {
                    LOG_THIS("Value: " + value + " already exists in attribute: " + attributePropAndValue[0]);
                }
            }
            return new Attribute(attributePropAndValue[0].strip(), valueSet);
        } else {
            LOG_THIS(LogLevel.ERROR, "Invalid attribute: " + attribute + " (TODO: maybe this could be 1? something like 'active' or 'disabled'?)");
            return Attribute.createEmptyAttribute(false);
        }
    }

    public static java.util.Set<String> getValuesFromStringOfValues(String values, String delimiter) {
        java.util.Set<String> valueSet = new java.util.HashSet<>();
        if(values == null || values.isEmpty()) {
            LOG_THIS("values is null or empty, returning empty value set");
            return valueSet;
        }
        String[] valuesArray = values.split(delimiter);
        for(String value : valuesArray) {
            if(valueSet.add(value.strip())) {
                LOG_THIS("Added value: " + value + " to value set");
            } else {
                LOG_THIS("Value: " + value + " already exists in value set");
            }
        }
        return valueSet;
    }


    /**
     * Get and set the attributes for the given html object.
     * <p>
     * Example: <code>getAndSetAttributesForHtmlObject(hTypeHtmlObject, "class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param hTypeHtmlObject the html object to set the attributes for
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the html object with the attributes set
     */
    public static AbstractHtmlObject getAndSetAttributesForHtmlObject(AbstractHtmlObject hTypeHtmlObject, String attributes, String delimiter) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Getting and setting attributes for given html object :: Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).toString();
        LOG_THIS(log);
        if(attributes == null || attributes.isEmpty()) {
            LOG_THIS("attributes is null or empty, returning empty html object");
            return hTypeHtmlObject;
        }
        String[] attributesArray = attributes.split(delimiter);
        for(String attribute : attributesArray) {
            String[] attributePropAndValue = attribute.split("=");
            if(attributePropAndValue.length == 2) {
                hTypeHtmlObject.addToAttribute(attributePropAndValue[0].strip(), attributePropAndValue[1].strip());
            } else {
                LOG_THIS(LogLevel.ERROR, "Invalid attribute: ", attribute);
            }
        }
        return hTypeHtmlObject;
    }

    /**
     * Get an empty html object with the given attributes.
     * <p>
     * Example: <code>getEmptyHtmlObjectWithAttributes("class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the empty html object with the attributes set
     */
    public static AbstractHtmlObject getEmptyHtmlObjectWithAttributes(String attributes, String delimiter) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Creating empty html object and adding attributes :: Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).toString();
        LOG_THIS(log);
        return getAndSetAttributesForHtmlObject(AbstractHtmlObject.createEmptyAbstractHtmlObject(), attributes, delimiter);
    }

    /**
     * Get an empty html object with the given attributes and html tag type.
     * <p>
     * Example: <code>getEmptyHtmlObjectWithAttributesAndHtmlTagType(HtmlTagType.DIV, "class=class1 class2 class3, id=id1, style=style1 style2 style3", ",")</code>
     * 
     * @param htmlTagType the html tag type of the html object
     * @param attributes the attributes to set, this is a string of attributes separated by the given delimiter
     * @param delimiter the delimiter to use to separate the attributes
     * @return the empty html object with the attributes and html tag type set
     */
    public static AbstractHtmlObject getEmptyHtmlObjectWithAttributesAndHtmlTagType(HtmlTagType htmlTagType, String attributes, String delimiter) {
        final String log = LogBuilder.logBuilderFromStringArgsNoSpaces(
            "Creating empty html object and adding attributes :: Tag Type: ",
            htmlTagType.getHtmlTagType(),
            "Attributes: ",
            attributes,
            "Delimiter: ",
            delimiter).toString();
        LOG_THIS(log);
        AbstractHtmlObject hTypeHtmlObject = getEmptyHtmlObjectWithAttributes(attributes, delimiter);
        hTypeHtmlObject.setHtmlTagType(htmlTagType);
        return hTypeHtmlObject;
    }

    /**
     * Clean the given attribute value string.
     * <p>
     * Example: <code>cleanAttributeValueString(" class1  class2 class3")</code> will return "class1 class2 class3".
     * <p>
     * TODO: maybe this can be in {@link StringUtil} seems like a string problem.
     * @param attributesString
     * @return
     */
    public static String cleanAttributeValueString(String attributesString) {
        StringBuilder sb = new StringBuilder();
        String[] attributesArray = attributesString.split(" ");
        boolean firstValueChosen = false;
        for(int attrtibutesArrayIndex = 0; attrtibutesArrayIndex < attributesArray.length; attrtibutesArrayIndex++) {
            if(attributesArray[attrtibutesArrayIndex].strip().isEmpty()) {
                continue;
            } else if(!firstValueChosen) {
                sb.append(attributesArray[attrtibutesArrayIndex].strip());
                firstValueChosen = true;
            } else {
                sb.append(" ").append(attributesArray[attrtibutesArrayIndex].strip());

            }
        }
        return sb.toString();
    }

    ///////////////////////
    // End Helper Methods//
    ///////////////////////

    ///////////////////////
    // Getters and Setters/
    ///////////////////////
    public String getPropertyName() {
        return this.propertyName;
    }

    public java.util.Set<String> getValue() {
        return this.value;
    }

    public String getValueAsString() {
        StringBuilder sb = new StringBuilder();
        for(String value : this.value) {
            sb.append(value).append(" ");
        }
        return sb.toString().strip();
    }

    public String getValueAsString(String delimiter) {
        StringBuilder sb = new StringBuilder();
        for(String value : this.value) {
            sb.append(value).append(delimiter);
        }
        return sb.toString().strip();
    }

    public Attribute setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public Attribute setValue(java.util.Set<String> value) {
        this.value = value;
        return this;
    }

    public java.util.Set<String> getValues() {
        return this.value;
    }

    @Override
    public String toString() {
        if(this.propertyName == null || this.value == null || this.propertyName.isEmpty() || this.value.isEmpty()) {
            return StringUtil.prettyPrintJson("");
        }
        StringBuilder values = new StringBuilder();
        for(String value : this.value) {
            values.append(value).append(" ");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.propertyName)
            .append("=")
            .append(values.toString().strip());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Attribute)) {
            LOG("obj is null or not an instance of Attribute");
            return false;
        }
        Attribute attribute = (Attribute) obj;
        return this.propertyName.equals(attribute.getPropertyName()) && this.value.equals(attribute.getValue());
    }

    private static void LOG_THIS(String... args) {
        com.rumpus.common.ICommon.LOG(Attribute.class, args);
    }

    private static void LOG_THIS(com.rumpus.common.Log.ICommonLogger.LogLevel level, String... args) {
        com.rumpus.common.ICommon.LOG(Attribute.class, level, args);
    }
}