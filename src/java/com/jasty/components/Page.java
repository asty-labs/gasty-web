package com.jasty.components;

import com.jasty.core.ComponentProxy;

/**
 * Created with IntelliJ IDEA.
 * User: stas
 * Date: 1/7/13
 * Time: 9:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Page extends ComponentProxy {

    static Page instance = new Page();

    private Page() {
        setQuery("body");
    }

    public static void reload() {
        instance.invoke("reload");
    }

    public static void sessionTimeout(String message) {
        instance.invoke("sessionTimeout", message);
    }
}
