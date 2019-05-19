package com.jdsv650.broadcastreceivertest;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SMSreceiver smsReceiver;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reg = (Button) findViewById(R.id.registerBroadcast);
        Button unreg = (Button) findViewById(R.id.unregisterBroadcast);

        reg.setOnClickListener(this);
        unreg.setOnClickListener(this);

        // get user perm to receive sms - required
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            Log.d("TAG", "My permission request sms received successfully");
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.registerBroadcast)
        {
            // OK this called onReceive in my Broadcast Receiver
            // don't need to get perm for this one...
            // intentFilter.addAction(Intent.ACTION_BATTERY_LOW);

            smsReceiver = new SMSreceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            intentFilter.addDataScheme("sms");
            registerReceiver(smsReceiver, intentFilter);

            Toast.makeText(this, "Register Receiver Called", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.unregisterBroadcast)
        {
            Toast.makeText(this, "Unregister Tapped", Toast.LENGTH_SHORT).show();

            if (smsReceiver != null) {
                unregisterReceiver(smsReceiver);
                smsReceiver = null;
                Toast.makeText(this, "Unregister Receiver Called", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
