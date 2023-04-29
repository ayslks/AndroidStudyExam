package com.cqust.exam2.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cqust.exam2.R;
import com.cqust.exam2.adapter.ContactsAdapter;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.dao.ContactsDao;
import com.cqust.exam2.dataBaice.ContactsDBHelp;
import com.cqust.exam2.dialog.ContactsDialog;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    ContactsDao contactsDao = new ContactsDao();
    private ListView listView;
    private Button select_bt;
    private Button add_bt;
    private EditText select_et;
    private ContactsAdapter adapter;
    private List<Contacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contacts);
        initActivity();
        SQLiteOpenHelper sqLiteOpenHelper = new ContactsDBHelp(this, "db.contacts_table", null, 1);
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        // 初始化，显示全部
        contactsList = contactsDao.selectAll(db);
        if(db.isOpen()) db.close();
        adapter = new ContactsAdapter(this, contactsList);
        listView.setAdapter(adapter);
        // 查找功能 ByName
        select_bt.setOnClickListener(e->click_selectAll(e, sqLiteOpenHelper));
        // 新增功能
        add_bt.setOnClickListener(e->click_add());
    }

    public void initActivity(){
        listView = findViewById(R.id.lv);
        select_bt = findViewById(R.id.select_bt);
        select_et = findViewById(R.id.select_et);
        add_bt = findViewById(R.id.add_bt);
    }

    public static void hintKeyBoards(View view) {
        InputMethodManager manager = ((InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public void click_selectAll(View view, SQLiteOpenHelper sqLiteOpenHelper){
        hintKeyBoards(view);
        String name = select_et.getText().toString().trim();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        if(name.equals("")){
            contactsList = contactsDao.selectAll(db);
        }else {
            contactsList = contactsDao.selectByName(db, name);
        }
        adapter = new ContactsAdapter(ContactsActivity.this, contactsList);
        listView.setAdapter(adapter);
        Toast.makeText(ContactsActivity.this, "查找成功！", Toast.LENGTH_SHORT).show();
    }

    public void click_add(){
        ContactsDialog contactsDialog =new ContactsDialog(ContactsActivity.this);
        contactsDialog.initContactsDialogNew(ContactsActivity.this);
    }
}