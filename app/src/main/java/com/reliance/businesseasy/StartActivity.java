package com.reliance.businesseasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by veera.katteboyena on 11/14/2017.
 */

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3 * 1000);
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        background.start();


    }
}
