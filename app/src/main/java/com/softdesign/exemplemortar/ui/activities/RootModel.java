package com.softdesign.exemplemortar.ui.activities;

import com.softdesign.exemplemortar.data.storage.StubStorage;
import com.softdesign.exemplemortar.data.storage.dto.ProfileDto;
import com.softdesign.exemplemortar.ui.screens.base.AbstractModel;

/**
 * Created by Makweb on 03.04.2017.
 */

public class RootModel extends AbstractModel {
    private StubStorage mStubStorage = StubStorage.getInstance();

    public RootModel() {
    }

    public ProfileDto getUserProfile() {
        return mStubStorage.getUserProfile();
    }
}
