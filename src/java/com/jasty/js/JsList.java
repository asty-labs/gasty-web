package com.jasty.js;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class represents lists or arrays
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsList implements JsExpression {

	private ArrayList<Object> items = new ArrayList<Object>();
	
	public JsList() {
	}
	
	public JsList(Iterable<?> items) {
        for(Object o : items) this.items.add(o);
	}
	
	public JsList(Object[] items) {
        Collections.addAll(this.items, items);
	}

	public static JsList from(Object ... params) {
		JsList result = new JsList();
        Collections.addAll(result.items, params);
		return result;
	}
	
	@Override
	public String encode() {
		StringBuilder builder = new StringBuilder("[");
		boolean hasFirst = false;
		for(Object item : items) {
			if(hasFirst)
				builder.append(", ");
			else
				hasFirst = true;
			builder.append(JsExpressionFactory.create(item).encode());
		}
		builder.append("]");
		return builder.toString();
	}
}
