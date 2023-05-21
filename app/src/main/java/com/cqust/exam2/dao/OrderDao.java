package com.cqust.exam2.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.bean.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public final static String TABLE_NAME = "order_table";

    public OrderDao(){}

    public List<Order> selectAll(SQLiteDatabase db){
        List<Order> list = new ArrayList<>();
        Cursor cursor=db.query(TABLE_NAME, new String[]{"oid","name","size","context","pid"},null,null,"name",null,null);
        while (cursor.moveToNext()){
            int oid = cursor.getInt(0);
            String name = cursor.getString(1);
            String size = cursor.getString(2);
            String context = cursor.getString(3);
            int pid = cursor.getInt(4);
            Order order = new Order(oid, name, size, context, pid);
            list.add(order);
        }
        if(!cursor.isClosed()) cursor.close();
        return list;
    }

    public List<Order> selectByName(SQLiteDatabase db,String name){
        List<Order> list = new ArrayList<>();
        Cursor cursor=db.query(TABLE_NAME, null,"name = ?", new String[]{name},null,null,null);
        while (cursor.moveToNext()){
            int oid = cursor.getInt(0);
            String size = cursor.getString(2);
            String context = cursor.getString(3);
            int pid = cursor.getInt(4);
            Order order = new Order(oid, name, size, context, pid);
            list.add(order);
        }
        if(!cursor.isClosed()) cursor.close();
        return list;
    }

    public List<Order> selectBySize(SQLiteDatabase db,String size,String name){
        List<Order> list = new ArrayList<>();
        Cursor cursor=db.query(TABLE_NAME, null,"size = ? and name = ?", new String[]{size, name},null,null,null);
        while (cursor.moveToNext()){
            int oid = cursor.getInt(0);
            String context = cursor.getString(3);
            int pid = cursor.getInt(4);
            Order order = new Order(oid, name, size, context, pid);
            list.add(order);
        }
        if(!cursor.isClosed()) cursor.close();
        return list;
    }

    public long addOrder(Order order, SQLiteDatabase db){
        ContentValues values1 = new ContentValues();
        values1.put("size", order.getSize());
        values1.put("name", order.getName());
        values1.put("context", order.getContext());
        values1.put("pid", order.getPid());
        long result=db.insert(TABLE_NAME, null, values1);
        return result;
    }

}
