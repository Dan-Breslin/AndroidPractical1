package com.example.androidpractical;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TextMessage extends AppCompatActivity {
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    Button send;

    private boolean checkPermission(String permisssion){
        int permissionCheck = ContextCompat.checkSelfPermission(this,permisssion);
        return (permissionCheck == getPackageManager().PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_message);

        send = (Button) findViewById(R.id.send_btn);
        send.setEnabled(false);

        if (checkPermission(Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case SEND_SMS_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    send.setEnabled(true);
                }
                return;
            }
        }
    }

    public void send(View view){
        String phonenumber = ((EditText)findViewById(R.id.number_txt)).getText().toString();
        String msg = ((EditText) findViewById(R.id.message_txt)).getText().toString();

        if (phonenumber == null || phonenumber.length()==0 || msg==null || msg.length()==0){
            return;
        }
        if (checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, msg, null, null);
            Toast.makeText(this, "Your Message has been Sent", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"No permission",Toast.LENGTH_SHORT).show();
        }
    }
}