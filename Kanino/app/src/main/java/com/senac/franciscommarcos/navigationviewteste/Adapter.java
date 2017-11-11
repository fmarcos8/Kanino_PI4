package com.senac.franciscommarcos.navigationviewteste;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.NumberFormat;
import java.util.List;
import com.senac.franciscommarcos.navigationviewteste.ProductsFragment;
/**
 * Created by franc on 08/11/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Product> mProducts;
    private boolean mHorizontal;
    private boolean mPager;
    private Context mContext;
    private String BASE_URL = "http://kanino-pi4.azurewebsites.net/Kanino/api/produtos/imagem/";

    public Adapter(boolean horizontal, boolean pager, List<Product> products, Context context) {
        mHorizontal = horizontal;
        mProducts = products;
        mPager = pager;
        mContext = context;
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
        final Product product = mProducts.get(position);
        final ProductsFragment pf = new ProductsFragment();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        imageLoader.displayImage(BASE_URL+product.getId(), holder.image_product, options);

        holder.name_product.setText(limit(product.getName(), 18));

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                pf.showDetails(product.getId());
            }
        };

        holder.name_product.setOnClickListener(listener);

        holder.price_product.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(product.getPrice())));
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
        public ImageView image_product;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_product = (TextView) itemView.findViewById(R.id.name_product);
            price_product = (TextView) itemView.findViewById(R.id.price_product);
            image_product = (ImageView) itemView.findViewById(R.id.image_product);
        }

        @Override
        public void onClick(View v) {
            Log.d("App", mProducts.get(getAdapterPosition()).getName());
        }
    }

    //Limita o tamanho da String
    public static String limit(String string, int length)
    {
        StringBuilder buf = new StringBuilder(string);
        if (buf.length() > length)
        {
            buf.setLength(length);
            buf.append("...");
        }

        return buf.toString();
    }



}
