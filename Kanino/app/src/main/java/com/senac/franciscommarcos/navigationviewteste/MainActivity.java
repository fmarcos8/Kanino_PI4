package com.senac.franciscommarcos.navigationviewteste;

import java.text.NumberFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView product_picture;
    private TextView product_name;
    private TextView product_price;
    private TextView product_description;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        product_name = (TextView) findViewById(R.id.product_name);
        product_price = (TextView) findViewById(R.id.product_price);
        product_description = (TextView) findViewById(R.id.product_description);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    public boolean onNavigationItemSelected(MenuItem menuItem){
                        if(menuItem.isChecked()){
                            menuItem.setChecked(false);
                        }else{
                            menuItem.setChecked(true);
                        }

                        drawerLayout.closeDrawers();
                        if(menuItem.getItemId() == R.id.action_login){
                            LoginFragment fragment = new LoginFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_register){
                            RegisterFragment fragment = new RegisterFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_register){
                            RegisterFragment fragment = new RegisterFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
                            return true;
                        }
                        return false;
                    }
                }
        );

        NetWorkCall myCall = new NetWorkCall();
        //myCall.execute("http://kanino-pi4.azurewebsites.net/Kanino/api/produtos");
        myCall.execute("http://kanino-pi4.azurewebsites.net/Kanino/api/produto/8");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer){};
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class NetWorkCall extends AsyncTask<String, Void, String>{
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
            try{
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
