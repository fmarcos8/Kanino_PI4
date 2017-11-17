package com.senac.franciscommarcos.navigationviewteste.Adapters;

import android.support.v4.app.FragmentManager;
import android.content.Context;

import com.senac.franciscommarcos.navigationviewteste.Models.Product;

import java.util.List;

/**
 * Created by franc on 08/11/2017.
 */

public class Snap {
    private int mGravity;
    private String mText;
    private List<Product> mProducts;
    private Context mContext;

    private FragmentManager fm;

    public Snap(int mGravity, String mText, List<Product> mProducts, Context context, FragmentManager fm) {
        this.mGravity = mGravity;
        this.mText = mText;
        this.mProducts = mProducts;
        this.mContext = context;
        this.fm = fm;
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

    public Context getmContext() {
        return mContext;
    }


    public FragmentManager getFm() {
        return fm;
    }
}
