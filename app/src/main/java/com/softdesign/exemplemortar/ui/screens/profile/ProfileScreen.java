package com.softdesign.exemplemortar.ui.screens.profile;

import android.os.Bundle;
import android.os.Parcel;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProfileDto;
import com.softdesign.exemplemortar.flow.Screen;
import com.softdesign.exemplemortar.mortar.DaggerScope;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.activities.RootActivity;
import com.softdesign.exemplemortar.ui.activities.RootModel;
import com.softdesign.exemplemortar.ui.activities.RootPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;

import dagger.Provides;
import mortar.MortarScope;

/**
 * Created by Makweb on 10.04.2017.
 */
@Screen(R.layout.screen_profile)
public class ProfileScreen extends AbstractScreen<RootActivity.RootComponent>{
    public ProfileScreen() {
    }

    public static final Creator<ProfileScreen> CREATOR = new Creator<ProfileScreen>() {
        @Override
        public ProfileScreen createFromParcel(Parcel in) {
            return new ProfileScreen();
        }

        @Override
        public ProfileScreen[] newArray(int size) {
            return new ProfileScreen[size];
        }
    };

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerProfileScreen_Component.builder()
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
    @DaggerScope(ProfileScreen.class)
    public interface Component {
        void inject(Presenter presenter);
        void inject(ProfileView view);
        RootPresenter getRootPresenter();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ProfileScreen.class)
        Presenter providePresenter() {
            return new Presenter();
        }
    }

    public class Presenter extends AbstractPresenter<ProfileView, RootModel>{

        @Override
        protected void initDagger(MortarScope scope) {
            ScreenScoper.<Component>getDaggerComponent(scope).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            ProfileDto profile = mModel.getUserProfile();
            getView().renderUi(profile);
        }
    }
}
