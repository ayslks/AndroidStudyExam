package com.cqust.exam2.dataBaice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.bean.Order;
import com.cqust.exam2.dao.ContactsDao;
import com.cqust.exam2.dao.OrderDao;

public class OrderDBHelp extends SQLiteOpenHelper {

    private OrderDao orderDao = new OrderDao();

    public OrderDBHelp(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists order_table");
        sqLiteDatabase.execSQL("create table if not exists order_table(" +
                "oid integer primary key autoincrement," +
                "name varchar(20)," +
                "size varchar(20)," +
                "context varchar(100)," +
                "pid integer)");

        orderDao.addOrder(new Order("拿铁", "Large cup", "拿铁-大杯", 1), sqLiteDatabase);
        orderDao.addOrder(new Order("拿铁", "Medium cup", "拿铁-中杯",1), sqLiteDatabase);
        orderDao.addOrder(new Order("美式", "Large cup", "美式-大杯",2), sqLiteDatabase);
        orderDao.addOrder(new Order("美式", "Medium cup", "美式-中杯",2), sqLiteDatabase);
        orderDao.addOrder(new Order("意式浓缩", "Small cup", "意式浓缩-小杯",3), sqLiteDatabase);
        orderDao.addOrder(new Order("卡布奇诺", "Large cup", "卡布奇诺-大杯",4), sqLiteDatabase);
        orderDao.addOrder(new Order("魔法棒面包", "草莓味", "草莓魔法棒",5), sqLiteDatabase);
        orderDao.addOrder(new Order("魔法棒面包", "巧克力味", "巧克力魔法棒",6), sqLiteDatabase);
        orderDao.addOrder(new Order("抹茶蛋糕", "抹茶蛋糕", "抹茶蛋糕",7), sqLiteDatabase);
        orderDao.addOrder(new Order("彩虹蛋糕", "彩虹蛋糕", "彩虹蛋糕",8), sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
