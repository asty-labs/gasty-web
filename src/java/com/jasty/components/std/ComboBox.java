package com.jasty.components.std;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;
import com.jasty.core.ParameterProvider;

/**
 * Component proxy for HTML combo editor
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class ComboBox extends Component {
    @InitProperty
    private String value;

    @SuppressWarnings("unused")
    @InitProperty
    private String onChange;

    @InitProperty
    private Iterable<Option> options;

    @Override
    public void restore(ParameterProvider parameterProvider) {
        if(parameterProvider.getKeys().contains(getId()))
            value = parameterProvider.getParameter(getId());
    }

    @Override
    public String getHtmlTag() {
        return "select";
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        invoke("value", value);
    }

    public void setOptions(Iterable<Option> options) {
        this.options = options;
        invoke("options", options);
    }
}
