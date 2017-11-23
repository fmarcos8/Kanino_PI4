package com.senac.franciscommarcos.navigationviewteste.Models;

import java.util.List;

/**
 * Created by victor.maciel on 22/11/2017.
 */

public class CheckoutModel {

    private Long idCustomer;
    private Long idAdress;
    private int idAplication;
    private int idPaymentType;
    private String orderDate;
    private int idStatus;
    private List<Product> products;

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Long getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(Long idAdress) {
        this.idAdress = idAdress;
    }

    public int getIdAplication() {
        return idAplication;
    }

    public void setIdAplication(int idAplication) {
        this.idAplication = idAplication;
    }

    public int getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(int idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
