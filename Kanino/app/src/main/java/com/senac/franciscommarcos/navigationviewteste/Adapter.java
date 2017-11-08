package com.senac.franciscommarcos.navigationviewteste;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by franc on 08/11/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Product> mProducts;
    private boolean mHorizontal;
    private boolean mPager;

    public Adapter(boolean horizontal, boolean pager, List<Product> products) {
        mHorizontal = horizontal;
        mProducts = products;
        mPager = pager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPager) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager, parent, false));
        } else {
            return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false)) :
                    new ViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapter_vertical, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = mProducts.get(position);
        holder.name_product.setText(product.getName());
        holder.price_product.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name_product;
        public TextView price_product;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_product = (TextView) itemView.findViewById(R.id.name_product);
            price_product = (TextView) itemView.findViewById(R.id.price_product);
        }

        @Override
        public void onClick(View v) {
            Log.d("App", mProducts.get(getAdapterPosition()).getName());
        }
    }

}
