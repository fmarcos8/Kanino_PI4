package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.senac.franciscommarcos.navigationviewteste.Models.*;
import com.senac.franciscommarcos.navigationviewteste.ProductsFragment;
import com.senac.franciscommarcos.navigationviewteste.QrCodeReader;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ViewGroup container_cart;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";
    private List<Product> cart = new ArrayList<>();
    private Button button_buy;
    private TextView tv_total;

    double cartTotal = CartSingleton.getInstance().getTotal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        container_cart = (ViewGroup) findViewById(R.id.container_cart);
        button_buy = (Button) findViewById(R.id.btn_buy);
        tv_total = (TextView) findViewById(R.id.tv_total);

        List<Product> list = CartSingleton.getInstance().getCartList();
        double total = CartSingleton.getInstance().getTotal();
        for (Product p : list){
            addItem(p.getId(), p.getName(), p.getQtd(), p.getPrice(), BASE_URL);
        }

        tv_total.setText(NumberFormat.getCurrencyInstance().format(cartTotal));

        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cartTotal == 0){
                    Alert2("Seu carrinho está vazio, vá as compras", "Ops!");
                    return;
                }

                if(!SharedPrefManager.getInstance(CartActivity.this).isLogged()){
                    Alert1("Você precisa estar logado para realizar a compra", "Ops!");
                    return;
                }
                Intent intent = new Intent(CartActivity.this, Checkout.class);
                startActivity(intent);

            }
        });


        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void addItem(final int idProduct, String name, final int qtd, final String total, String BASE_URL){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.cart_itens, container_cart, false);
        ImageView image_product_cart = (ImageView) cardView.findViewById(R.id.image_product_cart);
        TextView name_product_cart = (TextView) cardView.findViewById(R.id.name_product_cart);
        final TextView qtd_product_cart = (TextView) cardView.findViewById(R.id.qtd_product_cart);
        TextView total_value_per_item = (TextView) cardView.findViewById(R.id.total_value_per_item);
        TextView total_value = (TextView) cardView.findViewById(R.id.total_value);
        Button add_item = (Button) cardView.findViewById(R.id.add_item);
        Button remove_a_item = (Button) cardView.findViewById(R.id.remove_a_item);
        Button remove_item = (Button) cardView.findViewById(R.id.remove_item);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(CartActivity.this));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        imageLoader.displayImage(BASE_URL+"api/produtos/imagem/"+idProduct, image_product_cart, options);

        name_product_cart.setText(limit(name, 10));
        qtd_product_cart.setText(Integer.toString(qtd));
        total_value_per_item.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(total)));
        double sum_itens = qtd * Double.parseDouble(total);
        String sum_itens_formated = NumberFormat.getCurrencyInstance().format(sum_itens);
        total_value.setText(sum_itens_formated);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Product> cart_itens = CartSingleton.getInstance().getCartList();
                double total_cart = CartSingleton.getInstance().getTotal();

                for(Product p : cart_itens){
                    if(idProduct == p.getId()){
                        p.setQtd(p.getQtd() + 1);
                        CartSingleton.getInstance().setTotal(total_cart + Double.parseDouble(total));
                        tv_total.setText(NumberFormat.getCurrencyInstance().format(total_cart));

                        Intent intent = new Intent(CartActivity.this, CartActivity.class);
                        startActivity(intent);
                        return;
                    }
                }

            }
        });

        remove_a_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(qtd <= 1){
                    return;
                }

                List<Product> cart_itens = CartSingleton.getInstance().getCartList();
                double total_cart = CartSingleton.getInstance().getTotal();

                for(Product p : cart_itens){
                    if(idProduct == p.getId()){
                        p.setQtd(p.getQtd() - 1);
                        CartSingleton.getInstance().setTotal(total_cart - Double.parseDouble(total));

                        Intent intent = new Intent(CartActivity.this, CartActivity.class);
                        startActivity(intent);
                        return;
                    }
                }

            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> cart_itens = CartSingleton.getInstance().getCartList();
                double total_cart = CartSingleton.getInstance().getTotal();
                int i = 0;
                for(Product p : cart_itens){
                    if(idProduct == p.getId()){
                        CartSingleton.getInstance().setTotal(total_cart - Double.valueOf(p.getPrice()));
                        cart_itens.remove(i);

                        Intent intent = new Intent(CartActivity.this, CartActivity.class);
                        startActivity(intent);
                        return;
                    }
                    i++;
                }
            }
        });

        container_cart.addView(cardView);
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

    public void Alert1 (String msg, String title){

        AlertDialog.Builder builder = new
                AlertDialog.Builder(CartActivity.this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Alert2 (String msg, String title){

        AlertDialog.Builder builder = new
                AlertDialog.Builder(CartActivity.this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CartActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

