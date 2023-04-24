package com.cqust.exam2.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.dao.ContactsDao;
import com.cqust.exam2.dataBaice.ContactsDBHelp;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private final Context context;
    private final List<Contacts> contacts;
    private final String TABLE_NAME = "contacts_table";

    public ContactsAdapter(Context context, List<Contacts> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Contacts contacts1 = (Contacts) getItem(i);
        view = View.inflate(context, R.layout.adapter_view, null);
        SQLiteOpenHelper sqLiteOpenHelper = new ContactsDBHelp(context, "db.contacts_table", null, 1);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        ImageView contacts_image = view.findViewById(R.id.contacts_image);
        TextView contacts_name = view.findViewById(R.id.contacts_name);
        TextView contacts_phoneNumber = view.findViewById(R.id.contacts_phoneNumber);
        Button delete_bt = view.findViewById(R.id.delete_bt);
        Button update_bt = view.findViewById(R.id.update_bt);
        delete_bt.setOnClickListener(e->delete(db, contacts1.getName()));
        update_bt.setOnClickListener(e->update(db, contacts1));
        contacts_image.setImageResource(contacts1.getImage());
        contacts_name.setText(contacts1.getName());
        contacts_phoneNumber.setText(contacts1.getPhoneNumber());
        return view;
    }

    public void delete(SQLiteDatabase db,String name){
        db.delete(TABLE_NAME,"name = ?", new String[]{name});
    }

    public void update(SQLiteDatabase db, Contacts contacts){

    }
}
