package com.softdesign.exemplemortar.data.storage.dto;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Makweb on 04.04.2017.
 */

public class ProductDto implements Parcelable{
    private int id;
    private String title;
    private String description;
    private Drawable picture;
    private int price;

    public ProductDto() {
    }

    protected ProductDto(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        price = in.readInt();
    }

    public static final Creator<ProductDto> CREATOR = new Creator<ProductDto>() {
        @Override
        public ProductDto createFromParcel(Parcel in) {
            return new ProductDto(in);
        }

        @Override
        public ProductDto[] newArray(int size) {
            return new ProductDto[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Drawable getPicture() {
        return picture;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(price);
    }
}
