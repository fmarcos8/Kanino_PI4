package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CustomerDec;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditUserDataActivity extends AppCompatActivity {

    private EditText update_name, update_email, update_cpf, update_cellphone, update_residencial_phone, update_date_birth;
    private CheckBox update_newsletter;
    private Button btn_save_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_data);

        update_name = (EditText) findViewById(R.id.update_name);
        update_email = (EditText) findViewById(R.id.update_email);
        update_cpf = (EditText) findViewById(R.id.update_cpf);
        update_cellphone = (EditText) findViewById(R.id.update_cellphone);
        update_residencial_phone = (EditText) findViewById(R.id.update_residencial_phone);
        update_newsletter = (CheckBox) findViewById(R.id.update_newsletter);
        btn_save_update = (Button) findViewById(R.id.btn_save_update);

        final Customer c = SharedPrefManager.getInstance(this).getCustomer();
        final Long id = c.getId();
        update_name.setText(c.getName());
        update_email.setText(c.getEmail());
        update_cpf.setText(c.getCpf());
        update_cellphone.setText(c.getCell_phone());
        update_residencial_phone.setText(c.getResidencial_phone());


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String check_newsletter;
                if(update_newsletter.isChecked()){
                    check_newsletter = "1";
                }else{
                    check_newsletter = "0";
                }

                Customer update_c = new Customer(
                        id,
                        update_email.getText().toString(),
                        update_name.getText().toString(),
                        update_cpf.getText().toString(),
                        update_cellphone.getText().toString(),
                        update_residencial_phone.getText().toString(),
                        update_date_birth.getText().toString(),
                        check_newsletter
                );

                Gson gson =  new GsonBuilder().registerTypeAdapter(Customer.class, new CustomerDec()).create();
                Retrofit retrofit =  new Retrofit.Builder()
                        .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                CustomerService service = retrofit.create(CustomerService.class);
                Call<Long> customer = service.updatePersonalDatas(update_c);

                customer.enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if(response.isSuccessful()){
                            /*SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("customerSession", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            SharedPrefManager.getInstance(EditUserDataActivity.this).customerLogin(c);
                            Intent intent = new Intent(EditUserDataActivity.this, UserDataActivity.class);
                            startActivity(intent);*/
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        };
        btn_save_update.setOnClickListener(listener);
    }
}
