package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.senac.franciscommarcos.navigationviewteste.Models.*;
import com.senac.franciscommarcos.navigationviewteste.QrCodeReader;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ViewGroup container_cart;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";
    private List<Product> cart = new ArrayList<>();
    private Button button_buy;
    private TextView tv_total;

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
        double cartTotal = CartSingleton.getInstance().getTotal();
        tv_total.setText(Double.toString(cartTotal));

        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, Checkout.class);
                startActivity(intent);
            }
        });
    }

    private void addItem(int id, String name, int qtd, String total, String BASE_URL){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.cart_itens, container_cart, false);
        ImageView image_product_cart = (ImageView) cardView.findViewById(R.id.image_product_cart);
        TextView name_product_cart = (TextView) cardView.findViewById(R.id.name_product_cart);
        TextView qtd_product_cart = (TextView) cardView.findViewById(R.id.qtd_product_cart);
        TextView total_value_per_item = (TextView) cardView.findViewById(R.id.total_value_per_item);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(CartActivity.this));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        imageLoader.displayImage(BASE_URL+"api/produtos/imagem/"+id, image_product_cart, options);

        name_product_cart.setText(limit(name, 10));
        qtd_product_cart.setText(Integer.toString(qtd));
        total_value_per_item.setText(total);

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

}

