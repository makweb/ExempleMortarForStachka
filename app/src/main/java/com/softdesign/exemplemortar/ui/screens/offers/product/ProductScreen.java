package com.softdesign.exemplemortar.ui.screens.offers.product;


import android.os.Bundle;
import android.os.Parcel;

import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.mortar.DaggerScope;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.activities.RootPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;
import com.softdesign.exemplemortar.ui.screens.offers.OfferScreen;
import com.softdesign.exemplemortar.ui.screens.offers.reviews.ReviewListScreen;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

public class ProductScreen extends AbstractScreen<OfferScreen.Component>{

    private final ProductDto mProductDto;

    public ProductScreen(ProductDto productDto) {
        mProductDto = productDto;
    }

    protected ProductScreen(Parcel in) {
        mProductDto = in.readParcelable(ProductDto.class.getClassLoader());
    }

    public static final Creator<ProductScreen> CREATOR = new Creator<ProductScreen>() {
        @Override
        public ProductScreen createFromParcel(Parcel in) {
            return new ProductScreen(in);
        }

        @Override
        public ProductScreen[] newArray(int size) {
            return new ProductScreen[size];
        }
    };

    @Override
    public Object createScreenComponent(OfferScreen.Component parentComponent) {
        return DaggerProductScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mProductDto, flags);
    }


    @dagger.Component(dependencies = OfferScreen.Component.class, modules = ProductScreen.Module.class)
    @DaggerScope(ProductScreen.class)
    public interface Component {
        void inject(ProductScreen.Presenter presenter);
        void inject(ProductView view);
        RootPresenter getRootPresenter();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ProductScreen.class)
        ProductScreen.Presenter providePresenter() {
            return new ProductScreen.Presenter(mProductDto);
        }

        @Provides
        @DaggerScope(ProductScreen.class)
        ProductModel provideModel() {
            return new ProductModel();
        }
    }

    public static class Presenter extends AbstractPresenter<ProductView, ProductModel> {
        private final ProductDto mProduct;

        public Presenter(ProductDto product) {
            mProduct = product;
        }


        @Override
        protected void initDagger(MortarScope scope) {
            ScreenScoper.<ProductScreen.Component>getDaggerComponent(scope).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            getView().renderUi(mProduct);
        }

        public void clickOnReviews() {
            Flow.get(getView()).set(new ReviewListScreen(mProduct));
        }
    }
}
