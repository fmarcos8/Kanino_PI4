package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.OrderService;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.Models.Order;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersList extends AppCompatActivity {
    private List<Order> orders = new ArrayList<>();
    private ViewGroup container_orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        container_orders = (ViewGroup) findViewById(R.id.container_orders);

        Customer customer = SharedPrefManager.getInstance(OrdersList.this).getCustomer();

        Gson gson =  new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        OrderService serviceOrder = retrofit.create(OrderService.class);
        final Call<List<Order>> productCall = serviceOrder.getOrders(customer.getId());
        final ProgressDialog progress = new ProgressDialog(OrdersList.this);
        progress.setTitle("Aguarde");
        progress.setMessage("Estamos buscando seus pedidos...");
        progress.setCancelable(false);
        progress.show();

        productCall.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                progress.dismiss();
                orders = response.body();
                if(response.isSuccessful()){
                    for(Order order : orders){
                        showOrderDetails(order.getId(), order.getOrderDate(), order.getTotalPrice(), order.getDescStatus());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                progress.dismiss();
                Intent intent = new Intent(OrdersList.this, ErrorConnectionActivity.class);
                startActivity(intent);
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }



    public void showOrderDetails(final String id, String date, String total, String status){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.orders_layout, container_orders, false);
        TextView id_order = (TextView) cardView.findViewById(R.id.id_order);
        TextView date_order = (TextView) cardView.findViewById(R.id.date_order);
        TextView value_order = (TextView) cardView.findViewById(R.id.value_order);
        TextView status_order = (TextView) cardView.findViewById(R.id.status_order);

        if(status.equals("Aberto")){
            status_order.setTextColor(Color.rgb(255,165,0));
        }else if(status.equals("Aguardando Pagamento")){
            status_order.setTextColor(Color.rgb(0,0,255));
        }else if(status.equals("Enviado para Transportadora")){
            status_order.setTextColor(Color.rgb(0,0,200));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdersList.this, OrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_order", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        id_order.setText(id);
        date_order.setText(date);
        value_order.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(total)));
        status_order.setText(status);

        container_orders.addView(cardView);


    }
}
