package com.softdesign.exemplemortar;

import android.app.Application;
import android.content.Context;

import mortar.MortarScope;

/**
 * Created by Makweb on 03.04.2017.
 */

public class App extends Application {

    private static Context sAppContext;
    private MortarScope mRootScope;

    public static Context getAppContext() {
        return sAppContext;
    }

    @Override
    public Object getSystemService(String name) {
        return (mRootScope != null && mRootScope.hasService(name)) ? mRootScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = getApplicationContext();
        mRootScope = MortarScope.buildRootScope()
                .build("Root");
    }
}
