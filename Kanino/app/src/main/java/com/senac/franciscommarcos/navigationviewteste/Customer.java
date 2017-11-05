package com.senac.franciscommarcos.navigationviewteste;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by franc on 04/11/2017.
 */

public class Customer implements Serializable {


    @SerializedName("id")
    private Long id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;

    private String password;
    @SerializedName("cpf")
    private String cpf;
    @SerializedName("cell__phone")
    private String cell_phone;
    @SerializedName("commercial_phone")
    private String commercial_phone;
    @SerializedName("residencial_phone")
    private String residencial_phone;
    @SerializedName("birth")
    private String birth;
    @SerializedName("send_newsletter")
    private String send_newsletter;

    public Customer(Long id, String email, String name, String password, String cpf, String cell_phone, String commercial_phone, String residencial_phone, String birth, String send_newsletter) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.cpf = cpf;
        this.cell_phone = cell_phone;
        this.commercial_phone = commercial_phone;
        this.residencial_phone = residencial_phone;
        this.birth = birth;
        this.send_newsletter = send_newsletter;
    }

    public Customer(Long id, String email, String name, String cpf, String cell_phone, String residencial_phone, String send_newsletter) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.cpf = cpf;
        this.cell_phone = cell_phone;
        this.residencial_phone = residencial_phone;
        this.send_newsletter = send_newsletter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public String getCommercial_phone() {
        return commercial_phone;
    }

    public String getResidencial_phone() {
        return residencial_phone;
    }

    public String getBirth() {
        return birth;
    }

    public String getSend_newsletter() {
        return send_newsletter;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public void setCommercial_phone(String commercial_phone) {
        this.commercial_phone = commercial_phone;
    }

    public void setResidencial_phone(String residencial_phone) {
        this.residencial_phone = residencial_phone;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setSend_newsletter(String send_newsletter) {
        this.send_newsletter = send_newsletter;
    }
}
