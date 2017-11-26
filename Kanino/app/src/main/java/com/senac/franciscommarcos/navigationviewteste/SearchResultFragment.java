package com.senac.franciscommarcos.navigationviewteste;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.senac.franciscommarcos.navigationviewteste.Models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {
    private ListView listView;
    public List<Product> getList_prod() {
        return list_prod;
    }

    public void setList_prod(List<Product> list_prod) {
        this.list_prod = list_prod;
    }

    private List<Product> list_prod = new ArrayList<Product>();
    List<String> names_prod = new ArrayList<>();

    ProductsFragment pf = new ProductsFragment();

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);

        for(int i = 0; i < list_prod.size(); i++){
            names_prod.add(list_prod.get(i).getName());
        }

        listView = (ListView) v.findViewById(R.id.list_search);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, names_prod);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                pf.showDetails(getList_prod().get(position).getId(), getFragmentManager());

            }

        });

        return v;
    }

    public void onBackPress(){

    }

}
