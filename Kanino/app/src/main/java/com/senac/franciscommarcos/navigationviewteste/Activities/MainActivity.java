package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.AddressService;
import com.senac.franciscommarcos.navigationviewteste.Models.Address;
import com.senac.franciscommarcos.navigationviewteste.Models.Customer;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.AddressDec;
import com.senac.franciscommarcos.navigationviewteste.ProductFragment;
import com.senac.franciscommarcos.navigationviewteste.ProductsFragment;
import com.senac.franciscommarcos.navigationviewteste.QrCodeReader;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.SearchResultFragment;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.senac.franciscommarcos.navigationviewteste.R.styleable.MaterialSearchView;
import static com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton.getInstance;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String qrcode = null;
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().show();
        //toolbar.setSubtitleTextColor(Color.parseColor("#FFFFFF"));

        //searchView = (MaterialSearchView) findViewById(R.id.search_view);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ProductsFragment fragment = new ProductsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, fragment).commit();
        //searchViewCode();

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

                        if(menuItem.getItemId() == R.id.action_qrCodeReader){
                            Intent intent = new Intent(MainActivity.this, QrCodeReader.class);
                            startActivityForResult(intent, 1);
                            return true;
                        }

                        if(menuItem.getItemId() == R.id.action_my_profile){
                            Intent intent = new Intent(MainActivity.this, UserDataActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_orders){
                            Intent intent = new Intent(MainActivity.this, OrdersList.class);
                            startActivity(intent);
                            return true;
                        }

                        if(menuItem.getItemId() == R.id.action_products){
                            ProductsFragment fragment = new ProductsFragment();
                            getSupportFragmentManager().beginTransaction().addToBackStack("products_fragment").replace(R.id.frag_container, fragment).commit();
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_login){
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_cart){
                            Intent intent = new Intent(MainActivity.this, CartActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_register){
                            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_logout){
                            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("customerSession", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        if(menuItem.getItemId() == R.id.action_about){
                            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                            startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                }
        );

        SharedPreferences sessionCustomer = getSharedPreferences("customerSession", MODE_PRIVATE);
        boolean isLogged = sessionCustomer.getBoolean("sessionLogged", false);

        if(isLogged){
            navigationView.getMenu().findItem(R.id.action_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.action_register).setVisible(false);

            Gson gson = new GsonBuilder().registerTypeAdapter(Address.class, new AddressDec()).create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            AddressService serviceA = retrofit.create(AddressService.class);
            Customer customer = SharedPrefManager.getInstance(MainActivity.this).getCustomer();
            Call<List<Address>> address = serviceA.getAddress(customer.getId());
            address.enqueue(new Callback<List<Address>>() {
                @Override
                public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                    List<Address> addresses = response.body();
                    if(response.isSuccessful()){
                        List<Address> list_address = CartSingleton.getInstance().getAddresses();
                        for(Address a : addresses){
                            list_address.add(a);
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<Address>> call, Throwable t) {

                }
            });

        }else{
            navigationView.getMenu().findItem(R.id.action_orders).setVisible(false);
            navigationView.getMenu().findItem(R.id.action_my_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.action_logout).setVisible(false);

        }

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer){};
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

   /* private void searchViewCode(){

        String[] nomes = new String[initData().size()];
        int i = 0;
        for(String n : initData()){
            nomes[i] = n;
            i++;
        }
        searchView.setSuggestions(nomes);
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_cart, menu);
        MenuItem search = menu.findItem(R.id.action_search);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);


        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Product> search_list = CartSingleton.getInstance().getProducts_search();
                List<Product> productsFound = new ArrayList<Product>();
                for(Product p : search_list){
                    if(p.getName().contains(query)){
                        productsFound.add(p);
                    }
                }
                if (productsFound.size() > 0) {

                    ProductsFragment fragment = new ProductsFragment();
                    SearchResultFragment frag_search = new SearchResultFragment();
                    frag_search.setList_prod(productsFound);

                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, frag_search).commit();

                } else {
                    Toast.makeText(MainActivity.this, "nenhum produto encontrado",Toast.LENGTH_LONG );
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
//        if(item.getItemId() ==  R.id.action_search){
//            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//            startActivity(intent);
//            return true;
//        }

       if(item.getItemId() == R.id.action_cart2){
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        }

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private ArrayList<String> initData() {
        List<Product> search_list = getInstance().getProducts_search();
        ArrayList<String> names = new ArrayList<>();
        for(Product p : search_list){
            names.add(p.getName());
        }
        return names;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 0){
            return;
        }
        String id_product = data.getExtras().getString("id_product");
        qrcode = id_product;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(qrcode != null)
        {
            String id_product = qrcode;
            qrcode = null;
            ProductFragment fragment_details = new ProductFragment();
            Bundle bundleFrag = new Bundle();

            char primeiraLetra = id_product.charAt(0);

            if (primeiraLetra != 'K') {
                Toast.makeText(getApplicationContext(), "Produto não encontrado!" , Toast.LENGTH_SHORT).show();
            } else {
                id_product = id_product.toUpperCase().replace("K", "");
                int product_id_int;
                product_id_int = Integer.parseInt(id_product);
                bundleFrag.putInt("id", product_id_int);
                fragment_details.setArguments(bundleFrag);
                getSupportFragmentManager().beginTransaction().addToBackStack("product_fragment").replace(R.id.frag_container, fragment_details).commitAllowingStateLoss();
            }
        }
    }

}
