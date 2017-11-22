package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.CustomerService;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.ProductService;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CustomerDec;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;
import com.senac.franciscommarcos.navigationviewteste.R;

import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText register_name, register_email, register_password, register_confirm_password, register_cpf, register_cellphone, register_telephone, register_date_birth;
    private CheckBox register_check_newsletter;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_name = (EditText) findViewById(R.id.register_name);
        register_email = (EditText) findViewById(R.id.register_email);
        register_password = (EditText) findViewById(R.id.register_password);
        register_confirm_password = (EditText) findViewById(R.id.register_confirm_password);
        register_cpf = (EditText) findViewById(R.id.register_cpf);
        register_cellphone = (EditText) findViewById(R.id.register_cellphone);
        register_telephone = (EditText) findViewById(R.id.register_telephone);
        register_date_birth = (EditText) findViewById(R.id.register_date_birth);
        register_check_newsletter = (CheckBox) findViewById(R.id.register_check_newsletter);
        btn_register = (Button) findViewById(R.id.btn_register);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check_newsletter;
                if(register_check_newsletter.isChecked()){
                    check_newsletter = "1";
                }else{
                    check_newsletter = "0";
                }
                Customer c = new Customer(register_name.getText().toString(),
                                                 register_email.getText().toString(),
                                                 register_password.getText().toString(),
                                                 register_cpf.getText().toString(),
                                                 register_cellphone.getText().toString(),
                                                 register_telephone.getText().toString(),
                                                 register_date_birth.getText().toString(),
                                                 check_newsletter);
                //Gson gson = new Gson();

                Gson gson =  new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                String customer = gson.toJson(c);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                CustomerService serviceCustomer = retrofit.create(CustomerService.class);
                Call<Integer> registerCall = serviceCustomer.getRegisterResult(c);
                registerCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.body() != null){
                            Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                        }else{
                            System.out.print(response.errorBody().toString());
                            Toast.makeText(getApplicationContext(), "faiou", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        btn_register.setOnClickListener(listener);
    }
}
