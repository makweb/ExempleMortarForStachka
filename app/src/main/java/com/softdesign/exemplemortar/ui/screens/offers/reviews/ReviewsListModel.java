package com.softdesign.exemplemortar.ui.screens.offers.reviews;

import com.softdesign.exemplemortar.data.storage.StubStorage;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;
import com.softdesign.exemplemortar.ui.screens.base.AbstractModel;

import java.util.List;

/**
 * Created by Makweb on 05.04.2017.
 */

public class ReviewsListModel extends AbstractModel {
    private StubStorage mStubStorage = StubStorage.getInstance();
    private ReviewDto[] mHeavyData;

    public ReviewsListModel(){
        initHeavyData();
    }

    public List<ReviewDto> getReviewForProduct(ProductDto product) {
       return mStubStorage.getReviewForProduct(product);
    }

    //for example
    private void initHeavyData() {
        mHeavyData = new ReviewDto[300];
        mStubStorage.getReviewRandomGenerator().fill(mHeavyData);
    }
}
