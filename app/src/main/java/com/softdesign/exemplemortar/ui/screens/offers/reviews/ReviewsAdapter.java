package com.softdesign.exemplemortar.ui.screens.offers.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<ReviewDto> mReviews;
    private final ViewHolder.IListItemListener mItemListener;

    public ReviewsAdapter(List<ReviewDto> items, ViewHolder.IListItemListener listener) {
        mReviews = items;
       mItemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view, mItemListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ReviewDto review = mReviews.get(position);
        holder.mAvatarIv.setImageDrawable(review.getAvatar());
        holder.mUserNameTxt.setText(review.getName());
        holder.mDateTxt.setText(review.getDate());
        holder.mReviewTxt.setText(review.getReview());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.avatar_iv)
        ImageView mAvatarIv;
        @BindView(R.id.drawer_name_txt)
        TextView mUserNameTxt;
        @BindView(R.id.date_txt)
        TextView mDateTxt;
        @BindView(R.id.review_txt)
        TextView mReviewTxt;
        private final IListItemListener mItemClickListener;

        public ViewHolder(View view, IListItemListener itemListener) {
            super(view);
            ButterKnife.bind(this, view);
            mItemClickListener = itemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClicked(getAdapterPosition(),v);
            }
        }

        public interface IListItemListener {
            void onItemClicked(int position, View view);
        }

    }
}
