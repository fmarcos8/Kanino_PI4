package com.senac.franciscommarcos.navigationviewteste.Singleton;

import com.senac.franciscommarcos.navigationviewteste.Models.CartModel;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 20/11/2017.
 */

public class CartSingleton {

    private static final CartSingleton INSTANCE = new CartSingleton();

    private CartModel cartModel = new CartModel();
    private List<Product> products_cart = new ArrayList<>();

    public List<Product> getCartList() {
        return products_cart;
    }


    public void setCartList(Product p) {
        products_cart.add(p);
    }

    public void setCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
    }

    private CartSingleton(){

    }

    public static CartSingleton getInstance(){
        return INSTANCE;
    }
}
