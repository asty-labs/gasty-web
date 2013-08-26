package com.jasty.core;

import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps all specific data, sent with a particular event by
 * a javascript component.
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class EventArgs {

    /**
     * This is id of the javascript component, which triggered the event.
     */
    private String srcId;
	private Map<String, String> args = new HashMap<String, String>();
	
	public String get(String key) {
		return args.get(key);
	}

    public String put(String key, String value) {
        return args.put(key, value);
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
        args.put("source", srcId);
    }
}
