package com.jasty.js;

import java.math.BigDecimal;

/**
 * This class represents numeric constants
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsNumber implements JsExpression {

	private String text;

	public JsNumber(BigDecimal value) {
		text = value.toPlainString();
	}

	public JsNumber(double value) {
		text = Double.toString(value);
	}

	public JsNumber(long value) {
		text = Long.toString(value);
	}

	@Override
	public String encode() {
		return text;
	}

}
