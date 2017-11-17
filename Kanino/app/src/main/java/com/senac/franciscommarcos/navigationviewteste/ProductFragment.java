package com.senac.franciscommarcos.navigationviewteste;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.ProductService;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private TextView product_name;
    private TextView product_price;
    private TextView product_description;
    private ImageView product_image;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        product_name = (TextView) v.findViewById(R.id.product_name);
        product_price = (TextView) v.findViewById(R.id.product_price);
        product_description = (TextView) v.findViewById(R.id.product_description);
        product_image = (ImageView) v.findViewById(R.id.product_image);

        int id = getArguments().getInt("id");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        imageLoader.displayImage("http://kanino-pi4.azurewebsites.net/Kanino/api/produtos/imagem/"+id, product_image, options);

        Gson gson =  new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductService serviceProduct = retrofit.create(ProductService.class);
        final Call<Product> productCall = serviceProduct.getProductDetails(id);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if(response.isSuccessful()){



                    product_name.setText(product.getName());
                    product_price.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(product.getPrice())));
                    product_description.setText(product.getDescription());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        return v;
    }
}
