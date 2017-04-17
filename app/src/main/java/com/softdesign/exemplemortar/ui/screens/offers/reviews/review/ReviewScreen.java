package com.softdesign.exemplemortar.ui.screens.offers.reviews.review;

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
import com.softdesign.exemplemortar.ui.screens.offers.reviews.ReviewListScreen;

import dagger.Provides;
import flow.TreeKey;
import mortar.MortarScope;

/**
 * Created by Makweb on 07.04.2017.
 */
@Screen(R.layout.screen_review)
public class ReviewScreen extends AbstractScreen<ReviewListScreen.Component> implements TreeKey{
    private final ReviewDto mReviewDto;
    private final ProductDto mProductDto;

    public ReviewScreen(ReviewDto reviewDto, ProductDto product) {
        mReviewDto = reviewDto;
        mProductDto = product;
    }

    @Override
    public Object createScreenComponent(ReviewListScreen.Component parentComponent) {
        return DaggerReviewScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new ReviewListScreen(mProductDto);
    }


    //region ======================== Parcelable implementation ========================
    protected ReviewScreen(Parcel in) {
        mReviewDto = in.readParcelable(ReviewDto.class.getClassLoader());
        mProductDto = in.readParcelable(ProductDto.class.getClassLoader());
    }

    public static final Creator<ReviewScreen> CREATOR = new Creator<ReviewScreen>() {
        @Override
        public ReviewScreen createFromParcel(Parcel in) {
            return new ReviewScreen(in);
        }

        @Override
        public ReviewScreen[] newArray(int size) {
            return new ReviewScreen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mReviewDto, flags);
        dest.writeParcelable(mProductDto, flags);
    }

    //endregion

    @dagger.Component(dependencies = ReviewListScreen.Component.class, modules = Module.class)
    @DaggerScope(ReviewScreen.class)
    public interface Component {
        void inject(Presenter presenter);
        void inject(ReviewView view);
        RootPresenter getRootPresenter();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(ReviewScreen.class)
        Presenter providePresenter() {
            return new Presenter(mReviewDto);
        }

        @Provides
        @DaggerScope(ReviewScreen.class)
        ReviewModel provideModel() {
            return new ReviewModel();
        }
    }

    public class Presenter extends AbstractPresenter<ReviewView, ReviewModel> {
        private final ReviewDto mReview;

        public Presenter(ReviewDto review) {
            mReview = review;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            getView().renderUi(mReview);
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ScreenScoper.<ReviewScreen.Component>getDaggerComponent(scope).inject(this);
        }
    }
}
