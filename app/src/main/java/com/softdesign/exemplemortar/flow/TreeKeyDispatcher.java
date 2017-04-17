package com.softdesign.exemplemortar.flow;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;

import flow.Direction;
import flow.Dispatcher;
import flow.State;
import flow.Traversal;
import flow.TraversalCallback;
import flow.TreeKey;
import mortar.MortarScope;

/**
 * Created by Makweb on 21.11.2016.
 */

public class TreeKeyDispatcher implements Dispatcher {
    private static final String TAG = "TreeKeyDispatcher";
    private Activity mActivity;

    public TreeKeyDispatcher(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void dispatch(@NonNull Traversal traversal, @NonNull TraversalCallback callback) {
        State inState = traversal.getState(traversal.destination.top());
        State outState = traversal.origin == null ? null : traversal.getState(traversal.origin.top());

        AbstractScreen inKey = inState.getKey();
        AbstractScreen outKey = outState == null ? null : (AbstractScreen) outState.getKey();

        FrameLayout rootFrame = (FrameLayout) mActivity.findViewById(R.id.root_frame);
        View currentView = rootFrame.getChildAt(0);

        if (inKey.equals(outKey)) {
            callback.onTraversalCompleted();
            return;
        }

        //create new view
        Screen screen = inKey.getClass().getAnnotation(Screen.class);
        if (screen == null) {
            throw new IllegalStateException("@Screen annotation is missing on screen " + inKey.getScopeName());
        }

        //save out state
        if (outState != null) {
            outState.save(currentView);
        }

        int layout = screen.value();
        Context screenContext = ScreenScoper.createScreenContext(mActivity, inKey);
        Context flowContext = traversal.createContext(inKey, screenContext);
        LayoutInflater inflater = LayoutInflater.from(flowContext);
        View newView = inflater.inflate(layout, rootFrame, false);

        //restore state to new view
        inState.restore(newView);

        //delete old view
        if (currentView != null) {
            rootFrame.removeView(currentView);
            if (traversal.direction == Direction.BACKWARD) {
                MortarScope ms = MortarScope.getScope(currentView.getContext());
                Log.e(TAG, "dispatch: " + ms.getName() + " was destroyed");
                ms.destroy();
            } else if (!(inKey instanceof TreeKey) && outKey != null) {
                ScreenScoper.destroyTearDownScope(mActivity, outKey);
            }
        }

        rootFrame.addView(newView);
        callback.onTraversalCompleted();
    }
}
