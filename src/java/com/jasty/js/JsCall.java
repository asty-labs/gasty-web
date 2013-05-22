package com.jasty.js;

/**
 * This class represents call of a function with parameters
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsCall implements JsExpression {

	private Object[] args;
	private String method;
	
	public JsCall(final String method, Object ... args) {
		this.method = method;
		this.args = args;
	}
	
	public String encode() {
		StringBuilder builder = new StringBuilder(method);
		builder.append("(");
		boolean argumentProcessed = false;
        if(args != null) {
            for(Object param : args) {
                if(argumentProcessed)
                    builder.append(", ");
                else
                    argumentProcessed = true;
                JsExpression expr = JsExpressionFactory.create(param);
                builder.append(expr.encode());
            }
        }
		builder.append(")");
		return builder.toString();
	}
}
