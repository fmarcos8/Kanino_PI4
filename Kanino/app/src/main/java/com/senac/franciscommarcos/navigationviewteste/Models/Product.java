package com.senac.franciscommarcos.navigationviewteste.Models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Francisco on 14/10/2017.
 */

public class Product implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private String price;
    @SerializedName("description")
    private String description;
    @SerializedName("discountPromotion")
    private String discountPromotion;
    @SerializedName("activeProduct")
    private Integer activeProduct = null;
    @SerializedName("minimumStockQuantity")
    private Integer minimumStockQuantity = null;
    @SerializedName("image")
    private String image;

    private int qtd;

    public Product(String name) {
        this.name = name;
    }

    public Product(int id, String name, String price, String description, String discountPromotion, Integer activeProduct, Integer minimumStockQuantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPromotion = discountPromotion;
        this.activeProduct = activeProduct;
        this.minimumStockQuantity = minimumStockQuantity;
        this.image = image;
    }

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Product(int id, String name, int qtd, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qtd = qtd;
    }

    public Product(int id, int qtd, String price) {
        this.id = id;
        this.price = price;
        this.qtd = qtd;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountPromotion() {
        return discountPromotion;
    }

    public void setDiscountPromotion(String discountPromotion) {
        this.discountPromotion = discountPromotion;
    }

    public Integer getActiveProduct() {
        return activeProduct;
    }

    public void setActiveProduct(Integer activeProduct) {
        this.activeProduct = activeProduct;
    }

    public Integer getMinimumStockQuantity() {
        return minimumStockQuantity;
    }

    public void setMinimumStockQuantity(Integer minimumStockQuantity) {
        this.minimumStockQuantity = minimumStockQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String _toString() {
        return "Id: "+ getId() +" Produto: " + getName() + " preco: " + getPrice();
    }

}
