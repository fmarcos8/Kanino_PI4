package com.senac.franciscommarcos.navigationviewteste.Models;

import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by franc on 23/11/2017.
 */

public class SampleSearchModel implements Searchable {
    private String mTitle;

    public SampleSearchModel(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public SampleSearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }
}
