package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.CustomerService;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CustomerDec;
import com.senac.franciscommarcos.navigationviewteste.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btn_send_email;
    private EditText email_to_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_to_send = (EditText) findViewById(R.id.email_to_send);
        btn_send_email = (Button) findViewById(R.id.btn_send_email);

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_to_send.getText().toString();
                if(email.isEmpty()){
                    email_to_send.setError("Email Obrigatório");
                    email_to_send.requestFocus();
                    return;
                }

                try{
                    Gson gson = new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    CustomerService forgotService = retrofit.create(CustomerService.class);
                    Call<String> forgotPassword = forgotService.forgotPassword(email);
                    final ProgressDialog progress = new ProgressDialog(ForgotPasswordActivity.this);
                    progress.setTitle("Aguarde");
                    progress.setMessage("Estamos enviado um email com as instruções...");
                    progress.setCancelable(false);
                    progress.show();
                    forgotPassword.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Verifique as instruções enviadas no seu email...", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro durante o envio do email.", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progress.dismiss();
                            Intent intent = new Intent(ForgotPasswordActivity.this, ErrorConnectionActivity.class);
                            startActivity(intent);
                        }
                    });
                }catch (Exception e){
                    throw e;
                }
            }
        });
    }
}
