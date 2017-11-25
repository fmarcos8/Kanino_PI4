package com.senac.franciscommarcos.navigationviewteste.Models;

/**
 * Created by francisco.mmarcos on 21/11/2017.
 */

public class Address {
    private int id_address;
    private Long id_customer;
    private String address_name;
    private String street_name;
    private String address_number;
    private String zip_code;
    private String complement;
    private String city;
    private String country;
    private String uf;

    public Address(int id_address, Long id_customer, String address_name, String street_name, String address_number, String zip_code, String complement, String city, String country, String uf) {
        this.id_address = id_address;
        this.id_customer = id_customer;
        this.address_name = address_name;
        this.street_name = street_name;
        this.address_number = address_number;
        this.zip_code = zip_code;
        this.complement = complement;
        this.city = city;
        this.country = country;
        this.uf = uf;
    }

    public Address(Long id_customer, String address_name, String street_name, String address_number, String zip_code, String complement, String city, String country, String uf) {
        this.id_customer = id_customer;
        this.address_name = address_name;
        this.street_name = street_name;
        this.address_number = address_number;
        this.zip_code = zip_code;
        this.complement = complement;
        this.city = city;
        this.country = country;
        this.uf = uf;
    }

    public int getId_address() {
        return id_address;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public void setId_address(int id_address) {
        this.id_address = id_address;
    }

    public String getAddress_name() {
        return address_name;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getAddress_number() {
        return address_number;
    }

    public void setAddress_number(String address_number) {
        this.address_number = address_number;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
