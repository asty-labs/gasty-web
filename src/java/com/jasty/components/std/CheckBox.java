package com.jasty.components.std;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;
import com.jasty.core.ParameterProvider;

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
    public void restore(ParameterProvider parameterProvider) {
        checked = parameterProvider.getParameterNames().contains(getId()) && "1".equals(parameterProvider.getParameter(getId()));
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
