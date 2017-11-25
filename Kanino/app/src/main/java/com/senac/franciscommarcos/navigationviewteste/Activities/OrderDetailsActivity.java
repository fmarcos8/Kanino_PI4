package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.OrderService;
import com.senac.franciscommarcos.navigationviewteste.Models.Order;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;
import com.senac.franciscommarcos.navigationviewteste.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailsActivity extends AppCompatActivity {
    private ViewGroup container_order_detail;
    private TextView n_order;
    private List<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        n_order = (TextView) findViewById(R.id.n_order);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String id_order = bundle.getString("id_order");
        n_order.setText(n_order.getText()+id_order);



        container_order_detail = (ViewGroup) findViewById(R.id.container_order_detail);
        Gson gson =  new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        OrderService serviceOrder = retrofit.create(OrderService.class);
        final Call<List<Product>> productCall = serviceOrder.getDetailOrder(Integer.parseInt(id_order));

        productCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                products = response.body();
                if(response.isSuccessful()){
                    for(Product p : products){
                        addItemInList(p.getId(),p.getName(),p.getQtd(),Double.parseDouble(p.getPrice()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
//        double valor = 19.50F;
//        double total = 10*valor;
//        for (int i = 0; i < 10; i++){
//            addItemInList(i, "Produto"+(i+1), 10, total);
//        }
    }

    private void addItemInList(int id, String name, int qtd, double total){
        CardView cardview = (CardView) LayoutInflater.from(this).inflate(R.layout.order_detail_layout, container_order_detail, false);
        TextView id_product = (TextView) cardview.findViewById(R.id.id_product_order);
        TextView name_product_order = (TextView) cardview.findViewById(R.id.name_product_order);
        TextView qtd_product_order = (TextView) cardview.findViewById(R.id.qtd_product_order);
        TextView total_price_product_order = (TextView) cardview.findViewById(R.id.total_price_product);

        id_product.setText(Integer.toString(id));
        name_product_order.setText(limit(name, 10));
        qtd_product_order.setText(Integer.toString(qtd));
        total_price_product_order.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(String.valueOf(total))));

        container_order_detail.addView(cardview);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public static String limit(String string, int length)
    {
        StringBuilder buf = new StringBuilder(string);
        if (buf.length() > length)
        {
            buf.setLength(length);
            buf.append("...");
        }

        return buf.toString();
    }
}
