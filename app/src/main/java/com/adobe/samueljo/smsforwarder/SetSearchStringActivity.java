package com.adobe.samueljo.smsforwarder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetSearchStringActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_search_string);
        editText = (EditText)findViewById(R.id.editText);
    }

    public void setSearchString(View v){
        SharedPreferences prefs = getSharedPreferences("Contacts", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("searchString",editText.getText().toString());
        editor.commit();
        Toast.makeText(getApplicationContext(),"Updated successfully",Toast.LENGTH_LONG).show();
    }
}
