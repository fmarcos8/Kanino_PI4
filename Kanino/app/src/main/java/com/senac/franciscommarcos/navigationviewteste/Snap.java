package com.senac.franciscommarcos.navigationviewteste;

import java.util.List;

/**
 * Created by franc on 08/11/2017.
 */

public class Snap {
    private int mGravity;
    private String mText;
    private List<Product> mProducts;

    public Snap(int mGravity, String mText, List<Product> mProducts) {
        this.mGravity = mGravity;
        this.mText = mText;
        this.mProducts = mProducts;
    }

    public int getmGravity() {
        return mGravity;
    }

    public String getmText() {
        return mText;
    }

    public List<Product> getmProducts() {
        return mProducts;
    }
}
