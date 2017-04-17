package com.softdesign.exemplemortar.data.storage.dto;

import android.graphics.drawable.Drawable;

/**
 * Created by Makweb on 10.04.2017.
 */

public class ProfileDto {
    private String fullName;
    private Drawable avatar;
    private String phone;
    private String email;
    private String amount;
    private Drawable cardPicture;
    private String cardNumber;

    public ProfileDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAmount() {
        return amount;
    }

    public Drawable getCardPicture() {
        return cardPicture;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
