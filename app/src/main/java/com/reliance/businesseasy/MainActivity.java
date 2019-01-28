package com.reliance.businesseasy;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RequestToken.OnAsyncRequestComplete {
    Button bt_token;
    EditText et_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestid);
        bt_token = (Button) findViewById(R.id.bt_token_id);
        et_token = (EditText) findViewById(R.id.et_transaction);
        bt_token.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (et_token.getText().toString().length() == 0) {
                                                Toast.makeText(MainActivity.this, "please enter token id", Toast.LENGTH_SHORT).show();
                                            } else {
                                                dosomething();
                                                //request_token();
                                            }
                                        }
                                    }
        );

    }



    private void dosomething() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setComponent(new ComponentName("com.karvyservices.krisp", "com.karvyservices.krisp.MainActivity"));
        //intent.putExtra("key", "Yv%2f46uD3NoLWw%2fw36By07Qr8WY0ebp6jojvLztQct0GVn5bTEexxH88PHDDPDvVj9fu8JCNicLyRCCASdY7VnS6SDL8x3VwkXRZADueoSBM%3d");
        intent.putExtra("key", "YC9rIfbetkutsnPtAFNDzBxREIa8kjDo%2f8EaY662SPXSuNO9f%2fNJDTM8m0upKMpPzxx80wx93FiLIc7H%2fE3mYVk8K%2bS%2b7PqK6mv3WSqIk1M%3d");
        startActivity(intent);
        finish();
    }

    private void request_token() {
        RequestToken requestToken = new RequestToken(MainActivity.this, "RequestToken", et_token.getText().toString(), "Linux@1", this);
        requestToken.execute();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void asyncRespone(String response) {
        System.out.println("result_string" + response);
        try {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class).putExtra("token", response).putExtra("id", et_token.getText().toString());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


