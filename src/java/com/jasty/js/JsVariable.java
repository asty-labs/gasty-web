package com.jasty.js;

/**
 * This class represents variable names
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsVariable implements JsExpression {

	private String name;
	
	public JsVariable(String name) {
		this.name = name;
	}

	@Override
	public String encode() {
		return name;
	}
}
