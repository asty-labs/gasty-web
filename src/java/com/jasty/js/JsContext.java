package com.jasty.js;

/**
 * This class is a registry to collect javascript expressions
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class JsContext {

	private static ThreadLocal<JsSequence> contexts = new ThreadLocal<JsSequence>();

    /**
     * This method is called by whoever wants to register a javascript call
     *
     * @param call call representation
     */
	public static void add(final JsCall call) {
		JsSequence fragment = contexts.get();
		if(fragment != null) fragment.add(call);
	}

    /**
     * This method provides context for all registrations within the specified delegate
     *
     * @param action    delegate, which may contain JsContext.add calls
     * @return          collected javascript expression
     */
	public static JsExpression execute(Runnable action) {
		JsSequence previous = contexts.get();
		try {
			JsSequence fragment = new JsSequence();
			contexts.set(fragment);
			action.run();
			return fragment;
		}
		finally {
			contexts.set(previous);
		}
	}
}
