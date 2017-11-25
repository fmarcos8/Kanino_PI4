package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CustomerDec;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.CustomerService;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email;
    private EditText login_password;
    private TextView btn_forgot_password;
    private Button btn_login;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot_password = (TextView) findViewById(R.id.btn_forgot_password);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if(email.isEmpty()){
                    login_email.setError("Email Obrigatório");
                    login_email.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    login_password.setError("Senha Obrigatória");
                    login_password.requestFocus();
                    return;
                }

                try{
                    Gson gson =  new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                    Retrofit retrofit =  new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    CustomerService service = retrofit.create(CustomerService.class);
                    Call<Customer> customer = service.login(email, password);
                    final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                    progress.setTitle("Aguarde");
                    progress.setMessage("Estamos verificando seus dados");
                    progress.setCancelable(false);
                    progress.show();
                    customer.enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {

                            Customer customer_profile = response.body();
                            if(response.isSuccessful()){
                                if(customer_profile != null){
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Login feito com Sucesso" , Toast.LENGTH_LONG).show();
                                    SharedPrefManager.getInstance(LoginActivity.this).customerLogin(customer_profile);
                                    Intent intent = new Intent(LoginActivity.this, UserDataActivity.class);
                                    startActivity(intent);

                                }else{
                                    progress.dismiss();
                                    Toast.makeText(getApplicationContext(), "Seu Email ou Senha estão incorretos !" , Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Customer> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }catch(Exception e){
                    throw e;
                }
            }
        };

        btn_login.setOnClickListener(listener);

        btn_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }


}
