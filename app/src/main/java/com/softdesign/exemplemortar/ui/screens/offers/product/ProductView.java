package com.softdesign.exemplemortar.ui.screens.offers.product;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.softdesign.exemplemortar.R;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.mortar.ScreenScoper;
import com.softdesign.exemplemortar.ui.screens.base.AbstractView;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductView extends AbstractView<ProductScreen.Presenter>{
    @BindView(R.id.picture_img)
    ImageView mPictureImg;
    @BindView(R.id.title_txt)
    TextView mTitleTxt;
    @BindView(R.id.price_txt)
    TextView mPriceTxt;
    @BindView(R.id.describtion_txt)
    TextView mDescribtionTxt;
    public ProductView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        ScreenScoper.<ProductScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void renderUi(ProductDto product) {
        mPictureImg.setImageDrawable(product.getPicture());
        mTitleTxt.setText(product.getTitle());
        mPriceTxt.setText(product.getPrice() + "$");
        mDescribtionTxt.setText(product.getDescription());

        Bitmap avatarBitmap = ((BitmapDrawable) product.getPicture()).getBitmap();
        if (avatarBitmap != null && !avatarBitmap.isRecycled()) {
            Palette palette = Palette.from(avatarBitmap).generate();
            int textBg = palette.getDominantColor(Color.DKGRAY);
            mTitleTxt.setBackgroundColor(adjustAlpha(textBg,0.8f));
        }
    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @OnClick(R.id.reviews_btn)
    void clickOnReviews() {
        mPresenter.clickOnReviews();
    }
}
