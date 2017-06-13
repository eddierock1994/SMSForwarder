 package com.adobe.samueljo.smsforwarder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 /**
 * Created by mgigirey on 08/12/2014.
 */
public class SmsListener extends BroadcastReceiver {

    SQLiteDatabase mydatabase;

    @Override
    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);

                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    mydatabase = context.openOrCreateDatabase("Database",Context.MODE_PRIVATE,null);
                    SmsManager smsManager = SmsManager.getDefault();
                    PendingIntent sentPI;
                    String SENT = "SMS_SENT";

                    SharedPreferences prefs = context.getSharedPreferences("Contacts", Context.MODE_PRIVATE);
                    if(message.contains(prefs.getString("searchString","www.starindiaresearch.com"))) {
                        List<String> myList = new ArrayList<String>(Arrays.asList(prefs.getString("contacts",null).split(",")));
                        for( int i=0; i<myList.size(); i++){
                            sentPI = PendingIntent.getBroadcast(context, 0,new Intent(SENT), 0);
                            smsManager.sendTextMessage(myList.get(i), null, message, sentPI, null);
                        }
                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
