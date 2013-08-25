package com.jasty.servlet;

import com.jasty.core.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class FormEngineFactory {

    static FormEngineFactory instance;

    public static void setInstance(FormEngineFactory instance) {
        FormEngineFactory.instance = instance;
    }

    public static FormEngineFactory getInstance() {
        return instance;
    }

    public abstract FormEngine getFormEngine(ServletRequest request, ServletResponse response);
}
