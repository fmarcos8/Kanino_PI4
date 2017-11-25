package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.AddressService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.CEP;
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

public class NewAddressActivity extends AppCompatActivity {
    private EditText insert_cep, desc_endereco, number_address, city, complement, country, street_name, uf;
    private Button btn_save_address;


    Customer c = SharedPrefManager.getInstance(NewAddressActivity.this).getCustomer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);

        insert_cep = (EditText) findViewById(R.id.insert_cep);
        desc_endereco = (EditText) findViewById(R.id.desc_endereco);
        number_address = (EditText) findViewById(R.id.number_address);
        city = (EditText) findViewById(R.id.city);
        complement = (EditText) findViewById(R.id.complement);
        country = (EditText) findViewById(R.id.country);
        street_name = (EditText) findViewById(R.id.street_name);
        uf = (EditText) findViewById(R.id.uf);
        btn_save_address = (Button) findViewById(R.id.btn_save_address);


        final String cep = insert_cep.getText().toString();

        insert_cep.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 8){
                    Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, new AddressDec()).create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://viacep.com.br/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();



                    AddressService serviceA = retrofit.create(AddressService.class);
                    Call<CEP> address = serviceA.getAddressDatas(s.toString());
                    address.enqueue(new Callback<CEP>() {
                        @Override
                        public void onResponse(Call<CEP> call, Response<CEP> response) {
                            CEP dados = response.body();

                            if(response.isSuccessful()){
                                city.setText(dados.getLocalidade());
                                street_name.setText(dados.getLogradouro());
                                uf.setText(dados.getUf());
                            }
                        }

                        @Override
                        public void onFailure(Call<CEP> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btn_save_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id_customer = Long.parseLong(c.getId().toString());
                Address address = new Address(id_customer,
                        desc_endereco.getText().toString(),
                        street_name.getText().toString(),
                        number_address.getText().toString(),
                        insert_cep.getText().toString(),
                        complement.getText().toString(),
                        city.getText().toString(),
                        country.getText().toString(),
                        uf.getText().toString()
                );

                saveAddress(address);
            }
        });


    }

    public void saveAddress(Address address){
        Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, new AddressDec()).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        AddressService serviceA = retrofit.create(AddressService.class);
        Call<Long> addressSerice = serviceA.insertNewAddress(address);
        addressSerice.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Endereço cadastrado com sucesso !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewAddressActivity.this, AddressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", c.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
