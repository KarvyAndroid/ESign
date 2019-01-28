package com.reliance.businesseasy;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity implements GenerateDataRequest.OnAsyncRequestComplete {
    Button bt_open_esign;
    Bundle bundle;
    String id, token;
    EditText et_user_id, et_agency_code, et_agency_tid, et_pam, et_pan_name, et_mobile, et_emailid, emp_name, emp_code, emp_desig, emp_location;
    GenerateDataRequest getDataRequest;
    RadioGroup proces_group, ipv_group;
    String str_reg_proces = "1", str_ipv = "Y";
    RadioButton otp, fp, ipv_yes, ipv_no;

    String user_id, agency_code, agency_trd_id, pan_number, pan_name, mobile, email_id, str_emp_name = "", emp_des = "", str_emp_code = "", emp_loca = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            token = bundle.getString("token");
        }

        System.out.println("tokennnnnnnn" + token);
        bt_open_esign = (Button) findViewById(R.id.open_ekyc);
        proces_group = (RadioGroup) findViewById(R.id.process_group);
        otp = (RadioButton) findViewById(R.id.otp);
        fp = (RadioButton) findViewById(R.id.fp);

        et_user_id = (EditText) findViewById(R.id.et_user);
        et_agency_code = (EditText) findViewById(R.id.et_agency_code);
        et_agency_tid = (EditText) findViewById(R.id.et_agency_tid);
        et_pam = (EditText) findViewById(R.id.et_pan);
        et_pan_name = (EditText) findViewById(R.id.et_pan_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);

        ipv_group = (RadioGroup) findViewById(R.id.ipv_groupo);
        ipv_yes = (RadioButton) findViewById(R.id.yes);
        ipv_no = (RadioButton) findViewById(R.id.no);


        et_emailid = (EditText) findViewById(R.id.et_emailid);
        emp_name = (EditText) findViewById(R.id.et_emp_name);
        emp_code = (EditText) findViewById(R.id.et_emp_code);
        emp_desig = (EditText) findViewById(R.id.et_emp_desig);
        emp_location = (EditText) findViewById(R.id.et_emp_location);

        et_agency_tid.setText(id);
        et_agency_tid.setEnabled(false);

        proces_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (otp.isChecked()) {
                str_reg_proces = "1";
            } else if (fp.isChecked()) {
                str_reg_proces = "2";
            }
        });

        ipv_group.setOnCheckedChangeListener((group, checkedId) -> {
            if (ipv_yes.isChecked()) {
                str_ipv = "Y";
            } else {
                str_ipv = "N";
                emp_code.setText("");
                emp_name.setText("");
                emp_desig.setText("");
                emp_location.setText("");
            }
        });

        bt_open_esign.setOnClickListener(v -> {
            if (isValidData()) {
                user_id = et_user_id.getText().toString();
                agency_code = et_agency_code.getText().toString();
                agency_trd_id = et_agency_tid.getText().toString();
                pan_number = et_pam.getText().toString().toUpperCase();
                pan_name = et_pan_name.getText().toString();
                mobile = et_mobile.getText().toString();
                email_id = et_emailid.getText().toString();
                if (str_ipv.equalsIgnoreCase("Y")) {
                    str_emp_name = emp_name.getText().toString();
                    str_emp_code = emp_code.getText().toString();
                    emp_des = emp_desig.getText().toString();
                    emp_loca = emp_location.getText().toString();
                }
                apicall(user_id, agency_code, agency_trd_id, pan_number, pan_name, mobile, email_id, str_emp_name, str_emp_code, emp_des, emp_loca);
            }
        });
    }

    boolean isValidData() {
        boolean valid = true;
        View focusView = null;
        if (TextUtils.isEmpty(et_user_id.getText().toString())) {
            focusView = et_user_id;
            valid = false;
            Toast.makeText(this, "please enter user id", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_agency_code.getText().toString())) {
            focusView = et_agency_code;
            valid = false;
            Toast.makeText(this, "please enter agency code", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_agency_tid.getText().toString())) {
            focusView = et_agency_tid;
            valid = false;
            Toast.makeText(this, "please enter agency transaction id", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_pam.getText().toString())) {
            focusView = et_pam;
            valid = false;
            Toast.makeText(this, "please enter pan number", Toast.LENGTH_SHORT).show();
        } else if (!et_pam.getText().toString().equalsIgnoreCase("PAN_EXEMPT") && !Common.isValidPAN(et_pam.getText().toString())) {
            focusView = et_pam;
            valid = false;
            Toast.makeText(this, "please enter valid pan", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_pan_name.getText().toString())) {
            focusView = et_pan_name;
            valid = false;
            Toast.makeText(this, "please enter pan name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_mobile.getText().toString())) {
            focusView = et_mobile;
            valid = false;
            Toast.makeText(this, "please enter mobile number", Toast.LENGTH_SHORT).show();
        } else if (et_mobile.getText().toString().length() < 10) {
            focusView = et_mobile;
            valid = false;
            Toast.makeText(this, "please enter mobile number 10 digits", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_emailid.getText().toString())) {
            focusView = et_emailid;
            valid = false;
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
        } else if (!Common.emailValidate(et_emailid.getText().toString())) {
            focusView = et_emailid;
            valid = false;
            Toast.makeText(this, "please enter valid email", Toast.LENGTH_SHORT).show();
        } else if (str_ipv.equalsIgnoreCase("Y") && TextUtils.isEmpty(emp_name.getText().toString())) {
            focusView = et_emailid;
            valid = false;
            Toast.makeText(this, "please enter Employee name", Toast.LENGTH_SHORT).show();
        } else if (str_ipv.equalsIgnoreCase("Y") && TextUtils.isEmpty(emp_code.getText().toString())) {
            focusView = emp_code;
            valid = false;
            Toast.makeText(this, "please enter Employee Code", Toast.LENGTH_SHORT).show();
        } else if (str_ipv.equalsIgnoreCase("Y") && TextUtils.isEmpty(emp_desig.getText().toString())) {
            focusView = emp_desig;
            valid = false;
            Toast.makeText(this, "please enter Employee Designation", Toast.LENGTH_SHORT).show();
        } else if (str_ipv.equalsIgnoreCase("Y") && TextUtils.isEmpty(emp_location.getText().toString())) {
            focusView = emp_location;
            valid = false;
            Toast.makeText(this, "please enter Employee Location", Toast.LENGTH_SHORT).show();
        }
        if (!valid) {
            focusView.requestFocus();
        }
        return valid;
    }

    private void apicall(String user_id, String agency_code, String agency_trd_id, String pan_number, String pan_name, String mobile, String email_id, String str_emp_name, String str_emp_code, String emp_des, String emp_loca) {
        getDataRequest = new GenerateDataRequest(MainActivity2.this, "GenerateDataRequest", token, user_id, agency_code, agency_trd_id, pan_number, pan_name, mobile, email_id, str_emp_name, str_emp_code, emp_des, emp_loca, str_reg_proces, str_ipv, this);
        getDataRequest.execute();
    }

    @Override
    public void asyncRespone(String response) {
        System.out.println("response2" + response);
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setComponent(new ComponentName("com.karvyservices.krisp", "com.karvyservices.krisp.MainActivity"));
            intent.putExtra("key", response);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
