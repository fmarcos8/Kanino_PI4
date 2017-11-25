package com.senac.franciscommarcos.navigationviewteste.Interfaces;

import com.senac.franciscommarcos.navigationviewteste.Models.Customer;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by franc on 04/11/2017.
 */

public interface CustomerService {

    @Headers("Content-Type: application/json")
    @POST("api/cliente/editar")
    Call<Long> updatePersonalDatas(@Body Customer c);

    @FormUrlEncoded
    @POST("api/cliente/recuperar-senha")
    Call<String> forgotPassword(@Field("email") String email);

    @Headers("Content-Type: application/json")
    @POST("api/cliente/registrar")
    Call<Integer> getRegisterResult(@Body Customer c);

    @FormUrlEncoded
    @POST("api/cliente/login")
    Call<Customer> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
