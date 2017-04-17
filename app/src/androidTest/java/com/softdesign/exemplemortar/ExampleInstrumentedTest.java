package com.softdesign.exemplemortar;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;

import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.activities.RootActivity;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.profile.ProfileScreen;
import com.softdesign.exemplemortar.ui.screens.profile.ProfileView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<RootActivity> mActivityTestRule = new ActivityTestRule<>(RootActivity.class);
    private RootActivity mRootActivity;

    @Before
    public void setUp() throws Exception {
        mRootActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void useAppContext() throws Exception {
        assertTrue(mRootActivity instanceof RootActivity);
    }

    @Test
    public void createViewWithDependency_ProfileView_PRESENTER_EXIST() throws Exception {
        //given
        ProfileScreen screen = new ProfileScreen();
       Context context = ScreenScoper.createScreenContext(mRootActivity, screen);
        ProfileView actualView = (ProfileView) LayoutInflater.from(context).inflate(R.layout.screen_profile, null, false);

        //when
        Object component = ScreenScoper.getDaggerComponent(actualView.getContext());
        AbstractPresenter actualPresenter = actualView.getPresenter();

        //then
        assertTrue(component instanceof ProfileScreen.Component);
        assertNotNull(actualPresenter);
        assertTrue(actualPresenter instanceof ProfileScreen.Presenter);
    }
}
