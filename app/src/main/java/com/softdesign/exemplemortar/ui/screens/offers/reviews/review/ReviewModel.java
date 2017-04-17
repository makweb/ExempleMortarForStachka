package com.softdesign.exemplemortar.ui.screens.offers.reviews.review;

import com.softdesign.exemplemortar.data.storage.StubStorage;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;
import com.softdesign.exemplemortar.ui.screens.base.AbstractModel;

/**
 * Created by Makweb on 07.04.2017.
 */

public class ReviewModel extends AbstractModel {
    private StubStorage mStubStorage = StubStorage.getInstance();
    private ReviewDto[] mHeavyData;

    public ReviewModel() {
        initHeavyData();
    }

    //for example
    private void initHeavyData() {
        mHeavyData = new ReviewDto[300];
        mStubStorage.getReviewRandomGenerator().fill(mHeavyData);
    }
}
