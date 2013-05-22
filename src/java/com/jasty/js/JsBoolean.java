package com.jasty.js;

/**
 * This class represents boolean literals
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsBoolean implements JsExpression{

	private boolean value;
	
	public JsBoolean(boolean value) {
		this.value = value;
	}
	
	@Override
	public String encode() {
		return value ? "true" : "false";
	}
}
