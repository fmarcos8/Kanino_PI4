package com.senac.franciscommarcos.navigationviewteste.Models;

/**
 * Created by franc on 15/11/2017.
 */

public class Order {
    private String id;
    private String DescStatus;
    private String orderDate;
    private String totalPrice;

    public Order(){

    }

    public Order(String id, String descStatus, String orderDate, String totalPrice) {
        this.id = id;
        this.DescStatus = descStatus;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescStatus() {
        return DescStatus;
    }

    public void setDescStatus(String DescStatus) {
        this.DescStatus = DescStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
