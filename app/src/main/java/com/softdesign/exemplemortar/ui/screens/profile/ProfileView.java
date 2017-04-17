package com.softdesign.exemplemortar.ui.screens.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProfileDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractPresenter;
import com.softdesign.exemplemortar.ui.screens.base.AbstractView;

import butterknife.BindView;

import static android.support.annotation.VisibleForTesting.NONE;

/**
 * Created by Makweb on 10.04.2017.
 */

public class ProfileView extends AbstractView<ProfileScreen.Presenter> {
    @BindView(R.id.avatar_iv)
    ImageView mAvatarIv;
    @BindView(R.id.drawer_name_txt)
    TextView mUserNameTxt;
    @BindView(R.id.phone_txt)
    TextView mPhoneTxt;
    @BindView(R.id.mail_txt)
    TextView mMailTxt;
    @BindView(R.id.card_picture_iv)
    ImageView mBankCardIv;
    @BindView(R.id.amount_txt)
    TextView mAmountTxt;
    @BindView(R.id.card_user_name_txt)
    TextView mCardUserNameTxt;
    @BindView(R.id.card_number_txt)
    TextView mCardNumberTxt;
    @BindView(R.id.avatar_wrap)
    FrameLayout mAvatarWrap;
    @BindView(R.id.card_number_wrap)
    LinearLayout mCardNumberWrap;

    public ProfileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        ScreenScoper.<ProfileScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void renderUi(ProfileDto profile) {
        mAvatarIv.setImageDrawable(profile.getAvatar());
        mUserNameTxt.setText(profile.getFullName());
        mPhoneTxt.setText(profile.getPhone());
        mMailTxt.setText(profile.getEmail());
        mBankCardIv.setImageDrawable(profile.getCardPicture());
        mAmountTxt.setText(profile.getAmount());
        mCardUserNameTxt.setText(profile.getFullName());
        mCardNumberTxt.setText(profile.getCardNumber());

        Bitmap avatarBitmap = ((BitmapDrawable) profile.getAvatar()).getBitmap();
        if (avatarBitmap != null && !avatarBitmap.isRecycled()) {
            Palette palette = Palette.from(avatarBitmap).generate();
            int avatarBg = palette.getDominantColor(Color.DKGRAY);
            int textBg = palette.getDarkMutedColor(Color.parseColor("#6e000000"));
            mAvatarWrap.setBackgroundColor(avatarBg);
            mUserNameTxt.setBackgroundColor(adjustAlpha(textBg, 0.6f));
        }

        Bitmap cardBitmap = ((BitmapDrawable) profile.getCardPicture()).getBitmap();
        if (cardBitmap != null && !cardBitmap.isRecycled()) {
            Palette palette = Palette.from(cardBitmap).generate();
            int cardBg = palette.getDominantColor(Color.DKGRAY);
            mCardNumberWrap.setBackgroundColor(cardBg);
        }
    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @VisibleForTesting(otherwise = NONE)
    public AbstractPresenter getPresenter() {
        return mPresenter;
    }
}
