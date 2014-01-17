package com.jasty.js;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents closure (anonymous function)
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsClosure implements JsExpression {

	private JsExpression body;
	private List<JsVariable> args = new ArrayList<JsVariable>();

    /**
     * This constructor uses JsExpression as closure body directly
     *
     * @param body  code of the closure as JsExpression
     * @param args  closure parameters
     */
	public JsClosure(JsExpression body, JsVariable ... args) {
		this.body = body;
        Collections.addAll(this.args, args);
	}

    /**
     * This constructor collects JsExpression by running delegate
     *
     * @param closure  delegate to be run to collect JsExpression
     * @param args  closure parameters
     */
	public JsClosure(Runnable closure, JsVariable ... args) {
		this(JsContext.execute(closure), args);
	}
	
	@Override
	public String encode() {
		StringBuilder builder = new StringBuilder();
		builder.append("function(");
		boolean hasFirst = false;
		for(JsVariable var : args) {
			if(hasFirst)
				builder.append(", ");
			else
				hasFirst = true;
			builder.append(var.encode());
		}
		builder.append("){").append(body.encode()).append("}");
		return builder.toString();
	}

    public String encodeOnLoadAsHtml() {
        return "<script> jQuery(" + encode().replaceAll("</script>", "</scr\"+\"ipt>") + ") </script>";
    }
}
