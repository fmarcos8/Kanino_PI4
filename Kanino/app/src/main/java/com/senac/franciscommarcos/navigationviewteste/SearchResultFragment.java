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

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_result, container, false);


        listView = (ListView) v.findViewById(R.id.list_search);
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(getContext(), android.R.layout.simple_list_item_1, getList_prod());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getContext(), getList_prod().get(position).getName(), Toast.LENGTH_LONG).show();
            }

        });

        return v;
    }

}
