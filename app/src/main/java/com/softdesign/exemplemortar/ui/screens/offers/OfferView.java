package com.softdesign.exemplemortar.ui.screens.offers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Makweb on 04.04.2017.
 */

public class OfferView extends AbstractView<OfferScreen.Presenter>{
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public OfferView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        ScreenScoper.<OfferScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void renderUi(List<ProductDto> products) {
        OfferAdapter adapter = new OfferAdapter(products);
        mViewPager.setAdapter(adapter);
    }
}
