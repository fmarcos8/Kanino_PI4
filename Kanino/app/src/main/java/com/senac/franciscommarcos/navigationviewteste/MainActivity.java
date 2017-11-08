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
import android.widget.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView product_picture;
    private TextView product_name;
    private TextView product_price;
    private TextView product_description;
    private ListView list_products;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        product_name = (TextView) findViewById(R.id.product_name);
        product_price = (TextView) findViewById(R.id.product_price);
        product_description = (TextView) findViewById(R.id.product_description);

        ProductsFragment fragment = new ProductsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();

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


                        if(menuItem.getItemId() == R.id.action_products){
                            ProductsFragment fragment = new ProductsFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
                            return true;
                        }
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
}
