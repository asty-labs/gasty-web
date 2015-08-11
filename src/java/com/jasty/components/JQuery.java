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

	public JQuery val(Object value) {
		invoke("val", value);
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

    public JQuery toggle(boolean value) {
        invoke("toggle", value);
        return this;
    }

    public JQuery attr(String name, String value) {
        invoke("attr", name, value);
        return this;
    }

	public  JQuery prop(String name, Boolean value){
		invoke("prop", name, value);
		return this;
	}

	public  JQuery trigger(String eventName, Object ... params){
		invoke("trigger", eventName, params);
		return this;
	}

    public JQuery moveTo(String query) {
        invoke("moveTo", query);
        return this;
    }
}
