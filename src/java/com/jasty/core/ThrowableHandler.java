package com.jasty.core;

import com.jasty.js.JsExpression;

/**
 * Interface for generating javascript expression in response to an exception
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public interface ThrowableHandler {

	JsExpression handle(Throwable t);
}
