package com.softdesign.exemplemortar.ui.screens.offers.reviews.review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractView;

import butterknife.BindView;

/**
 * Created by Makweb on 07.04.2017.
 */

public class ReviewView extends AbstractView<ReviewScreen.Presenter> {
    @BindView(R.id.avatar_iv)
    protected ImageView mAvatarIv;
    @BindView(R.id.user_name_txt)
    protected TextView mUserNameTxt;
    @BindView(R.id.rating_bar)
    protected AppCompatRatingBar mRatingBar;
    @BindView(R.id.date_txt)
    protected TextView mDateTxt;
    @BindView(R.id.full_review_txt)
    protected TextView mReviewTxt;
    private static final String TAG ="ReviewView";

    public ReviewView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        ScreenScoper.<ReviewScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void renderUi(ReviewDto review) {
        mAvatarIv.setImageDrawable(review.getAvatar());
        mUserNameTxt.setText(review.getName());
        mRatingBar.setRating(review.getRating());
        mDateTxt.setText(review.getDate());
        mReviewTxt.setText(review.getReview());
    }
}
