package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

public class UserDataActivity extends AppCompatActivity {
    private EditText session_name, session_email, session_cpf, session_cell_phone, session_residencial_phone;
    private CheckBox session_newsletter;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        session_name = (EditText) findViewById(R.id.session_name);
        session_email = (EditText) findViewById(R.id.session_email);
        session_cpf = (EditText) findViewById(R.id.session_cpf);
        session_cell_phone = (EditText) findViewById(R.id.session_cell_phone);
        session_residencial_phone = (EditText) findViewById(R.id.session_cell_phone);
        session_newsletter = (CheckBox) findViewById(R.id.session_newsletter);


        Customer customer = SharedPrefManager.getInstance(UserDataActivity.this).getCustomer();

        session_name.setText(customer.getName());
        session_email.setText(customer.getEmail());
        session_cpf.setText(customer.getCpf());
        session_cell_phone.setText(customer.getCell_phone());
        session_residencial_phone.setText(customer.getResidencial_phone());

        if(customer.getSend_newsletter().equals("1")){
            session_newsletter.setChecked(true);
        }
    }
}
