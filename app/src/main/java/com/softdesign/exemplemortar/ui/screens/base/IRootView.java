package com.softdesign.exemplemortar.ui.screens.base;

/**
 * Created by Makweb on 03.04.2017.
 */

public interface IRootView {
    void showMessage(String message);

    void showError(Throwable e);

    void showLoad();

    void hideLoad();
}
