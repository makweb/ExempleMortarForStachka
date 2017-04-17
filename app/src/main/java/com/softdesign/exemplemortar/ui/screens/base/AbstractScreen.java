package com.softdesign.exemplemortar.ui.screens.base;

import android.os.Parcelable;

import flow.ClassKey;

/**
 * Created by Makweb on 03.04.2017.
 */

public abstract class AbstractScreen<C> extends ClassKey implements Parcelable{

    public String getScopeName(){
        return getClass().getName();
    };

    public abstract Object createScreenComponent(C parentComponent);
}
