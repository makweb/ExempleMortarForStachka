package com.softdesign.exemplemortar.flow;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import flow.KeyParceler;

/**
 * Created by Makweb on 11.04.2017.
 */

public class BasicKeyParceler implements KeyParceler {
    @NonNull @Override public Parcelable toParcelable(@NonNull Object key) {
        return (Parcelable) key;
    }

    @NonNull
    @Override public Object toKey(@NonNull Parcelable parcelable) {
        return parcelable;
    }
}
