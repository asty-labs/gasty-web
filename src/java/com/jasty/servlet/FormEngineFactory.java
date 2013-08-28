package com.jasty.servlet;

import com.jasty.core.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FormEngineFactory {

    static FormEngineFactory instance;

    public static void setInstance(FormEngineFactory instance) {
        FormEngineFactory.instance = instance;
    }

    public static FormEngineFactory getInstance() {
        return instance;
    }

    public abstract FormEngine getFormEngine(HttpServletRequest request, HttpServletResponse response);
}
