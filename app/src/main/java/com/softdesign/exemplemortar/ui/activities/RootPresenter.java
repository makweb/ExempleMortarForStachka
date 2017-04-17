package com.softdesign.exemplemortar.ui.activities;

import android.os.Bundle;

import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.IRootView;

import javax.inject.Inject;

import mortar.MortarScope;
import mortar.Presenter;
import mortar.bundler.BundleService;

/**
 * Created by Makweb on 03.04.2017.
 */

public class RootPresenter extends Presenter<IRootView> {
    @Inject
    RootModel mRootModel;

    @Override
    protected void onEnterScope(MortarScope scope) {
        ScreenScoper.<RootActivity.RootComponent>getDaggerComponent(scope).inject(this);
    }

    @Override
    protected BundleService extractBundleService(IRootView view) {
        return BundleService.getBundleService((RootActivity) view);
    }

    public RootActivity getRootView() {
        return ((RootActivity) getView());
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        getRootView().initDrawer(mRootModel.getUserProfile());
    }
}
