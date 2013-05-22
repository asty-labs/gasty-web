package com.jasty.components;

import com.jasty.js.JsExpression;
import com.jasty.js.JsList;
import com.jasty.js.JsSerializable;

/**
 * Component proxy for HTML combobox option
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class Option implements JsSerializable {
    private String value;
    private String text;

    public Option(String value, String text) {
        this.value = value;
        this.text = text;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public JsExpression toJsExpression() {
        return JsList.from(value, text);
    }
}
