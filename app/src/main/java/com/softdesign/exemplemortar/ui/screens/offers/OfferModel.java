package com.softdesign.exemplemortar.ui.screens.offers;

import com.softdesign.exemplemortar.data.storage.StubStorage;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.ui.screens.base.AbstractModel;

import java.util.List;

class OfferModel extends AbstractModel {
    private StubStorage mStubStorage = StubStorage.getInstance();
    private ProductDto[] mHeavyData;

    OfferModel() {
        initHeavyData();
    }

    List<ProductDto> getProducts() {
        return mStubStorage.getProducts();
    }

    //for example
    private void initHeavyData() {
        mHeavyData = new ProductDto[300];
        mStubStorage.getProductRandomGenerator().fill(mHeavyData);
    }
}
