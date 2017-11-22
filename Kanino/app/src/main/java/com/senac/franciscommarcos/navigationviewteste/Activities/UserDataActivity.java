package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.AddressService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.AddressDec;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDataActivity extends AppCompatActivity {
    private TextView session_name, session_email, session_cpf, session_residencial_phone;
    private CheckBox session_newsletter;
    private TextView session_cell_phone;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        session_name = (TextView) findViewById(R.id.session_name);
        session_email = (TextView) findViewById(R.id.session_email);
        session_cpf = (TextView) findViewById(R.id.session_cpf);
        session_cell_phone = (TextView) findViewById(R.id.session_cell_phone);
        session_residencial_phone = (TextView) findViewById(R.id.session_residencial_phone);
        session_newsletter = (CheckBox) findViewById(R.id.session_newsletter);


        Customer customer = SharedPrefManager.getInstance(UserDataActivity.this).getCustomer();
        Long session_id = customer.getId();
        session_name.setText(customer.getName());
        session_email.setText(customer.getEmail());
        session_cpf.setText(customer.getCpf());
        session_cell_phone.setText(customer.getCell_phone());
        session_residencial_phone.setText(customer.getResidencial_phone());

        if(customer.getSend_newsletter().equals("1")){
            session_newsletter.setChecked(true);
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, new AddressDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AddressService serviceA = retrofit.create(AddressService.class);
        Call<List<Address>> address = serviceA.getAddress(session_id);
        address.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                List<Address> listAddress = response.body();
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }
}
