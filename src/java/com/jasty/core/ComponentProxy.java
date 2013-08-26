package com.jasty.core;

import com.jasty.js.JsCall;
import com.jasty.js.JsContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This is the base class for all javascript component server-proxies.
 * The target component(s) is addressed by id or query
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public abstract class ComponentProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * To address component by id
     */
    private String id;
    /**
     * To address component(s) by jQuery selector
     */
    private transient String query;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return getId().replace('.', '_');
    }

    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * jQuery-component to dispatch proxy calls to the specific javascript
     * components. Dispatcher components is aware of the namespace and
     * of the call syntax for the javascript methods of this component family.
     *
     * @return name of the jQuery component
     */
    protected String getFamily() {
        return "jasty";
    }

    /**
     * Name of the proxied javascript component.
     * Default convention: javascript component name is the proxy class name
     *
     * @return component name
     */
    protected String getClassName() {
        return getClass().getSimpleName();
    }

    /**
     * This method generates corresponding javascript call of the proxied component
     * and registers it in the current scripting context.
     *
     * @param method method name
     * @param params arbitrary method parameters
     */
    public final void invoke(String method, Object ... params) {
        if(getId() == null && query == null) return;
        ArrayList<Object> p = new ArrayList<Object>();
        Collections.addAll(p, params);
        String selector = getId() == null ? "$('" + query + "').": "$$('" + getClientId() + "').";
        JsContext.add(new JsCall(selector + getFamily(), getClassName(), method, p));
    }

    public void addClass(String value) {
        invoke("addClass", value);
    }

    public void addClass(String value, int duration) {
        invoke("addClass", value, duration);
    }

    public void removeClass(String value) {
        invoke("removeClass", value);
    }

    public void removeClass(String value, int duration) {
        invoke("removeClass", value, duration);
    }

    public void remove() {
        invoke("remove");
    }

    public void replaceWith(Object fragment) {
        invoke("replaceWith", fragment);
    }

    /**
     * This method can be overridden to restore the internal state of the component
     * from the current request.
     *
     * @param parameterProvider all available data to restore component state from
     */
    public void restore(ParameterProvider parameterProvider) {

    }
}
