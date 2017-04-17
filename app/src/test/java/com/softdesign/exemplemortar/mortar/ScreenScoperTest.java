package com.softdesign.exemplemortar.mortar;

import android.content.Context;

import com.softdesign.exemplemortar.ui.activities.RootActivity;
import com.softdesign.exemplemortar.ui.screens.profile.ProfileScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

/**
 * Created by Makweb on 10.04.2017.
 */
public class ScreenScoperTest {

    @Spy
    Context mockAppContext;
    @Spy
    RootActivity mockRootActivity;

    @SuppressWarnings("WrongConstant")
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        String mBundleServiceName = BundleServiceRunner.SERVICE_NAME;
        BundleServiceRunner bundleServiceRunner = new BundleServiceRunner();
        String mMortarScope = MortarScope.class.getName();
        MortarScope rootScope = MortarScope.buildRootScope().build("Root");

        MortarScope mMockMortarScope = MortarScope.buildRootScope()
                .withService(mBundleServiceName, bundleServiceRunner)
                //.withService(DaggerService.SERVICE_NAME, mock(AuthScreen.Component.class))
                .build("MockRoot");

        given(mockRootActivity.getApplicationContext()).willReturn(mockAppContext);
        given(mockAppContext.getSystemService(mMortarScope)).willReturn(rootScope);
        given(mockRootActivity.getSystemService(mBundleServiceName)).willReturn(bundleServiceRunner);
        given(mockRootActivity.getSystemService(mMortarScope)).willReturn(mMockMortarScope);

    }

    @Test
    public void getContext_ProfileScreen_SCOPE_NAMES_EQ() throws Exception {
        //given
        ProfileScreen screen = new ProfileScreen();
        Context context = ScreenScoper.createScreenContext(mockRootActivity, screen);

        //when
        MortarScope scope = MortarScope.getScope(context);

        //then
        assertEquals(scope.getName(), screen.getScopeName());
    }
}