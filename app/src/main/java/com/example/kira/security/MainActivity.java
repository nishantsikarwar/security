package com.example.kira.security;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public static Button scan_button,button_fetch;
    public static TextView fetched_data,qr_value,users_media;
    public static ImageView profile_image;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan_button = (Button)findViewById(R.id.scan);
        fetched_data =(TextView)findViewById(R.id.fetched_data);
        profile_image = (ImageView)findViewById(R.id.profile_image);
        qr_value = (TextView)findViewById(R.id.scan_text);
        button_fetch = (Button)findViewById(R.id.button_fetch);
        users_media = (TextView)findViewById(R.id.users);
      //  context = this;

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRScanner();
            }
        });

        button_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonData jsonData = new JsonData();
                jsonData.execute();
            }
        });
    }

    private void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() != null) {
                qr_value.setText(result.getContents());
            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }

        }
    }
}
