package com.jasty.core;

import com.jasty.js.JsClosure;
import com.jasty.js.JsExpression;
import com.jasty.js.JsMap;
import com.jasty.js.JsSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps rendered HTML and corresponding javascript code (e.g. initialization)
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public class HtmlFragment implements JsSerializable {

    private String html;
    private JsClosure script;
    
    public HtmlFragment(String html, JsClosure script) {
        this.html = html;
        this.script = script;
    }

    public String getHtml() {
        return html;
    }

    public JsClosure getScript() {
        return script;
    }

    @Override
    public JsExpression toJsExpression() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("html", html);
        map.put("script", script);
        return new JsMap(map);
    }
}
