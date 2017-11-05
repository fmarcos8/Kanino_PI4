package com.senac.franciscommarcos.navigationviewteste;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserData extends Fragment {
    private EditText session_name, session_email, session_cpf, session_cell_phone, session_residencial_phone;
    private CheckBox session_newsletter;

    public UserData() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_data, container, false);

        session_name = (EditText) view.findViewById(R.id.session_name);
        session_email = (EditText) view.findViewById(R.id.session_email);
        session_cpf = (EditText) view.findViewById(R.id.session_cpf);
        session_cell_phone = (EditText) view.findViewById(R.id.session_cell_phone);
        session_residencial_phone = (EditText) view.findViewById(R.id.session_cell_phone);
        session_newsletter = (CheckBox) view.findViewById(R.id.session_newsletter);


        Customer customer = SharedPrefManager.getInstance(getContext()).getCustomer();

        session_name.setText(customer.getName());
        session_email.setText(customer.getEmail());
        session_cpf.setText(customer.getCpf());
        session_cell_phone.setText(customer.getCell_phone());
        session_residencial_phone.setText(customer.getResidencial_phone());

        if(customer.getSend_newsletter().equals("1")){
            session_newsletter.setChecked(true);
        }

        return view;

    }

}
