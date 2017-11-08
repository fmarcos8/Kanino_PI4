package com.senac.franciscommarcos.navigationviewteste;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by franc on 07/11/2017.
 */

public interface ProductService {
    @GET("api/produtos/categoria/{id}")
    Call<List<Product>> getProductsPerCategory(@Path("id") int id);
}
