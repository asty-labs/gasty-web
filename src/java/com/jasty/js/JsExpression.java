package com.jasty.js;

/**
 * Interface for all code wrappers, that need to be encoded to javascript
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public interface JsExpression {

    /**
     * Encodes the expression to javascript
     *
     * @return  javascript
     */
	String encode();
}
