package com.adobe.samueljo.smsforwarder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textView, searchText;
    String CONTACTS = "Contacts";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences(CONTACTS, Context.MODE_PRIVATE);
        searchText.setText(prefs.getString("searchString", null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        searchText = (TextView)findViewById(R.id.search);
        SharedPreferences prefs = getSharedPreferences(CONTACTS, Context.MODE_PRIVATE);
        textView.setText(prefs.getString("contacts", "").replace(",","\n"));
        searchText.setText(prefs.getString("searchString", ""));
    }

    public void addToDatabase(View v){
        SharedPreferences prefs = getSharedPreferences(CONTACTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String temp = prefs.getString("contacts", null);
        Set<String> set;
        set = new HashSet<String>();
        if(temp != null)
            set = new HashSet<String>(Arrays.asList(temp.split(",")));
        else
            temp = "";
        if(temp.length() == 0){
            temp += editText.getText().toString();
        }
        else{
            Boolean isDuplicate = false;
            if(set.contains(editText.getText().toString())){
                isDuplicate = true;
            }
            if(!isDuplicate)
                temp += ","+ editText.getText().toString();
        }
        Log.i("Edwin",temp);
        editor.putString("contacts",temp);
        editor.commit();
        Toast.makeText(getApplicationContext(),editText.getText().toString()+" added successfully",Toast.LENGTH_LONG).show();
        textView.setText(prefs.getString("contacts", null).replace(",","\n"));
    }

    public void clearDatabase(View v){
        SharedPreferences prefs = getSharedPreferences(CONTACTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("contacts","");
        editor.commit();
        Toast.makeText(getApplicationContext(),"All contacts deleted",Toast.LENGTH_LONG).show();
        textView.setText(prefs.getString("contacts", null).replace(",","\n"));
    }

    public void setSearchString(View v){
        Intent intent = new Intent(this, SetSearchStringActivity.class);
        startActivity(intent);
    }
}
