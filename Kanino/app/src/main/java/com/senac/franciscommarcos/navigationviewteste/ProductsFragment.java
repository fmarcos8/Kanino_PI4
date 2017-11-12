package com.senac.franciscommarcos.navigationviewteste;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        category.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> listCat = response.body();
                for(Category cat : listCat){
                    categories.add(new Category(cat.getId(), cat.getName()));
                }
                setupAdapter();
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "erro" , Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(ORIENTATION, mHorizontal);
    }

    private void setupAdapter() {
        for (Category c : categories ) {
            getProducts(c.getName(),c.getId());
        }
    }

    public void getProducts(final String nameCategory, int id){

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
                List<Product> products = response.body();

                if(products.size()>0) {
                    snapAdapter.addSnap(new Snap(Gravity.START, nameCategory, products, getContext()));
                }
                mRecyclerView.setAdapter(snapAdapter);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

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