package com.softdesign.exemplemortar.ui.screens.offers.reviews;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;
import com.softdesign.exemplemortar.flow.Screen;
import com.softdesign.exemplemortar.mortar.DaggerScope;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.activities.RootPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractScreen;
import com.softdesign.exemplemortar.ui.screens.offers.OfferScreen;
import com.softdesign.exemplemortar.ui.screens.offers.reviews.review.ReviewScreen;

import java.util.List;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
@Screen(R.layout.review_list)
public class ReviewListScreen extends AbstractScreen<OfferScreen.Component> implements TreeKey{
    private final ProductDto mProductDto;

    public ReviewListScreen(ProductDto productDto) {
        mProductDto = productDto;
    }

    protected ReviewListScreen(Parcel in) {
        mProductDto = in.readParcelable(ProductDto.class.getClassLoader());
    }

    public static final Creator<ReviewListScreen> CREATOR = new Creator<ReviewListScreen>() {
        @Override
        public ReviewListScreen createFromParcel(Parcel in) {
            return new ReviewListScreen(in);
        }

        @Override
        public ReviewListScreen[] newArray(int size) {
            return new ReviewListScreen[size];
        }
    };

    @Override
    public Object createScreenComponent(OfferScreen.Component parentComponent) {
        return DaggerReviewListScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new OfferScreen();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mProductDto, flags);
    }

    @dagger.Component(dependencies = OfferScreen.Component.class, modules = Module.class)
    @DaggerScope(ReviewListScreen.class)
    public interface Component {
        void inject(ReviewListScreen.Presenter presenter);
        void inject(ReviewsListView view);
        RootPresenter getRootPresenter();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ReviewListScreen.class)
        Presenter providePresenter() {
            return new ReviewListScreen.Presenter(mProductDto);
        }

        @Provides
        @DaggerScope(ReviewListScreen.class)
        ReviewsListModel provideModel() {
            return new ReviewsListModel();
        }
    }

    public static class Presenter extends AbstractPresenter<ReviewsListView, ReviewsListModel> {
        private final ProductDto mProduct;
        private List<ReviewDto> mReviews;


        public Presenter(ProductDto product) {
            mProduct = product;
        }


        @Override
        protected void initDagger(MortarScope scope) {
            ScreenScoper.<ReviewListScreen.Component>getDaggerComponent(scope).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            mReviews = mModel.getReviewForProduct(mProduct);
            getView().renderUi(mReviews);
        }

        public void clickOnReview(int position) {
            Flow.get(getView()).set(new ReviewScreen(mReviews.get(position), mProduct));
        }
    }
}
