package com.cqust.exam2.dialog;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.cqust.exam2.R;
import com.cqust.exam2.bean.Contacts;
import com.cqust.exam2.dao.ContactsDao;
import com.cqust.exam2.dataBaice.ContactsDBHelp;

public class ContactsDialog extends Dialog {

    public ContactsDialog(@NonNull Context context) {
        super(context);
    }

    private static final int INVALID_LAYOUT_ID = -1;
    private OnDialogClickListener onClickBottomListener;
    private int contactsDialogLayout = INVALID_LAYOUT_ID;
    private EditText addName_et;
    private EditText addPhoneNumber_et;
    private Button leftBtn;
    private Button rightBtn;
    private ContactsDao contactsDao;
    private  long addResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contactsDialogLayout == INVALID_LAYOUT_ID ?
                R.layout.add_contacts_dialog : contactsDialogLayout);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        // 弹窗外部蒙层不可取消弹窗
        setCanceledOnTouchOutside(false);
        addName_et = findViewById(R.id.addName_et);
        addPhoneNumber_et = findViewById(R.id.addPhoneNumber_et);
        leftBtn = findViewById(R.id.btn_left);
        rightBtn = findViewById(R.id.btn_right);
        contactsDao = new ContactsDao();

        leftBtn.setOnClickListener(v -> {
            if (onClickBottomListener != null) {
                onClickBottomListener.onLeftClick();
            }
        });

        rightBtn.setOnClickListener(v -> {
            if (onClickBottomListener != null) {
                onClickBottomListener.onRightClick();
                SQLiteOpenHelper sqLiteOpenHelper = new ContactsDBHelp(getContext(), "db.contacts_table", null, 1);
                SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
                String addName = addName_et.getText().toString().trim();
                String addPhoneNumber = addPhoneNumber_et.getText().toString().trim();
                if(!addName.equals("") && !addPhoneNumber.equals("")){
                    addResult = contactsDao.addContacts(new Contacts(R.id.contacts_image, addName, addPhoneNumber), db);
                }
                if(addResult>0){
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "添加失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ContactsDialog setOnClickBottomListener(OnDialogClickListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnDialogClickListener {

        void onLeftClick();

        void onRightClick();
    }

    public void initContactsDialogNew(Context context){
        final ContactsDialog dialog = new ContactsDialog(context);
        dialog.setOnClickBottomListener(new OnDialogClickListener() {
            @Override
            public void onLeftClick() {
                dialog.dismiss();
            }

            @Override
            public void onRightClick() {
                dialog.dismiss();
            }
        }).show();
    }
}
