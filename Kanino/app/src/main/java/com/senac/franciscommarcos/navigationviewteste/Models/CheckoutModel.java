package com.senac.franciscommarcos.navigationviewteste.Models;

import java.util.List;

/**
 * Created by victor.maciel on 22/11/2017.
 */

public class CheckoutModel {

    private Long idCustomer;
    private Long idAdress;
    private Long idAplication;
    private Long idPaymentType;
    private String orderDate;
    private Long idStatus;
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

    public Long getIdAplication() {
        return idAplication;
    }

    public void setIdAplication(Long idAplication) {
        this.idAplication = idAplication;
    }

    public Long getIdPaymentType() {
        return idPaymentType;
    }

    public void setIdPaymentType(Long idPaymentType) {
        this.idPaymentType = idPaymentType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Long idStatus) {
        this.idStatus = idStatus;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
