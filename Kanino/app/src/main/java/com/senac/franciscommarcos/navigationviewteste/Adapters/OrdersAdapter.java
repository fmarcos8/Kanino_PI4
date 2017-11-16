package com.senac.franciscommarcos.navigationviewteste.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senac.franciscommarcos.navigationviewteste.Models.Order;
import com.senac.franciscommarcos.navigationviewteste.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 15/11/2017.
 */

public class OrdersAdapter extends RecyclerView.Adapter {
    private List<Order> orders = new ArrayList<>();
    private Context mContext;

    public OrdersAdapter(List<Order> orders, Context mContext) {
        this.orders = orders;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.orders_layout, parent, false);
        OrdersViewHolder holder = new OrdersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrdersViewHolder oHolder = (OrdersViewHolder) holder;

        Order order  = orders.get(position) ;

        oHolder.id_order.setText(order.getId());

        if(order.getDescStatus().equals("Aberto")){
            oHolder.status_order.setTextColor(Color.rgb(255,255,0));
        }else if(order.getDescStatus().equals("Aguardando Pagamento")){
            oHolder.status_order.setTextColor(Color.rgb(255,255,0));
        }else if(order.getDescStatus().equals("Enviado para Transportadora")){
            oHolder.status_order.setTextColor(Color.rgb(0,0,200));
        }
        oHolder.status_order.setText(limit(order.getDescStatus(), 12));
        oHolder.date_order.setText(order.getOrderDate());
        oHolder.value_order.setText(NumberFormat.getCurrencyInstance().format(Double.parseDouble(order.getTotalPrice())));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView id_order;
        public TextView status_order;
        public TextView date_order;
        public TextView value_order;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            id_order = (TextView) itemView.findViewById(R.id.id_order);
            status_order = (TextView) itemView.findViewById(R.id.status_order);
            date_order = (TextView) itemView.findViewById(R.id.date_order);
            value_order = (TextView) itemView.findViewById(R.id.value_order);
        }
    }

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
