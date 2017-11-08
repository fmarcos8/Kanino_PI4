package com.senac.franciscommarcos.navigationviewteste;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by franc on 04/11/2017.
 */

public interface CustomerService {
    /*@GET("api/produtos")
    Call<List<Product>> getProdutos();*/

    @FormUrlEncoded
    @POST("cliente/login")
    Call<Customer> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
