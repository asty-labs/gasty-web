package com.jasty.core;

/**
 * This interface is an abstraction to render main views or view fragments
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
public interface ViewRenderer {

    String renderMainView(Form form, Object model);
    
    String renderFragment(Form form, String fragmentName, Object model);

}
