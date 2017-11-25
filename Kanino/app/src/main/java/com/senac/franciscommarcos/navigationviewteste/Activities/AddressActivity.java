package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.AddressService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.AddressDec;
import com.senac.franciscommarcos.navigationviewteste.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        container_address = (ViewGroup) findViewById(R.id.container_address);

        //final ListView list_address = (ListView) findViewById(R.id.list_address);
        //final ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1, listAddress);

        Intent intent = getIntent();
        long id = intent.getExtras().getLong("id");

        Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, new AddressDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressService serviceA = retrofit.create(AddressService.class);
        Call<List<Address>> address = serviceA.getAddress(id);
        address.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                listAddress = response.body();
                if(response.isSuccessful()){


                    //list_address.setAdapter(adapter);
                    for(Address address : listAddress){
                        addAddress(address.getId_address(),
                                address.getAddress_name(),
                                address.getStreet_name(),
                                address.getAddress_number(),
                                address.getZip_code(),
                                address.getComplement(),
                                address.getCity(),
                                address.getCountry(),
                                address.getUf());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void addAddress(int id, String name, String street, String number, String zipCode, String complnt, String name_city, String name_country, String uf_desc){
        CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.card_address, container_address, false);
        TextView id_address = (TextView) cardView.findViewById(R.id.id_address);
        TextView address_name = (TextView) cardView.findViewById(R.id.address_name);
        TextView street_name = (TextView) cardView.findViewById(R.id.street_name);
        //TextView address_number = (TextView) cardView.findViewById(R.id.address_number);
        //TextView zip_code = (TextView) cardView.findViewById(R.id.zip_code);
        //TextView complement = (TextView) cardView.findViewById(R.id.complement);
        TextView city = (TextView) cardView.findViewById(R.id.city);
       // TextView country = (TextView) cardView.findViewById(R.id.country);
       // TextView uf = (TextView) cardView.findViewById(R.id.uf);

        id_address.setText(Integer.toString(id));
        address_name.setText(name);
        street_name.setText(street +", "+number);
        //address_number.setText(number);
        //zip_code.setText(zipCode);
        //complement.setText(complnt);
        city.setText(name_city);
        //country.setText(name_country);
        //uf.setText(uf_desc);

        container_address.addView(cardView);
    }
}
