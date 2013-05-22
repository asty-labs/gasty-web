package com.jasty.components;

import com.jasty.core.ComponentProxy;

/**
 * Component proxy for generic JQuery-functions
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JQuery extends ComponentProxy {

	public JQuery html(Object value) {
		invoke("html", value);
		return this;
	}

	public JQuery text(String value) {
		invoke("text", value);
		return this;
	}
	
	public JQuery append(Object content) {
		invoke("append", content);
		return this;
	}
	
	public JQuery empty() {
		invoke("empty");
		return this;
	}
}
