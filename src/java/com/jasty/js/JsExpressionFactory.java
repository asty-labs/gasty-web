package com.jasty.js;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Factory to wrap any object in the appropriate javascript expression
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsExpressionFactory {

	public static JsExpression create(Object value) {
		if(value == null)
			return new JsVariable("null");
        if(value instanceof JsSerializable)
            return ((JsSerializable)value).toJsExpression();
        if(value instanceof String)
            return new JsString((String)value);
		if(value instanceof Map)
			return new JsMap((Map<String, ?>)value);
        if(value.getClass().isArray())
            return new JsList((Object[])value);
		if(value instanceof Iterable)
			return new JsList((Iterable<?>)value);
		if(value instanceof JsExpression)
			return (JsExpression)value;
		if(value instanceof Integer)
			return new JsNumber((Integer)value);
		if(value instanceof Long)
			return new JsNumber((Long)value);
		if(value instanceof Byte)
			return new JsNumber((Byte)value);
		if(value instanceof Short)
			return new JsNumber((Short)value);
		if(value instanceof Float)
			return new JsNumber((Float)value);
		if(value instanceof Double)
			return new JsNumber((Double)value);
		if(value instanceof BigDecimal)
			return new JsNumber((BigDecimal)value);
        if(value instanceof Boolean)
            return new JsBoolean((Boolean)value);
		if(value instanceof Character)
			return new JsString(value.toString());
		return new JsString(value.toString());
	}
}
