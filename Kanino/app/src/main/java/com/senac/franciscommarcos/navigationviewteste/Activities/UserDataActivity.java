package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

public class UserDataActivity extends AppCompatActivity {
    private TextView session_name, session_email, session_cpf, session_residencial_phone;
    private CheckBox session_newsletter;
    private TextView session_cell_phone, session_date_birth;
    private Button change_password, btn_open_edit_data, btn_open_address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        session_name = (TextView) findViewById(R.id.session_name);
        session_email = (TextView) findViewById(R.id.session_email);
        session_cpf = (TextView) findViewById(R.id.session_cpf);
        session_cell_phone = (TextView) findViewById(R.id.session_cell_phone);
        session_residencial_phone = (TextView) findViewById(R.id.session_residencial_phone);
        session_date_birth = (TextView) findViewById(R.id.session_date_birth);
        session_newsletter = (CheckBox) findViewById(R.id.session_newsletter);
        change_password = (Button) findViewById(R.id.change_password);
        btn_open_edit_data = (Button) findViewById(R.id.btn_open_edit_data);
        btn_open_address = (Button) findViewById(R.id.btn_open_address);

        Customer customer = SharedPrefManager.getInstance(UserDataActivity.this).getCustomer();
        final long session_id = customer.getId();
        session_name.setText(customer.getName());
        session_email.setText(customer.getEmail());
        session_cpf.setText(customer.getCpf());
        session_cell_phone.setText(customer.getCell_phone());
        session_residencial_phone.setText(customer.getResidencial_phone());
        session_date_birth.setText(customer.getBirth());
        if(customer.getSend_newsletter().equals("1")){
            session_newsletter.setChecked(true);
        }

        View.OnClickListener listenerChangePass= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDataActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener listenerEditData = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserDataActivity.this, EditUserDataActivity.class);
                startActivity(intent);
            }
        };

        View.OnClickListener listenerAddress = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserDataActivity.this, AddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("id", session_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        change_password.setOnClickListener(listenerChangePass);
        btn_open_edit_data.setOnClickListener(listenerEditData);
        btn_open_address.setOnClickListener(listenerAddress);
    }
}
