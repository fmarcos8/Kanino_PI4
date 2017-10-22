package com.senac.franciscommarcos.navigationviewteste;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {
    private ViewGroup list_products;

    public ProductsFragment() {
        // Required empty public constructor
    }

    List<Product> products = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        list_products = (ViewGroup) v.findViewById(R.id.list_products);

        NetWorkCall myCall = new NetWorkCall();
        myCall.execute("http://kanino-pi4.azurewebsites.net/Kanino/api/produtos");

        return v;




    }


    public class NetWorkCall extends AsyncTask<String, Void, String> {
        Bitmap bmp = null;
        @Override
        protected String doInBackground(String... params) {
            try{
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                urlConnection.setConnectTimeout(4000);
                InputStream in =  urlConnection.getInputStream();
                BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder resultado = new StringBuilder();
                String linha = bufferedReader.readLine();



                while(linha != null){
                    resultado.append(linha);
                    linha = bufferedReader.readLine();
                }

                String respostaCompleta = resultado.toString();

                return respostaCompleta;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);

            products = new Gson().fromJson(result, new TypeToken<List<Product>>(){}.getType());

            for(int i = 0; i < products.size(); i++){
                addItemInCard(products.get(i).getId(), products.get(i).getName(), products.get(i).getPrice());
            }


        }
    }

    public void addItemInCard(int id_product, String name_product, String price_product){
        CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_item_layout, list_products, false);

        TextView id = (TextView) cardView.findViewById(R.id.id_product);
        ImageView picture = (ImageView) cardView.findViewById(R.id.product_picture);
        TextView name = (TextView) cardView.findViewById(R.id.product_name);
        TextView price = (TextView) cardView.findViewById(R.id.product_price);
        Button btn_details = (Button) cardView.findViewById(R.id.btn_details);

        id.setText(Integer.toString(id_product));
        name.setText(name_product);
        price.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(price_product)));

        final int idProduto = id_product;
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                showDetails(idProduto);
            }
        };
        btn_details.setOnClickListener(listener);

        list_products.addView(cardView);

    }

    public void showDetails(int id){
        ProductFragment fragment_details = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment_details.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, fragment_details).commit();
    }
}
