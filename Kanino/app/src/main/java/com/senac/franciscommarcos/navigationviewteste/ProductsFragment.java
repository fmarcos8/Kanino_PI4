package com.senac.franciscommarcos.navigationviewteste;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.senac.franciscommarcos.navigationviewteste.Activities.MainActivity;
import com.senac.franciscommarcos.navigationviewteste.Adapters.Snap;
import com.senac.franciscommarcos.navigationviewteste.Adapters.SnapAdapter;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.CategoryService;
import com.senac.franciscommarcos.navigationviewteste.Interfaces.ProductService;
import com.senac.franciscommarcos.navigationviewteste.Models.Category;
import com.senac.franciscommarcos.navigationviewteste.Models.Product;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.CategoryDec;
import com.senac.franciscommarcos.navigationviewteste.ObjectDec.ProductDec;
import com.senac.franciscommarcos.navigationviewteste.Singleton.CartSingleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment{

    public static final String ORIENTATION = "orientation";
    public static final String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/";
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    private List<Category> categories = new ArrayList<>();
    private SnapAdapter snapAdapter = new SnapAdapter();
    public List<Product> products = new ArrayList<>();
    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        if(savedInstanceState == null){
            mHorizontal = true;
        }else{
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(Category.class, new CategoryDec()).create();
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CategoryService service = retrofit.create(CategoryService.class);
        final Call<List<Category>> category = service.getCategories();
        final ProgressDialog progress = new ProgressDialog(getContext());
        category.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                List<Category> listCat = response.body();

                for(Category cat : listCat){
                    categories.add(new Category(cat.getId(), cat.getName()));
                }
                setupAdapter(progress);
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(), t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });


        progress.setTitle("Aguarde");
        progress.setMessage("Carregando Lista de produtos...");
        progress.setCancelable(false);
        progress.show();

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    private void setupAdapter(ProgressDialog progress) {
        CartSingleton.getInstance().getProducts_search().clear();
        for (Category c : categories ) {
            getProducts(c.getName(),c.getId(), progress);
        }
    }

    public void getProducts(final String nameCategory, int id, final ProgressDialog progress){
        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductService service2 = retrofit.create(ProductService.class);
        final Call<List<Product>> product = service2.getProductsPerCategory(id);

        product.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                products = response.body();

                if(response.isSuccessful()){
                    if(products.size()>0) {
                        snapAdapter.addSnap(new Snap(Gravity.START, nameCategory, products, getContext(), getFragmentManager()));
                    }
                    List<Product> search = CartSingleton.getInstance().getProducts_search();
                    for(Product p : products){
                        CartSingleton.getInstance().setProducts_search(p);
                    }
                }
                mRecyclerView.setAdapter(snapAdapter);
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public void showDetails(int id, FragmentManager fm){
        ProductFragment fragment_details = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment_details.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack("product_detail").replace(R.id.frag_container, fragment_details).commit();
    }
}
