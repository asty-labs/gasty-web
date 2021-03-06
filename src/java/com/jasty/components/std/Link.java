package com.jasty.components.std;

import com.jasty.core.Component;
import com.jasty.core.InitProperty;

/**
 * Component proxy for HTML link (anchor)
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class Link extends Component {
	
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
    
    public void setOnClick(String value) {
        onClick = value;
    }

    public void setConfirmTitle(String value) {
        confirmTitle = value;
    }

    public void setConfirmText(String value) {
        confirmText = value;
    }

    @Override
    public String getHtmlTag() {
        return "a";
    }
}
