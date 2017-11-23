package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senac.franciscommarcos.navigationviewteste.Models.CheckoutModel;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;
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
                Customer customer = SharedPrefManager.getInstance(Checkout.this).getCustomer();

//                CheckoutModel ch = new CheckoutModel();
//                ch.setProducts(CartSingleton.getInstance().getCartList());
//                ch.setIdAdress();
//                ch.setIdAplication(2);
//                ch.setIdCustomer(customer.getId());
//                ch.setIdPaymentType();
//                String type = "";
//                type ? ch.setIdStatus(1) : ch.setIdStatus(2);
//                ch.setOrderDate("2017-05-10 10:00:00");


            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}

//                {
//
//                    "idCustomer": "5",
//                        "idAddress": "9",
//                        "idApplication": "2",
//                        "idPaymentType": "1",
//                        "orderDate": "2017-05-10 10:00:00",
//                        "idStatus": "1",
//                        "products": [
//                    {
//                        "id": "1",
//                            "qtd": "30",
//                            "price": "1500.00"
//                    },
//                    {
//                        "id": "2",
//                            "qtd": "35",
//                            "price": "500.00"
//                    }
//                    ]
//
//                }
