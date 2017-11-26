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
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edit_change_password, confirm_password;
    private Button btn_save_new_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edit_change_password = (EditText) findViewById(R.id.edit_change_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        btn_save_new_password = (Button) findViewById(R.id.btn_save_new_password);

        btn_save_new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_password = edit_change_password.getText().toString();
                String confirm_pass = confirm_password.getText().toString();

                if(new_password.isEmpty()){
                    edit_change_password.setError("Senha Obrigatória");
                    edit_change_password.requestFocus();
                    return;
                }
                if(confirm_pass.isEmpty()){
                    confirm_password.setError("Senha de confirmação Obrigatória");
                    confirm_password.requestFocus();
                    return;
                }

                if(!new_password.equals(confirm_pass)){
                    Toast.makeText(getApplicationContext(), "As senhas não são iguais", Toast.LENGTH_LONG).show();
                    edit_change_password.requestFocus();
                    confirm_password.requestFocus();
                    return;
                }

                Gson gson =  new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                Retrofit retrofit =  new Retrofit.Builder()
                        .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                CustomerService service = retrofit.create(CustomerService.class);
                Customer c = SharedPrefManager.getInstance(ChangePasswordActivity.this).getCustomer();
                final long session_id = c.getId();
                Call<Integer> customer = service.changePassword(session_id, new_password);

                final ProgressDialog progress = new ProgressDialog(ChangePasswordActivity.this);
                progress.setTitle("Aguarde");
                progress.setMessage("fazendo alteração...");
                progress.setCancelable(false);
                progress.show();

                customer.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            progress.dismiss();
                            Toast.makeText(getApplicationContext(), "Senha Alterada com Sucesso !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, UserDataActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro ao alterar sua senha !", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progress.dismiss();
                        Intent intent = new Intent(ChangePasswordActivity.this, ErrorConnectionActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
