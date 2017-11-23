package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.widget.Toast;

import com.senac.franciscommarcos.navigationviewteste.ProductFragment;
import com.senac.franciscommarcos.navigationviewteste.ProductsFragment;
import com.senac.franciscommarcos.navigationviewteste.QrCodeReader;
import com.senac.franciscommarcos.navigationviewteste.R;
import com.senac.franciscommarcos.navigationviewteste.RegisterFragment;
import com.senac.franciscommarcos.navigationviewteste.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction ft;
    private String qrcode = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
                            getApplicationContext().startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
