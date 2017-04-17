package com.softdesign.exemplemortar.ui.screens.offers;

import android.os.Bundle;
import android.os.Parcel;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.flow.Screen;
import com.softdesign.exemplemortar.mortar.DaggerScope;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.activities.RootActivity;
import com.softdesign.exemplemortar.ui.activities.RootPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;

import java.util.List;

import dagger.Provides;
import mortar.MortarScope;
@Screen(R.layout.screen_offer)
public class OfferScreen extends AbstractScreen<RootActivity.RootComponent>{

    public OfferScreen() {}

    public static final Creator<OfferScreen> CREATOR = new Creator<OfferScreen>() {
        @Override
        public OfferScreen createFromParcel(Parcel in) {
            return new OfferScreen();
        }

        @Override
        public OfferScreen[] newArray(int size) {
            return new OfferScreen[size];
        }
    };

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerOfferScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(OfferScreen.class)
    public interface Component {
        void inject(OfferScreen.Presenter presenter);
        void inject(OfferView view);
        RootPresenter getRootPresenter();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(OfferScreen.class)
        OfferScreen.Presenter providePresenter() {
            return new OfferScreen.Presenter();
        }

        @Provides
        @DaggerScope(OfferScreen.class)
        OfferModel provideModel() {
            return new OfferModel();
        }
    }

    public class Presenter extends AbstractPresenter<OfferView, OfferModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            ScreenScoper.<OfferScreen.Component>getDaggerComponent(scope).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            List<ProductDto> products = mModel.getProducts();
            getView().renderUi(products);
        }
    }
}
