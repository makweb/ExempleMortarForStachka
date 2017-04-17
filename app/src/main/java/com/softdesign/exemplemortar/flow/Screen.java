package com.softdesign.exemplemortar.flow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Scope;

/**
 * Created by Makweb on 21.11.2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
public @interface Screen {
    int value();
}
