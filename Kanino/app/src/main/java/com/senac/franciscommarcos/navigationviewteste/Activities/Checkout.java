package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.CustomerService;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.OrderService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.CheckoutModel;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CustomerDec;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.OrderDec;
import com.senac.franciscommarcos.navigationviewteste.ProductFragment;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Checkout extends AppCompatActivity {

    private TextView cart_total;
    private Button btn_finish;
    private RadioButton rb_boleto, rb_cartao_credito;
    private EditText et_cod_seguranca, et_num_cartao, et_name, et_cpf;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";
    CheckoutModel ch = new CheckoutModel();

    private String paymentType = "";
    private String paymentStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cart_total = (TextView) findViewById(R.id.tv_total);
        btn_finish = (Button) findViewById(R.id.bt_finalizar);
        rb_boleto = (RadioButton) findViewById(R.id.rb_boleto);
        rb_cartao_credito = (RadioButton) findViewById(R.id.rb_cartao_credito);
        et_cod_seguranca = (EditText) findViewById(R.id.et_cod_seguranca);
        et_num_cartao = (EditText) findViewById(R.id.et_num_cartao);
        et_name = (EditText) findViewById(R.id.et_name);
        et_cpf = (EditText) findViewById(R.id.et_cpf);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        spinner = (Spinner) findViewById(R.id.sp_spinner);

        double cartTotal = CartSingleton.getInstance().getTotal();
        cart_total.setText(NumberFormat.getCurrencyInstance().format(cartTotal));


        spinner = (Spinner) findViewById(R.id.sp_spinner);

        final List<Address> addresses = CartSingleton.getInstance().getAddresses();

        List<String> list = new ArrayList<String>();

        for(Address a : addresses){
            list.add(a.getAddress_name());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                Address addressSelected = addresses.get(pos);
                ch.setIdAddress(Integer.toString(addressSelected.getId_address()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        rb_boleto.isChecked();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId){
                if(rb_cartao_credito.isChecked()){
                    et_cod_seguranca.setVisibility(View.VISIBLE);
                    et_num_cartao.setVisibility(View.VISIBLE);
                    paymentType = "1";
                    paymentStatus = "3";

                    String cartao_de_credito = et_num_cartao.getText().toString();
                    String cod_seguranca = et_cod_seguranca.getText().toString();

                    if(cartao_de_credito.isEmpty() || cartao_de_credito.length() < 16){
                        et_num_cartao.setError("Digite todos os digitos do cartao");
                        et_num_cartao.requestFocus();
                        return;
                    }
                    if(cod_seguranca.isEmpty() || cod_seguranca.length() < 3){
                        et_cod_seguranca.setError("Digite todos os digitos de segurança do cartao");
                        et_cod_seguranca.requestFocus();
                        return;
                    }

                }
                if(rb_boleto.isChecked()){
                    et_cod_seguranca.setVisibility(View.INVISIBLE);
                    et_num_cartao.setVisibility(View.INVISIBLE);
                    paymentType = "2";
                    paymentStatus = "2";
                }
            }
        });


        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome_cliente = et_name.getText().toString();
                String cpf_cliente = et_cpf.getText().toString();

                if(nome_cliente.isEmpty()){
                    et_name.setError("Digite o nome impresso no cartâo");
                    et_name.requestFocus();
                    return;
                }
                if(cpf_cliente.isEmpty() || cpf_cliente.length() < 11){
                    et_cpf.setError("Digite CPF do titular do cartão");
                    et_cpf.requestFocus();
                    return;
                }

                List<Product> cart = CartSingleton.getInstance().getCartList();
                List<Product> checkout = new ArrayList<>();
                for(Product p : cart){
                    checkout.add(new Product(p.getId(), p.getQtd(), p.getPrice()));
                }
                ch.setProducts(checkout);
                ch.setIdApplication(Integer.toString(2));
                Customer customer = SharedPrefManager.getInstance(Checkout.this).getCustomer();
                ch.setIdCustomer(Long.toString(customer.getId()));
                ch.setIdPaymentType(paymentType);
                ch.setIdStatus(paymentStatus);
                String date = android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()).toString();
                ch.setOrderDate(date);

                Gson gson =  new GsonBuilder().registerTypeAdapter(Checkout.class, new OrderDec()).create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                OrderService serviceOrder = retrofit.create(OrderService.class);
                Call<Integer> orderCall = serviceOrder.checkout(ch);
                orderCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("RESPONSE", response.toString());
                        if(response.body() != null){
                            Alert("Seu pedido foi gerado com sucesso", "Compra aprovada!");
                        }else{
                            Toast.makeText(getApplicationContext(), "erro", Toast.LENGTH_SHORT).show();
                        }
                        List<Product> cart = CartSingleton.getInstance().getCartList();
                        CartSingleton.getInstance().setTotal(0);
                        cart.clear();

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "erro", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void Alert (String msg, String title){

        AlertDialog.Builder builder = new
                AlertDialog.Builder(Checkout.this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Checkout.this, OrdersList.class);
                        startActivity(intent);

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

