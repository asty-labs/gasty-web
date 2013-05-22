package com.jasty.js;

/**
 * This class represents string constants
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsString implements JsExpression {

	private String value;
	
	public JsString(String value) {
		this.value = value;
	}
	
	@Override
	public String encode() {
		return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\"";
	}
}
