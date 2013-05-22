package com.jasty.components;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;

import java.util.Map;

/**
 * Component proxy for HTML checkbox editor
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class CheckBox extends Component {

    @InitProperty
    private boolean checked;

    @SuppressWarnings("unused")
    @InitProperty
    private String onChange;

    @Override
    public void restore(Map<String, Object> data) {
        checked = data.containsKey(getId()) && "1".equals(data.get(getId()));
    }

    @Override
    public String getHtmlTag() {
        return "input";
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        invoke("checked", checked);
    }

    @Override
    protected void fillHtmlAttributes(Map<String, String> attributes) {
        attributes.put("type", "checkbox");
        attributes.put("value", "1");
    }
}
