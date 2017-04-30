package com.softdesign.exemplemortar.flow;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import flow.KeyParceler;

/**
 * Created by Makweb on 11.04.2017.
 */

public class BasicKeyParceler implements KeyParceler {
    private final Parcelable mEmptyParcelable = new Parcelable() {
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            /*empty*/
        }
    };

    @NonNull
    @Override
    public Parcelable toParcelable(@NonNull Object key) {
        if (key instanceof Parcelable)
            return (Parcelable) key;
        else
            return mEmptyParcelable;
    }

    @NonNull
    @Override
    public Object toKey(@NonNull Parcelable parcelable) {
        return parcelable;
    }
}
