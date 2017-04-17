package com.softdesign.exemplemortar.data.storage.dto;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Makweb on 05.04.2017.
 */

public class ReviewDto implements Parcelable{
    private int id;
    private Drawable avatar;
    private String name;
    private String review;
    private String date;
    private float rating;

    public ReviewDto() {
    }

    protected ReviewDto(Parcel in) {
        id = in.readInt();
        Bitmap bitmap = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
        if ( bitmap != null ) {
            avatar = new BitmapDrawable(bitmap);
        }
        else {
            avatar = null;
        }
        name = in.readString();
        review = in.readString();
        date = in.readString();
        rating = in.readFloat();
    }

    public static final Creator<ReviewDto> CREATOR = new Creator<ReviewDto>() {
        @Override
        public ReviewDto createFromParcel(Parcel in) {
            return new ReviewDto(in);
        }

        @Override
        public ReviewDto[] newArray(int size) {
            return new ReviewDto[size];
        }
    };

    public Drawable getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        if ( avatar != null ) {
            Bitmap bitmap = (Bitmap) ((BitmapDrawable) avatar).getBitmap();
            dest.writeParcelable(bitmap, flags);
        }
        else {
            dest.writeParcelable(null, flags);
        }
        dest.writeString(name);
        dest.writeString(review);
        dest.writeString(date);
        dest.writeFloat(rating);
    }
}
