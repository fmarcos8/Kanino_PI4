package com.senac.franciscommarcos.navigationviewteste;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

    //private ViewGroup list_category;
    public static final String ORIENTATION = "orientation";
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    //private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();


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



        setupAdapter();

        Gson gson = new GsonBuilder().registerTypeAdapter(Category.class, new CategoryDec()).create();
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
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
        List<Product> products = getProducts();
        SnapAdapter snapAdapter = new SnapAdapter();
        if(mHorizontal){
            for(Product p: products){
                snapAdapter.addSnap(new Snap(Gravity.START, p.getName(), products));
            }
        }

        mRecyclerView.setAdapter(snapAdapter);

    }

    private List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        products.add(new Product("Colera", "4,66"));
        return products;
    }

    /*public void getProducts(final String nameCategory, int id){
        Gson gson = new GsonBuilder().registerTypeAdapter(Product.class, new ProductDec()).create();
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://kanino-pi4.azurewebsites.net/Kanino/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ProductService service2 = retrofit.create(ProductService.class);
        final Call<List<Product>> product = service2.getProductsPerCategory(id);

        product.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> list = response.body();
                int i = 0;
                for(Product p : list){
                    List<Product> ;
                    products.add(p);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }*/

    //private List<Product> getProducts(){

    //}

/*
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

            categories = new Gson().fromJson(result, new TypeToken<List<Category>>(){}.getType());

            for(int i = 0; i < categories.size(); i++){
                //addItemInCard(categories.get(i).getId(), categories.get(i).getName());
            }
        }
    }

    /*public void addItemInCard(int id_product, String name_category){
        CardView cardView = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.card_item_layout, list_category, false);
        TextView category_name = (TextView) cardView.findViewById(R.id.category_name);

        TextView id = (TextView) cardView.findViewById(R.id.id_product);
        ImageView picture = (ImageView) cardView.findViewById(R.id.product_picture);
        TextView price = (TextView) cardView.findViewById(R.id.product_price);
        Button btn_details = (Button) cardView.findViewById(R.id.btn_details);

        id.setText(Integer.toString(id_product));
        name.setText(name_product);
        //price.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(price_product)));

        final int idProduto = id_product;
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                showDetails(idProduto);
            }
        };
        btn_details.setOnClickListener(listener);

        category_name.setText(name_category);
        list_category.addView(cardView);

    }

    public void showDetails(int id){
        ProductFragment fragment_details = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        fragment_details.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frag_container, fragment_details).commit();
    }*/
}
