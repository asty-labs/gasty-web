package com.jasty.core;

import java.lang.reflect.InvocationTargetException;

public interface MethodInvoker {

    void invoke(Form form, ParameterProvider parameterProvider);

}
