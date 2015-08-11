package com.jasty.components.std;

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
    private String clazz;

    public Option(String value, String text) {
        this.value = value;
        this.text = text;
        this.clazz = null;
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public JsExpression toJsExpression() {
        return JsList.from(value, text, clazz);
    }
}
