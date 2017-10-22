package com.senac.franciscommarcos.navigationviewteste;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private TextView product_name;
    private TextView product_price;
    private TextView product_description;

    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product, container, false);

        product_name = (TextView) v.findViewById(R.id.product_name);
        product_price = (TextView) v.findViewById(R.id.product_price);
        product_description = (TextView) v.findViewById(R.id.product_description);

        String id = Integer.toString(getArguments().getInt("id"));

        NetWorkCall myCall = new NetWorkCall();
        myCall.execute("http://kanino-pi4.azurewebsites.net/Kanino/api/produto/"+id);
        return v;
    }

    public class NetWorkCall extends AsyncTask<String, Void, String> {

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

            try {
                JSONObject json = new JSONObject(result);

                String name = json.getString("name");
                String price = json.getString("price");
                String description = json.getString("description");

                product_name.setText(name);
                product_price.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(price)));
                product_description.setText(description);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
