package com.senac.franciscommarcos.navigationviewteste;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.senac.franciscommarcos.navigationviewteste.Activities.EditUserDataActivity;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.ProductService;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {
    private TextView id_product;
    private TextView product_name;
    private TextView product_price;
    private TextView product_price_discount;
    private TextView product_description;
    private ImageView product_image;
    private Button btn_add_cart;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";
    Product product;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        final ProgressDialog progress = new ProgressDialog(getContext());
        progress.setTitle("Aguarde");
        progress.setMessage("Carregando dados do produto...");
        progress.setCancelable(false);
        progress.show();

        id_product = (TextView) v.findViewById(R.id.id_product);
        product_name = (TextView) v.findViewById(R.id.product_name);
        product_price = (TextView) v.findViewById(R.id.product_price);
        product_price_discount = (TextView) v.findViewById(R.id.product_price_discount);
        product_description = (TextView) v.findViewById(R.id.product_description);
        product_image = (ImageView) v.findViewById(R.id.product_image);
        btn_add_cart = (Button) v.findViewById(R.id.btn_add_cart);

        final int id = getArguments().getInt("id");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        imageLoader.displayImage(BASE_URL+"api/produtos/imagem/"+id, product_image, options);

        Gson gson =  new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductService serviceProduct = retrofit.create(ProductService.class);
        final Call<Product> productCall = serviceProduct.getProductDetails(id);
        productCall.enqueue(new Callback<Product>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                product = response.body();
                if(response.isSuccessful()){
                    double price = Double.parseDouble(product.getPrice());
                    double percent = Double.parseDouble(product.getDiscountPromotion()) * 100;
                    double discount = price * Double.parseDouble(product.getDiscountPromotion());
                    double price_promotion = price - discount;

                    id_product.setText(Integer.toString(product.getId()));
                    product_name.setText(product.getName());

                    if(percent != 0){
                        product_price.setText("de "+NumberFormat.getCurrencyInstance().format(price));
                        product_price_discount.setText("por "+NumberFormat.getCurrencyInstance().format(price_promotion));
                    }else {
                        product_price.setEnabled(false);
                        product_price_discount.setTextColor(Color.rgb(0,0,0));
                        product_price_discount.setText(NumberFormat.getCurrencyInstance().format(price));
                    }
                    product_description.setText(product.getDescription());
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = product.getId();
                String name = product.getName();
                String price = product.getPrice();
                if(!product.getDiscountPromotion().equals(0)){
                    double bd_price = Double.parseDouble(product.getPrice());
                    double bd_discount_percent = Double.parseDouble(product.getDiscountPromotion());
                    double discount = bd_price * bd_discount_percent;
                    final double final_price = bd_price - discount;
                    price = Double.toString(final_price);
                }

                addItem(new Product(product.getId(), product.getName(), 1, price));
                Toast.makeText(getContext(),"Produto adicionado no carrinho!",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    public void addItem(Product product){
        double total = CartSingleton.getInstance().getTotal();
        total += Double.parseDouble(product.getPrice());
        CartSingleton.getInstance().setTotal(total);

        List<Product> list = CartSingleton.getInstance().getCartList();
        for(Product p : list){
            if(product.getId() == (p.getId())){
                p.setQtd(p.getQtd() + 1);
                return;
            }
        }
        CartSingleton.getInstance().setCartList(new Product(product.getId(), product.getName(), 1, product.getPrice()));

    }
}
