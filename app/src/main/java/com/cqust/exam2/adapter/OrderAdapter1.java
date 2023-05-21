package com.cqust.exam2.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.Order;
import com.cqust.exam2.dataBaice.ContactsDBHelp;
import com.cqust.exam2.dataBaice.OrderDBHelp;

import java.util.List;

public class OrderAdapter1 extends BaseAdapter {

    private final Context context;
    private final List<Order> orders;
    private final String TABLE_NAME = "order_table";
    private final String state;

    public OrderAdapter1(Context context, List<Order> orders, String state) {
        this.context = context;
        this.orders = orders;
        this.state = state;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Order order = (Order) getItem(i);
        ViewHold viewHold;
        View v;
        if(view == null){
            v = LayoutInflater.from(context).inflate(R.layout.adapter_order_view, null);
            viewHold = new ViewHold();
            viewHold.order_name = v.findViewById(R.id.adapter_tv);
            v.setTag(viewHold);
        }else {
            v = view;
            viewHold = (ViewHold) v.getTag();
        }

        if(state.equals("100")){
            viewHold.order_name.setText(order.getName());
        }else if (state.equals("200")) {
            viewHold.order_name.setText(order.getSize());
        }else if (state.equals("300")){
            viewHold.order_name.setText(order.getContext());
        }

        return v;
    }

    class ViewHold{
        TextView order_name;
    }
}
