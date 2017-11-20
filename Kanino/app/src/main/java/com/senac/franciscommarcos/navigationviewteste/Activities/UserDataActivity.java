package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

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
