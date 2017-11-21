package com.senac.franciscommarcos.navigationviewteste.Models;

import com.senac.franciscommarcos.navigationviewteste.Adapters.Snap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 20/11/2017.
 */

public class CartModel {

    private List<Product> products_cart = new ArrayList<>();

    public CartModel(){

    }

    public void addProductInCart(Product product) {
        products_cart.add(product);
    }

    public List<Product> getList(){
        return this.products_cart;
    }
}
