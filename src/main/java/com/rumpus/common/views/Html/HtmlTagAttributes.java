package com.rumpus.common.views.Html;

import com.rumpus.common.Manager.AbstractCommonManagerIdKey;
import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.util.StringUtil;

/**
 * This class is used to manage the attributes of an {@link AbstractHtmlObject} object.
 * <p>
 * It is a set of {@link Attribute} objects.
 */
public class HtmlTagAttributes extends AbstractCommonManagerIdKey<Attribute> implements IManageable {
    
        private static final String NAME = "HtmlTagAttributes";
    
        private HtmlTagAttributes() {
            super(NAME);
        }
    
        ////////////////////////////
        /// Static factory methods /
        ////////////////////////////

        /**
         * Creates an empty {@link HtmlTagAttributes} object.
         * @return an empty {@link HtmlTagAttributes} object
         */
        public static HtmlTagAttributes create() {
            return new HtmlTagAttributes();
        }

        /**
         * Creates an {@link HtmlTagAttributes} object from a {@link java.util.Map} of {@link String} keys and {@link String} values.
         * <p>
         * The {@link String} keys are the names of the {@link Attribute} objects.
         * <p>
         * The {@link String} values are the values of the {@link Attribute} objects, delimited by the valueDelimiter.
         * 
         * @param attributes the {@link java.util.Map} of {@link String} keys and {@link String} values
         * @param valueDelimiter the delimiter used to separate the values of the {@link Attribute} objects
         * @return an {@link HtmlTagAttributes} object
         */
        public static HtmlTagAttributes createFromMap(java.util.Map<String, String> attributes, String valueDelimiter) {
            HtmlTagAttributes htmlTagAttributes = new HtmlTagAttributes();
            for (java.util.Map.Entry<String, String> entry : attributes.entrySet()) {
                htmlTagAttributes.add(
                    Attribute.create(entry.getKey().strip(), Attribute.getValuesFromStringOfValues(entry.getValue(), valueDelimiter))
                );
            }
            return htmlTagAttributes;
        }

        public static HtmlTagAttributes createFromString(String attributesString, String attributeDelimiter, String propValueDelimiter, String valueDelimiter) {
            HtmlTagAttributes htmlTagAttributes = new HtmlTagAttributes();
            String[] attributes = attributesString.split(attributeDelimiter);
            for (String attribute : attributes) {
                String[] propValue = attribute.split(propValueDelimiter);
                if (propValue.length == 2) { // TODO: could this be 1?
                    htmlTagAttributes.add(
                        Attribute.create(propValue[0].strip(), Attribute.getValuesFromStringOfValues(propValue[1], valueDelimiter))
                    );
                }
            }
            return htmlTagAttributes;
        }

        /**
         * Creates an {@link HtmlTagAttributes} object from a {@link java.util.Map} of {@link String} keys and {@link String} values.
         * 
         * @return an {@link HtmlTagAttributes} object
         */
        public static HtmlTagAttributes createEmpty() {
            return new HtmlTagAttributes();
        }

        /////////////////////////
        /// End factory methods /
        /////////////////////////
    
        /**
         * Creates a {@link java.util.Map} from the {@link Attribute} objects in this {@link HtmlTagAttributes} object.
         * 
         * @param valueDelimiter the delimiter used to separate the values of the {@link Attribute} objects
         * @return a {@link java.util.Map} of {@link String} keys and {@link String} values
         */
        public java.util.Map<String, String> getAttributesAsMap(String valueDelimiter) {
            java.util.Map<String, String> attributes = new java.util.HashMap<String, String>();
            for(Attribute attribute : this) {
                if (attribute != null) {
                    attributes.put(attribute.getPropertyName(), attribute.getValueAsString(valueDelimiter));
                }
            }
            return attributes;
        }

        /**
         * Get this {@link HtmlTagAttributes} object as a {@link String} of {@link Attribute} objects.
         * 
         * @param attributeDelimiter the delimiter used to separate the {@link Attribute} objects
         * @param propValueDelimiter the delimiter used to separate the property name and the value of the {@link Attribute} objects
         * @param valueDelimiter the delimiter used to separate the values of the {@link Attribute} objects
         * @return a {@link String} of {@link Attribute} objects
         */
        public String getAttributesAsString(String attributeDelimiter, String propValueDelimiter, String valueDelimiter) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Attribute attribute : this) {
                stringBuilder.append(attribute.getPropertyName());
                stringBuilder.append(propValueDelimiter);
                stringBuilder.append(attribute.getValueAsString(valueDelimiter));
                stringBuilder.append(attributeDelimiter);
            }
            return StringUtil.trimEnd(stringBuilder.toString(), attributeDelimiter);
        }

        /**
         * Get this {@link HtmlTagAttributes} object as a {@link String} of {@link Attribute} objects.
         * <p>
         * Same as {@link #getAttributesAsString(String, String, String)} but with default delimiters.
         * <p>
         * The default delimiters are:
         * <ul>
         * <li>attributeDelimiter: ","</li>
         * <li>propValueDelimiter: "="</li>
         * <li>valueDelimiter: " "</li>
         * </ul>
         * 
         * @return a {@link String} of {@link Attribute} objects
         */
        public String getAttributesAsString() {
            return this.getAttributesAsString(",", "=", " ");
        }
}
