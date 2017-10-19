package com.senac.franciscommarcos.navigationviewteste;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter{
    private final List<Product> list_products;
    private final Activity activity;

    public ListAdapter(List<Product> list_products, Activity activity) {
        this.list_products = list_products;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.activity_main, parent, false);
        Product product = list_products.get(position);

        TextView product_name = (TextView) view.findViewById(R.id.product_name);
        TextView product_price = (TextView) view.findViewById(R.id.product_price);

        product_name.setText(product.getName());
        product_price.setText(product.getPrice());
        return view;
    }
}
