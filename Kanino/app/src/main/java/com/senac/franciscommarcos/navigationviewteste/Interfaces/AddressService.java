package com.senac.franciscommarcos.navigationviewteste.Interfaces;

import com.senac.franciscommarcos.navigationviewteste.Models.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by francisco.mmarcos on 21/11/2017.
 */

public interface AddressService {

    @GET("api/endereco/{id}")
    Call<List<Address>> getAddress(@Path("id") Long id);
}
