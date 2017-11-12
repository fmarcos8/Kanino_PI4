package com.senac.franciscommarcos.navigationviewteste;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{
    private EditText login_email, login_password;
    private Button btnForgo, btn_login;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        login_email = (EditText) view.findViewById(R.id.login_email);
        login_password = (EditText) view.findViewById(R.id.login_password);
        btn_login = (Button) view.findViewById(R.id.btn_login);

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
                            .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/api/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build();

                    CustomerService service = retrofit.create(CustomerService.class);
                    Call<Customer> customer = service.login(email, password);

                    customer.enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(Call<Customer> call, Response<Customer> response) {

                            Customer customer_profile = response.body();
                            if(response.isSuccessful()){
                                if(customer_profile != null){

                                    SharedPrefManager.getInstance(getContext()).customerLogin(customer_profile);
                                    UserData userData = new UserData();
                                    FragmentManager fm = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.addToBackStack("customer_profile").replace(R.id.frag_container, userData).commit();

                                }else{
                                    Toast.makeText(getContext(), "erro" , Toast.LENGTH_SHORT).show();
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
        return view;
    }
}
