package com.jasty.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DefaultMethodInvoker implements MethodInvoker {

    private static final String EVENT_PREFIX = "EVT.";

    @Override
    public void invoke(Form form, ParameterProvider parameterProvider) {
        final String eventHandler = parameterProvider.getParameter("eventHandler");
        final EventArgs args = extractEventArgs(parameterProvider, form);
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
     * @param parameterProvider       request parameters
     * @param form      form, owning the event
     * @return          filled out EventArgs-object
     */
    private static EventArgs extractEventArgs(ParameterProvider parameterProvider, Form form) {

        EventArgs args = new EventArgs();
        for(String key : parameterProvider.getParameterNames()) {
            if(key.startsWith(EVENT_PREFIX)) {
                String value = parameterProvider.getParameter(key);
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
