package com.softdesign.exemplemortar.data.storage;

import android.util.Log;

import com.softdesign.exemplemortar.App;
import com.softdesign.exemplemortar.data.storage.dto.ProductDto;
import com.softdesign.exemplemortar.data.storage.dto.ProfileDto;
import com.softdesign.exemplemortar.data.storage.dto.ReviewDto;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;
import tk.zielony.randomdata.RandomData;
import tk.zielony.randomdata.common.DrawableImageGenerator;
import tk.zielony.randomdata.common.FloatGenerator;
import tk.zielony.randomdata.common.IntegerGenerator;
import tk.zielony.randomdata.common.IntegerIdGenerator;
import tk.zielony.randomdata.common.StringDateGenerator;
import tk.zielony.randomdata.common.TextGenerator;
import tk.zielony.randomdata.finance.StringAmountGenerator;
import tk.zielony.randomdata.finance.StringCardNumberGenerator;
import tk.zielony.randomdata.person.DrawableAvatarGenerator;
import tk.zielony.randomdata.person.StringEmailGenerator;
import tk.zielony.randomdata.person.StringNameGenerator;
import tk.zielony.randomdata.person.StringPhoneGenerator;
import tk.zielony.randomdata.place.StringCityGenerator;

/**
 * Created by Makweb on 11.04.2017.
 */

public class StubStorage {

    public static StubStorage INSTANCE;
    private RandomData mProfileRandomData;
    private RandomData mOfferRandomData;
    private RandomData mReviewRandomData;
    private ProfileDto mProfileDto;
    private ProductDto[] mProducts;
    private HashMap<Integer, List<ReviewDto>> mProductsReviews = new HashMap<>();

    private StubStorage() {
        prepareProfileRandomData();
        prepareOfferRandomData();
        prepareReviewRandomData();
    }

    public static StubStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StubStorage();
        }
        return INSTANCE;
    }

    public ProfileDto getUserProfile() {
        if (mProfileDto == null) {
            mProfileDto = new ProfileDto();
            mProfileRandomData.fill(mProfileDto);
        }
        return mProfileDto;
    }

    public List<ProductDto> getProducts() {
        if (mProducts == null) {
            mProducts = new ProductDto[10];
            mOfferRandomData.fill(mProducts);
        }
        return Arrays.asList(mProducts);
    }

    public List<ReviewDto> getReviewForProduct(ProductDto product) {
        int id = product.getId();
        List<ReviewDto> reviews = mProductsReviews.get(id);
        if (reviews == null) {
            int count = new Random().nextInt(15 - 6) + 6;
            Log.e("RANDOM", "getReviewForProduct: " + count);
            ReviewDto[] newReviews = new ReviewDto[count];
            mReviewRandomData.fill(newReviews);
            mProductsReviews.put(id, Arrays.asList(newReviews));
            return Arrays.asList(newReviews);
        }
        return reviews;
    }

    //region ======================== prepare ========================
    private void prepareProfileRandomData() {
        mProfileRandomData = new RandomData();
        mProfileRandomData.addGenerators(new Generator[]{
                new StringNameGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("fullName");
                    }
                }),
                new DrawableAvatarGenerator(App.getAppContext()),
                new StringPhoneGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("phone");
                    }
                }),
                new StringEmailGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("email");
                    }
                }),
                new StringAmountGenerator(10000, false).withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("amount");
                    }
                }),
                new DrawableImageGenerator(App.getAppContext()).withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("cardPicture");
                    }
                }),
                new StringCardNumberGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("cardNumber");
                    }
                })
        });
    }

    private void prepareOfferRandomData() {
        mOfferRandomData = new RandomData();
        mOfferRandomData.addGenerators(new Generator[]{
                new IntegerIdGenerator(),
                new StringCityGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("title");
                    }
                }),
                new TextGenerator(2, false).withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("description");
                    }
                }),
                new DrawableImageGenerator(App.getAppContext()),
                new IntegerGenerator(100, 10000)
        });
    }

    private void prepareReviewRandomData() {
        mReviewRandomData = new RandomData();
        mReviewRandomData.addGenerators(new Generator[]{
                new IntegerIdGenerator(),
                new StringNameGenerator().withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("name");
                    }
                }),
                new TextGenerator(25, false).withMatcher(new Matcher() {
                    @Override
                    public boolean matches(Field field) {
                        return field.getName().equals("review");
                    }
                }),
                new DrawableAvatarGenerator(App.getAppContext()),
                new FloatGenerator(0, 5),
                new StringDateGenerator()
        });
    }

    public RandomData getProfileRandomGenerator() {
        return mProfileRandomData;
    }

    public RandomData getProductRandomGenerator() {
        return mOfferRandomData;
    }

    public RandomData getReviewRandomGenerator() {
        return mReviewRandomData;
    }

    //endregion


}
