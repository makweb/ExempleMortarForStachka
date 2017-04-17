package com.softdesign.exemplemortar.ui.screens.offers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.offers.product.ProductScreen;

import java.util.List;
import java.util.Locale;

import mortar.MortarScope;

/**
 * Created by Makweb on 04.04.2017.
 */

public class OfferAdapter extends PagerAdapter {
    private final List<ProductDto> mProducts;

    public OfferAdapter(List<ProductDto> products) {
        mProducts = products;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ProductDto product = mProducts.get(position);
        Context productContext = createProductContext(product, container.getContext());
        View newView = LayoutInflater.from(productContext).inflate(R.layout.screen_product, container, false);
        newView.setTag("Product"+product.getId());//добавляем тег к вью продукта
        container.addView(newView);
        return newView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        MortarScope screenScope = MortarScope.getScope(((View)object).getContext());
        container.removeView((View)object);
        screenScope.destroy();
    }

    private Context createProductContext(ProductDto productDto, Context parentContext) {
        MortarScope parentScope = MortarScope.getScope(parentContext);
        MortarScope childScope = null;

        ProductScreen productScreen = new ProductScreen(productDto);
        String scopeName = String.format(Locale.ENGLISH, "%s_%s", productScreen.getScopeName(), productDto.getId());

        if(parentScope.findChild(scopeName) == null) {

            final OfferScreen.Component parentComponent = ScreenScoper.getDaggerComponent(parentContext);
            ProductScreen.Component productComponent = (ProductScreen.Component) productScreen.createScreenComponent(parentComponent);

            childScope = parentScope.buildChild()
                    .withService(ScreenScoper.SERVICE_NAME, productComponent)
                    .build(scopeName);
        } else {
            childScope = parentScope.findChild(scopeName);
        }
        return childScope.createContext(parentContext);
    }
}
