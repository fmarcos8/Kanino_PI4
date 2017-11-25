package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final ListView list_address = (ListView) findViewById(R.id.list_address);
        final ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1, listAddress);

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


                    list_address.setAdapter(adapter);
                    /*for(Address address : listAddress){
                        addAddress(address.getId_address(),
                                address.getAddress_name(),
                                address.getStreet_name(),
                                address.getAddress_number(),
                                address.getZip_code(),
                                address.getComplement(),
                                address.getCity(),
                                address.getCountry(),
                                address.getUf());
                    }*/
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }
}
