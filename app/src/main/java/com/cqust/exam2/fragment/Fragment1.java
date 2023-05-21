package com.cqust.exam2.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqust.exam2.R;
import com.cqust.exam2.activity.OrderActivity;
import com.cqust.exam2.activity.ShowOrderActivity;
import com.cqust.exam2.adapter.ContactsAdapter;
import com.cqust.exam2.adapter.OrderAdapter1;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.bean.Order;
import com.cqust.exam2.dao.OrderDao;
import com.cqust.exam2.dataBaice.ContactsDBHelp;
import com.cqust.exam2.dataBaice.OrderDBHelp;

import java.util.List;

public class Fragment1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String state = "100", click_name = "";
    private Boolean fist_start = true;
    private ImageView icon;
    private ListView order_lv;
    private List<Order> orderList;
    OrderDao orderDao = new OrderDao();
    private OrderAdapter1 orderAdapter1;
    private SQLiteOpenHelper sqLiteOpenHelper;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        initFragmentView(view);
        Log.i("lks","---------------"+state+"----------------");
//        if (state.equals("300")) {
//            Intent intent = new Intent(this.getContext(), ShowOrderActivity.class);
//            intent.putExtra("state", 300);
//            intent.putExtra("context", orderList.get(0).getContext());
//            intent.putExtra("pid", String.valueOf(orderList.get(0).getPid()));
//            startActivity(intent);
//        }
        return view;
    }

    private void initFragmentView(View view) {
        icon = view.findViewById(R.id.order_icon_1);
        order_lv = view.findViewById(R.id.order_lv);

        Glide.with(this)
                .load("https://img1.baidu.com/it/u=2239097720,26447575&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=280")
                .fitCenter()
                .into(icon);

        sqLiteOpenHelper = new OrderDBHelp(view.getContext(), "db.order_table", null, 1);

        if (fist_start) {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            orderList = orderDao.selectAll(db);
            if (db.isOpen()) db.close();
            orderAdapter1 = new OrderAdapter1(view.getContext(), orderList, "100");
            order_lv.setAdapter(orderAdapter1);
            fist_start = false;
        }
        order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView adapter_tv = view.findViewById(R.id.adapter_tv);
                if (state.equals("100")) {
                    click_name = adapter_tv.getText().toString();
                    SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                    if (!click_name.equals("")) {
                        orderList = orderDao.selectByName(db, click_name);
                        orderAdapter1 = new OrderAdapter1(view.getContext(), orderList, "200");
                        order_lv.setAdapter(orderAdapter1);
                        if (db.isOpen()) db.close();
                        state = "200";
                    }
                } else if (state.equals("200")) {
                    Log.i("lks","---------------"+state+"----------------");
                    String click_size = adapter_tv.getText().toString();
                    SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                    if (!click_size.equals("") && !click_name.equals("")) {
                        orderList = orderDao.selectBySize(db, click_size, click_name);
                        orderAdapter1 = new OrderAdapter1(view.getContext(), orderList, "300");
                        order_lv.setAdapter(orderAdapter1);
                        if (db.isOpen()) db.close();
                        state = "300";
                        Log.i("lks","---------------"+orderList+"----------------");
                        Intent intent = new Intent(view.getContext(), ShowOrderActivity.class);
                        intent.putExtra("state", 300);
                        intent.putExtra("context", orderList.get(0).getContext());
                        intent.putExtra("pid", String.valueOf(orderList.get(0).getPid()));
                        startActivity(intent);
                    }
                }
            }
        });
    }

}