package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import com.senac.franciscommarcos.navigationviewteste.Classes.MaskUtil;
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

    boolean cpfValido = true;
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

        register_cpf.addTextChangedListener(MaskUtil.insert(register_cpf, MaskUtil.MaskType.CPF));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = register_name.getText().toString();
                String email = register_email.getText().toString();
                String password = register_password.getText().toString();
                String confirm_password = register_confirm_password.getText().toString();
                String cpf = register_cpf.getText().toString();
                String date_birth = register_date_birth.getText().toString();

                if(name.isEmpty()){
                    register_name.setError("Nome é Obrigatório");
                    register_name.requestFocus();
                    return;
                }

                if(email.isEmpty()){
                    register_email.setError("Email é Obrigatório");
                    register_email.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    register_password.setError("A senha é Obrigatória");
                    register_password.requestFocus();
                    return;
                }

                if(confirm_password.isEmpty()){
                    register_confirm_password.setError("Senha de confirmação é Obrigatória");
                    register_confirm_password.requestFocus();
                    return;
                }

                if(cpf.isEmpty()) {
                    register_cpf.setError("CPF é obrigatório");
                    register_cpf.requestFocus();
                    return;
                }

                if(date_birth.isEmpty()){
                    register_date_birth.setError("Data de nascimento é obrigatória");
                    register_date_birth.requestFocus();
                    return;
                }

                if(!register_password.getText().toString().equals(register_confirm_password.getText().toString())){
                    Toast.makeText(getApplicationContext(), "As senhas não são iguais", Toast.LENGTH_LONG).show();
                    register_password.requestFocus();
                    register_password.setError("");
                    register_confirm_password.requestFocus();
                    register_confirm_password.setError("");
                    return;
                }

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

                Gson gson = new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                CustomerService serviceCustomer = retrofit.create(CustomerService.class);
                Call<Integer> registerCall = serviceCustomer.getRegisterResult(c);

                final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                progress.setTitle("Aguarde");
                progress.setMessage("Realizando cadastro...");
                progress.setCancelable(false);
                progress.show();

                registerCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.body() != null){
                            progress.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Cadastro Efetuado com sucesso!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Ocorreu um erro ao Efetuar seu cadastro...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        progress.dismiss();
                        Intent intent = new Intent(RegisterActivity.this, ErrorConnectionActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        btn_register.setOnClickListener(listener);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public boolean validar(EditText e, String mensagem){
        if(!e.getText().toString().isEmpty()){
            e.setError(mensagem);
            e.requestFocus();
            return true;
        }
        return false;
    }
}
