package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senac.franciscommarcos.navigationviewteste.Models.CheckoutModel;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

public class Checkout extends AppCompatActivity {

    private TextView cart_total;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cart_total = (TextView) findViewById(R.id.tv_total);
        btn_finish = (Button) findViewById(R.id.bt_finalizar);

        double cartTotal = CartSingleton.getInstance().getTotal();
        cart_total.setText(Double.toString(cartTotal));

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckoutModel ch = new CheckoutModel();
                ch.setProducts(CartSingleton.getInstance().getCartList());
//                ch.setIdAdress();
//                ch.setIdAplication(2);
//                ch.setIdCustomer();
//                ch.setIdPaymentType();
//                ch.setIdStatus();
//                ch.setOrderDate();
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
