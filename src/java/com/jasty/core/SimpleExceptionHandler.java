package com.jasty.core;

public class SimpleExceptionHandler implements MethodInvoker {

    private MethodInvoker methodInvoker;

    public SimpleExceptionHandler(MethodInvoker methodInvoker) {
        this.methodInvoker = methodInvoker;
    }

    @Override
    public void invoke(Form form, ParameterProvider parameterProvider) {

        try {
            methodInvoker.invoke(form, parameterProvider);
        }
        catch(FormEngine.FormEngineException e) {
            form.invoke("errors", e.getCause().getMessage());
        }
        catch(Throwable t) {
            form.invoke("errors", t.getMessage());
        }
    }
}
