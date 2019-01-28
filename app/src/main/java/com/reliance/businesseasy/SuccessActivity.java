package com.reliance.businesseasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {
    TextView tv_er;
    String res = "", res2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);
        tv_er = (TextView) findViewById(R.id.text);
        Intent intent = getIntent();
        if (intent != null) {
            res = intent.getStringExtra("bucketId");
            res2 = intent.getStringExtra("reason");
        }

        if (res == null) {
            tv_er.setText("Reason for failure  :" + res2);
        } else {
            tv_er.setText("Reason for failure  :" + res2 + "\n\n\n" + "Bucket ID:" + res);
        }
    }
}
