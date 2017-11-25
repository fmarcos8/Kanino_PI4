package com.senac.franciscommarcos.navigationviewteste.Singleton;

import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 20/11/2017.
 */

public class CartSingleton {

    private static final CartSingleton INSTANCE = new CartSingleton();

    private List<Product> products_cart = new ArrayList<>();
    private List<Product> products_search = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private double total;


    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Address a) {
        addresses.add(a);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Product> getCartList() {
        return products_cart;
    }

    public void setCartList(Product p) {
        products_cart.add(p);
    }

    public List<Product> getProducts_search() {
        return products_search;
    }

    public void setProducts_search(Product p) {
        products_search.add(p);
    }

    private CartSingleton(){

    }

    public static CartSingleton getInstance(){
        return INSTANCE;
    }
}
