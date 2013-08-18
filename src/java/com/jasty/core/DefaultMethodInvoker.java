package com.jasty.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DefaultMethodInvoker implements MethodInvoker {

    private static final String EVENT_PREFIX = "EVT.";

    @Override
    public void invoke(Form form, ParameterProvider parameterProvider) {
        final String eventHandler = parameterProvider.getParameter("eventHandler");
        final EventArgs args = extractEventArgs(parameterProvider.getParameterMap(), form);
        try {
            Method method = form.getClass().getMethod(eventHandler, EventArgs.class);
            method.invoke(form, args);
        } catch (NoSuchMethodException e) {
            throw new FormEngine.FormEngineException(e);
        } catch (InvocationTargetException e) {
            throw new FormEngine.FormEngineException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new FormEngine.FormEngineException(e);
        }
    }

    /**
     * Fills EventArgs object from the request parameters
     *
     * @param map       request parameters
     * @param form      form, owning the event
     * @return          filled out EventArgs-object
     */
    private static EventArgs extractEventArgs(Map<String, Object> map, Form form) {

        EventArgs args = new EventArgs();
        for(String key : map.keySet()) {
            if(key.startsWith(EVENT_PREFIX)) {
                String value = (String)map.get(key);
                key = key.substring(EVENT_PREFIX.length());
                if("srcId".equals(key))
                    args.setSrcId(value.substring(form.getId().length() + 1));
                else
                    args.put(key, value);
            }
        }
        return args;
    }
}
