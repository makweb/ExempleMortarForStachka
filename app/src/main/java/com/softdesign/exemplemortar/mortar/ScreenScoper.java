package com.softdesign.exemplemortar.mortar;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flow.TreeKey;
import mortar.MortarScope;

/**
 * Created by Makweb on 03.04.2017.
 */

public class ScreenScoper {
    private static final String TAG ="ScreenScoper";
    public static final String SERVICE_NAME = "DEPENDENCY_SERVICE";

    @VisibleForTesting
    private static MortarScope getScreenScope(Context context, AbstractScreen screen) {
        MortarScope parentScope = MortarScope.getScope(context);

        MortarScope childScope = parentScope.findChild(screen.getScopeName());
        if (childScope != null) {
            return childScope;
        }

        Object parentComponent = parentScope.getService(SERVICE_NAME);
        Object screenComponent = screen.createScreenComponent(parentComponent);

        MortarScope.Builder builder = parentScope.buildChild()
                .withService(SERVICE_NAME, screenComponent);

        return builder.build(screen.getScopeName());
    }

    public static Context createScreenContext(Context rootContext, AbstractScreen screen) {
        Context context = rootContext;
        List<AbstractScreen> screens = new ArrayList<>();
        MortarScope parScope;

        while (screen instanceof TreeKey) {
            screens.add(screen);
            screen = (AbstractScreen) ((TreeKey) screen).getParentKey();
        }
        screens.add(screen);

        Collections.reverse(screens);
        for (AbstractScreen abstractScreen : screens) {
            parScope = getScreenScope(context, abstractScreen);
            context = parScope.createContext(context);
        }
        return context;
    }

    public static void destroyTearDownScope(Context context, AbstractScreen screen) {
        while (screen instanceof TreeKey) {
            screen = (AbstractScreen) ((TreeKey) screen).getParentKey();
        }
        MortarScope ms = MortarScope.getScope(context).findChild(screen.getScopeName());
        Log.e(TAG, "destroyTearDownScope scope: " + ms.getName());
        ms.destroy();
    }

    @SuppressWarnings({"unchecked", "WrongConstant"})
    public static <T> T getDaggerComponent(Context context) {
        return (T) context.getSystemService(SERVICE_NAME);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDaggerComponent(MortarScope scope) {
        return (T) scope.getService(SERVICE_NAME);
    }
}
