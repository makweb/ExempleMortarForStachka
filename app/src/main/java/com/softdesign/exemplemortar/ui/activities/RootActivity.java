package com.softdesign.exemplemortar.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softdesign.exemplemortar.BuildConfig;
import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProfileDto;
import com.softdesign.exemplemortar.flow.BasicKeyParceler;
import com.softdesign.exemplemortar.flow.TreeKeyDispatcher;
import com.softdesign.exemplemortar.mortar.DaggerScope;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.IRootView;
import com.softdesign.exemplemortar.ui.screens.offers.OfferScreen;
import com.softdesign.exemplemortar.ui.screens.profile.ProfileScreen;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Component;
import dagger.Module;
import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class RootActivity extends AppCompatActivity implements IRootView, NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "RootActivity";
    @Inject
    RootPresenter mRootPresenter;
    @BindView(R.id.root_frame)
    public FrameLayout mRootContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new OfferScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .keyParceler(new BasicKeyParceler())
                .install();

        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), getScopeName());

        if (activityScope == null) {
            activityScope = MortarScope.buildChild(getApplicationContext()) //
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(ScreenScoper.SERVICE_NAME, createDaggerComponent())
                    .build(getScopeName());
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }

    public String getScopeName() {
        return this.getClass().getName();
    }

    public Object createDaggerComponent() {
        return DaggerRootActivity_RootComponent.builder()
                .rootModule(new RootActivity.RootModule())
                .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ScreenScoper.<RootComponent>getDaggerComponent(this).inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mRootPresenter.takeView(this);
    }

    public void initDrawer(ProfileDto profile) {
        View header = mNavigationView.getHeaderView(0);
        HeaderViewHolder headerViewHolder = new HeaderViewHolder(header);
        headerViewHolder.mDrawerAvatarIv.setImageDrawable(profile.getAvatar());
        headerViewHolder.mDrawerNameTxt.setText(profile.getFullName());
        headerViewHolder.mDrawerEmailTxt.setText(profile.getEmail());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRootPresenter.dropView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Что то пошло не так, попроббуйте позже");
            // TODO: 03.04.2017 send to crashlytics
        }
    }

    @Override
    public void showLoad() {
        // TODO: show Load
    }

    @Override
    public void hideLoad() {
        // TODO: hide Load
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_profile:
                Flow.get(this).set(new ProfileScreen());
                break;
            case R.id.nav_offers:
                Flow.get(this).set(new OfferScreen());
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Component(modules = {RootModule.class})
    @DaggerScope(RootActivity.class)
    public interface RootComponent {
        void inject(RootActivity view);
        void inject(RootPresenter presenter);

        RootPresenter getRootPresenter();
        RootModel getRootModel();
    }

    @Module
    public static class RootModule {

        @Provides
        @DaggerScope(RootActivity.class)
        RootPresenter provideRootPresenter() {
            return new RootPresenter();
        }

        @Provides
        @DaggerScope(RootActivity.class)
        RootModel provideRootModel() {
            return new RootModel();
        }
    }

    protected static class HeaderViewHolder {

        @BindView(R.id.drawer_avatar_iv)
        ImageView mDrawerAvatarIv;
        @BindView(R.id.drawer_name_txt)
        TextView mDrawerNameTxt;
        @BindView(R.id.drawer_email_txt)
        TextView mDrawerEmailTxt;

        HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
