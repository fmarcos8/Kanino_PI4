package com.senac.franciscommarcos.navigationviewteste.Interfaces;

import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.CEP;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by francisco.mmarcos on 21/11/2017.
 */

public interface AddressService {

    @Headers("Content-Type: application/json")
    @POST("api/endereco/cadastrar")
    Call<Long> insertNewAddress(@Body Address a);

    @GET("api/endereco/{id}")
    Call<List<Address>> getAddress(@Path("id") Long id);
//https://viacep.com.br/ws/04458000/json/

    @GET("ws/{cep}/json/")
    Call<CEP> getAddressDatas(@Path("cep") String cep);
}
