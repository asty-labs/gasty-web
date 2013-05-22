package com.jasty.components;

import java.util.Map;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;

/**
 * Component proxy for HTML text editor
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class TextBox extends Component {

    @InitProperty
	private String value;

    @InitProperty
    private int maxLength;

    @InitProperty
    private int rows;

    @InitProperty
    private int cols;

    private boolean password;

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		invoke("value", value);
	}

    public void setPassword(boolean value) {
        this.password = value;
    }

    @Override
	public void restore(Map<String, Object> data) {
        if(data.containsKey(getId()))
    		value = (String)data.get(getId());
	}

    @Override
    public String getHtmlTag() {
        return rows == 0 ? "input" : "textarea";
    }

    @Override
    protected void fillHtmlAttributes(Map<String, String> attributes) {
        attributes.put("type", password ? "password" : "text");
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
