package com.cqust.exam2.dataBaice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.dao.ContactsDao;

public class ContactsDBHelp extends SQLiteOpenHelper {

    private ContactsDao contactsDao = new ContactsDao();

    public ContactsDBHelp(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists contacts_table");
        sqLiteDatabase.execSQL("create table if not exists contacts_table(" +
                "id integer primary key autoincrement," +
                "image integer," +
                "name varchar(20)," +
                "phoneNumber varchar(12))");

        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "lks", "20204404930"), sqLiteDatabase);
        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "666", "20204404931"), sqLiteDatabase);
        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "NB", "20204404932"), sqLiteDatabase);
        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "test1", "20204404933"), sqLiteDatabase);
        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "test2", "20204404934"), sqLiteDatabase);
        contactsDao.addContacts(new Contacts(R.drawable.contacts_image, "test3", "20204404935"), sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
