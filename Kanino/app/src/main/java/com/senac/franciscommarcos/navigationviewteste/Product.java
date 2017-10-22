package com.senac.franciscommarcos.navigationviewteste;

/**
 * Created by Francisco on 14/10/2017.
 */

public class Product {
    private int id;
    private String name;
    private String price;
    private String description;
    private String discountPromotion;
    private int activeProduct;
    private int minimumStockQuantity;
    private String image;

    public Product(int id, String name, String price, String description, String discountPromotion, int activeProduct, int minimumStockQuantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discountPromotion = discountPromotion;
        this.activeProduct = activeProduct;
        this.minimumStockQuantity = minimumStockQuantity;
        this.image = image;
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

    public int getActiveProduct() {
        return activeProduct;
    }

    public void setActiveProduct(int activeProduct) {
        this.activeProduct = activeProduct;
    }

    public int getMinimumStockQuantity() {
        return minimumStockQuantity;
    }

    public void setMinimumStockQuantity(int minimumStockQuantity) {
        this.minimumStockQuantity = minimumStockQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        return "Id: "+ getId() +" Produto: " + getName() + " Descrição: " +
                getDescription() + " preco: " + getPrice();
    }
}
