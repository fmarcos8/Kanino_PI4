package com.senac.franciscommarcos.navigationviewteste;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by franc on 07/11/2017.
 */

public interface CategoryService {
    @GET("api/categorias")
    Call<List<Category>> getCategories();


}
