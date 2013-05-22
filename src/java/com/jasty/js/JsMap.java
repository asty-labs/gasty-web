package com.jasty.js;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents maps
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsMap implements JsExpression {

	private Map<String, Object> map = new LinkedHashMap<String, Object>();
	
	public JsMap() {
		
	}
	
	public JsMap(Map<String, ?> map) {
		this.map.putAll(map);
	}
	
	@Override
	public String encode() {
		StringBuilder builder = new StringBuilder("{");
		boolean hasFirst = false;
		for(String key : map.keySet()) {
			if(hasFirst)
				builder.append(", ");
			else
				hasFirst = true;
			builder.append(JsExpressionFactory.create(key).encode());
			builder.append(":");
			builder.append(JsExpressionFactory.create(map.get(key)).encode());
		}
		builder.append("}");
		return builder.toString();
	}
}
