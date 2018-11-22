package com.fu.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView =findViewById(R.id.listContacts);
        fetchContacts();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fetchContacts();
    }

    // TODO: 取出聯絡人的資料 直接用讀取外部資料這個權限
    private void fetchContacts(){
        ArrayList<String> contacts =new ArrayList<>();
        Uri uri=ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection=null;
        String[] selectionArgs=null;
        String sortOrder=null;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor =contentResolver.query(uri,projection,selection,selectionArgs,sortOrder);

        while (cursor.moveToNext()){
            String name =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i("fetchContacts: ", name+"  "+num);
            contacts.add(name+" "+num);
        }

        mListView.setAdapter(new Adapter(this,contacts));
    }
}
