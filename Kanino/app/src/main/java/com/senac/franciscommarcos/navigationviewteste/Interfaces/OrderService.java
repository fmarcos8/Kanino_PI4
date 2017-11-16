package com.senac.franciscommarcos.navigationviewteste.Interfaces;

import com.senac.franciscommarcos.navigationviewteste.Models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by franc on 15/11/2017.
 */

public interface OrderService {
    @GET("api/pedidos/{id}")
    Call<List<Order>> getOrders(@Path("id") long id);
}
