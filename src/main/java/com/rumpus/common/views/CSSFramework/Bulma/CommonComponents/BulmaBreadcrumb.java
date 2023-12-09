package com.rumpus.common.views.CSSFramework.Bulma.CommonComponents;

import com.rumpus.common.views.Component.AbstractBreadcrumb;
import com.rumpus.common.views.Html.AbstractHtmlObject;

public class BulmaBreadcrumb extends AbstractBreadcrumb {
    
        private static final String NAME = "BulmaBreadcrumb";
    
        private BulmaBreadcrumb() {
            super(NAME);
            this.init();
        }

        public static BulmaBreadcrumb create() {
            return new BulmaBreadcrumb();
        }

        private void init() {
            this.addHtmlTagAttribute("class", "breadcrumb");
            this.addHtmlTagAttribute("aria-label", "breadcrumbs");
        }

        @Override
        public void setChildrenFromCrumbs() {
            for (String key : this.keySet()) {
                AbstractHtmlObject li = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                li.setHtmlTagType(AbstractHtmlObject.HtmlTagType.LI);
                AbstractHtmlObject a = AbstractHtmlObject.createEmptyAbstractHtmlObject();
                a.setHtmlTagType(AbstractHtmlObject.HtmlTagType.A);
                a.addHtmlTagAttribute("href", this.get(key));
                a.setBody(key);
                li.addChild(a);
                this.ul.addChild(li);
            }
            this.addChild(ul);
        }
}
