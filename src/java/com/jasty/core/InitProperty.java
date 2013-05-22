package com.jasty.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark the fields to be automatically added to the
 * initialisation options of the javascript component (parameter map
 * of the init-method)
 *
 * @author Stanislav Tkachev
 * @version 1.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InitProperty {
}
