package com.softdesign.exemplemortar.ui.screens.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Makweb on 03.04.2017.
 */

public abstract class AbstractView<P extends AbstractPresenter> extends FrameLayout {
    @Inject
    protected P mPresenter;

    public AbstractView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode()) {
            initDagger(context);
        }
    }

    protected abstract void initDagger(Context context);

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }
}
