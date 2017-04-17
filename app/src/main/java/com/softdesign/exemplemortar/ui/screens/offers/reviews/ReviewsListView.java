package com.softdesign.exemplemortar.ui.screens.offers.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Makweb on 05.04.2017.
 */

public class ReviewsListView extends AbstractView<ReviewListScreen.Presenter>{
    @BindView(R.id.review_list)
    RecyclerView mReviewList;
    public ReviewsListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        ScreenScoper.<ReviewListScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void renderUi(List<ReviewDto> products) {
        ReviewsAdapter adapter = new ReviewsAdapter(products, new ReviewsAdapter.ViewHolder.IListItemListener() {
            @Override
            public void onItemClicked(int position, View view) {
                mPresenter.clickOnReview(position);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mReviewList.setLayoutManager(llm);
        mReviewList.setAdapter(adapter);
    }
}
