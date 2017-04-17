package com.softdesign.exemplemortar.ui.screens.offers.product;

import com.softdesign.exemplemortar.data.storage.StubStorage;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.ui.screens.base.AbstractModel;

/**
 * Created by Makweb on 03.04.2017.
 */

class ProductModel extends AbstractModel{
    private ProductDto[] mHeavyData;
    private StubStorage mStubStorage = StubStorage.getInstance();

    public ProductModel() {
        initHeavyData();
    }

    //for example
    private void initHeavyData() {
        mHeavyData = new ProductDto[300];
        mStubStorage.getProductRandomGenerator().fill(mHeavyData);
    }
}
