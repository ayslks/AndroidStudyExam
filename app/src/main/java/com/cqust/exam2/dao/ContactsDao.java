package com.cqust.exam2.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cqust.exam2.bean.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsDao {

    public final static String TABLE_NAME = "contacts_table";

    public ContactsDao(){}

    public long addContacts(Contacts contacts, SQLiteDatabase db){
        ContentValues values1 = new ContentValues();
        values1.put("image", contacts.getImage());
        values1.put("name", contacts.getName());
        values1.put("phoneNumber", contacts.getPhoneNumber());
        long result=db.insert(TABLE_NAME, null, values1);
        return result;
    }

    public List<Contacts> selectAll(SQLiteDatabase db){
        List<Contacts> list = new ArrayList<>();
        Cursor cursor=db.query(TABLE_NAME, null,null,null,null,null,null);
        while (cursor.moveToNext()){
            int imageID = cursor.getInt(1);
            String name = cursor.getString(2);
            String phoneNumber = cursor.getString(3);
            Contacts contacts = new Contacts(imageID, name, phoneNumber);
            list.add(contacts);
        }
        if(!cursor.isClosed()) cursor.close();
        return list;
    }

    public List<Contacts> selectByName(SQLiteDatabase db,String name){
        List<Contacts> list = new ArrayList<>();
        Cursor cursor=db.query("contacts_table", null,"name = ?", new String[]{name},null,null,null);
        while (cursor.moveToNext()){
            int imageID = cursor.getInt(1);
            String phoneNumber = cursor.getString(3);
            Contacts contacts = new Contacts(imageID, name, phoneNumber);
            list.add(contacts);
        }
        if(!cursor.isClosed()) cursor.close();
        return list;
    }
}
