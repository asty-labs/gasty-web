package com.jasty.js;

/**
 * This interface is used to convert class to the javascript expression.
 * It should be implemented by all classes, that can be sent to the client. For instance,
 * as method parameters.
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public interface JsSerializable {

    JsExpression toJsExpression();
}
