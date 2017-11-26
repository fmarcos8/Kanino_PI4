package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.AddressService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.AddressDec;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressActivity extends AppCompatActivity {
    List<Address> listAddress = new ArrayList<>();
    private ViewGroup container_address;
    private Button btn_add_new_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        container_address = (ViewGroup) findViewById(R.id.container_address);
        btn_add_new_address = (Button) findViewById(R.id.btn_add_new_address);


        Customer customer = SharedPrefManager.getInstance(AddressActivity.this).getCustomer();
        final String session_id = customer.getId().toString();

        listAddress = CartSingleton.getInstance().getAddresses();

        for(Address address : listAddress){
            addAddress(Integer.parseInt(session_id),
                    address.getAddress_name(),
                    address.getStreet_name(),
                    address.getAddress_number(),
                    address.getZip_code(),
                    address.getComplement(),
                    address.getCity(),
                    address.getCountry(),
                    address.getUf());
        }

        btn_add_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=  new Intent(AddressActivity.this, NewAddressActivity.class);
                startActivity(intent);
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void addAddress(int id, String name, String street, String number, String zipCode, String complnt, String name_city, String name_country, String uf_desc){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.card_address, container_address, false);
        TextView address_name = (TextView) cardView.findViewById(R.id.address_name);
        TextView street_name = (TextView) cardView.findViewById(R.id.street_name);
        TextView city = (TextView) cardView.findViewById(R.id.city);

        address_name.setText(name);
        street_name.setText(street +", Nº "+number);
        city.setText(name_city);

        container_address.addView(cardView);
    }
}