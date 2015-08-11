package com.jasty.components.std;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;

import java.util.Map;

/**
 * Component proxy for HTML Button
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class Button extends Component {
	
	@SuppressWarnings("unused")
    @InitProperty
	private String text;
	@SuppressWarnings("unused")
    @InitProperty
	private String onClick;
    @SuppressWarnings("unused")
    @InitProperty
    String confirmTitle;
    @SuppressWarnings("unused")
    @InitProperty
    String confirmText;

	public void setText(String text) {
		this.text = text;
		invoke("text", text);
	}

    @Override
    public String getHtmlTag() {
        return "button";
    }

    @Override
    protected void fillHtmlAttributes(Map<String, String> attributes) {
        attributes.put("type", "button");
    }
}
