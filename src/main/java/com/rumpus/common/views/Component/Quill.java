package com.rumpus.common.views.Component;

import com.rumpus.common.Manager.IManageable;
import com.rumpus.common.views.Html.AbstractHtmlObject;

/**
 * An AbstractView that uses rumpus_quill.js to render a Quill editor.
 * 
 * TODO: this needs to be built out more and connected to the rumpus_quill.js object.
 */
public class Quill extends AbstractHtmlObject {

        // TODO may need to change types on some of these members. This will mirror the rumpus_quill.js object.
        private String modules;
        private String formats;
        private String placeholder;
        private String value;
        private String setValue;
        private String editor_ref;

        private Quill(String modules, String formats, String placeholder, String value, String setValue, String editor_ref) {
            super(HtmlTagType.DIV, "");
            this.modules = modules;
            this.formats = formats;
            this.placeholder = placeholder;
            this.value = value;
            this.setValue = setValue;
            this.editor_ref = editor_ref;
        }

        public static Quill create(String modules, String formats, String placeholder, String value, String setValue, String editor_ref) {
            return new Quill(modules, formats, placeholder, value, setValue, editor_ref);
        }

        public static Quill createEmpty() {
            return new Quill("", "", "", "", "", "");
        }

        public String getModules() {
            return modules;
        }

        public void setModules(String modules) {
            this.modules = modules;
        }

        public String getFormats() {
            return formats;
        }

        public void setFormats(String formats) {
            this.formats = formats;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getSetValue() {
            return setValue;
        }

        public void setSetValue(String setValue) {
            this.setValue = setValue;
        }

        public String getEditor_ref() {
            return editor_ref;
        }

        public void setEditor_ref(String editor_ref) {
            this.editor_ref = editor_ref;
        }
}
