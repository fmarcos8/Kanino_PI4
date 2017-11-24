package com.senac.franciscommarcos.navigationviewteste.Interfaces;

import com.senac.franciscommarcos.navigationviewteste.Models.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by franc on 07/11/2017.
 */

public interface ProductService {

    @GET("api/produtos/buscar/{parametro}")
    Call<ArrayList<Product>> getAllProducts(@Path("param") String param);

    @GET("api/produtos/{id}")
    Call<Product> getProductDetails(@Path("id") int id);

    @GET("api/produtos/categoria/{id}")
    Call<List<Product>> getProductsPerCategory(@Path("id") int id);
}
