package com.softdesign.exemplemortar.ui.screens.base;

import com.softdesign.exemplemortar.ui.activities.RootActivity;
import com.softdesign.exemplemortar.ui.activities.RootPresenter;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.ViewPresenter;

/**
 * Created by Makweb on 03.04.2017.
 */

public abstract class AbstractPresenter<V extends AbstractView, M extends AbstractModel> extends ViewPresenter<V> {
    private final String TAG = this.getClass().getSimpleName();

    @Inject
    public M mModel;

    @Inject
    public RootPresenter mRootPresenter;

    @Override
    protected void onEnterScope(MortarScope scope) {
        super.onEnterScope(scope);
        initDagger(scope);
    }

    public RootActivity getRootView() {
        return mRootPresenter.getRootView();
    }

    protected abstract void initDagger(MortarScope scope);
}
