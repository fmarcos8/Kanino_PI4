package com.senac.franciscommarcos.navigationviewteste.Models;

import java.util.List;

/**
 * Created by victor.maciel on 22/11/2017.
 */

public class CheckoutModel {

    private String idCustomer;
    private String idAddress;
    private String idApplication;
    private String idPaymentType;
    private String orderDate;
    private String idStatus;
    private List<Product> products;

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getIdApplication() {
        return idApplication;
    }

    public void setIdApplication(String idApplication) {
        this.idApplication = idApplication;
    }

    public String getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(String idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
