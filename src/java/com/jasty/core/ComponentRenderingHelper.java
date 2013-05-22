package com.jasty.core;

import java.util.Map;

/**
 * This class contains helper methods to generate initial HTML-code for
 * javascript components.
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class ComponentRenderingHelper {
    
    public static String getStartTag(Component component) {
        StringBuilder sb = new StringBuilder("<").append(component.getHtmlTag());
        appendAttributes(sb, component);
        sb.append(">");
        return sb.toString();
    }
    
    public static String getEndTag(Component component) {
        return String.format("</%s>", component.getHtmlTag());
    }

    public static String getVoidTag(Component component) {
        StringBuilder sb = new StringBuilder("<").append(component.getHtmlTag());
        appendAttributes(sb, component);
        sb.append("/>");
        return sb.toString();
    }
    
    private static void appendAttributes(StringBuilder sb, Component component) {
        for(Map.Entry<String, String> entry : component.getHtmlAttributes().entrySet()) {
            sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
    }
}
