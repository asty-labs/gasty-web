package com.jasty.js;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents sequence of statements, separated by semicolon
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsSequence implements JsExpression {

	private Collection<JsExpression> expressions = new ArrayList<JsExpression>();

	public JsSequence(JsExpression ... expressions) {
		for(JsExpression expr : expressions) {
			add(expr);
		}
	}
	
	public void add(JsExpression expr) {
		expressions.add(expr);
	}
	
	@Override
	public String encode() {
		StringBuilder builder = new StringBuilder();
		for(JsExpression expr : expressions) {
			builder.append(expr.encode());
			builder.append(";\n");
		}
		return builder.toString();
	}
}
