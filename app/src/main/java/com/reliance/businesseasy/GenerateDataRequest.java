package com.reliance.businesseasy;

import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class GenerateDataRequest extends AsyncTask<Object, Object, String> {

    OnAsyncRequestComplete caller;
    Context context;
    String methodname, ipv, userid, tokenn, agency_tra, agency_code, pan, type, mobile, pan_name, emailid, emp_name, emp_design, emp_code, emp_location, result_string;
    SoapPrimitive resultString;
    String NAMESPACE = "http://tempuri.org/";
    String URL1 = "https://ekyc.karvykra.com/partners/Clients-ver2_5.asmx?";
    HttpTransportSE transport;

    public GenerateDataRequest(Context a, String method, String token, String userid, String agency_code, String agency_tra, String pan, String pan_name, String mobile, String emailid, String emp_name, String emp_code, String emp_desig, String emp_location, String type, String ipv, OnAsyncRequestComplete caller) {
        this.caller = caller;
        this.context = a;
        this.methodname = method;
        this.userid = userid;
        this.agency_code = agency_code;
        this.agency_tra = agency_tra;
        this.pan = pan;
        this.pan_name = pan_name;
        this.mobile = mobile;
        this.emailid = emailid;
        this.emp_code = emp_code;
        this.emp_name = emp_name;
        this.emp_design = emp_desig;
        this.emp_location = emp_location;
        this.tokenn = token;
        this.type = type;
        this.ipv = ipv;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public interface OnAsyncRequestComplete {
        void asyncRespone(String response);
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            SoapObject Request = new SoapObject(NAMESPACE, methodname);
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            Request.addProperty("UserID", userid);
            Request.addProperty("AgencyCode", agency_code);
            Request.addProperty("AgencyTransactionID", agency_tra);
            //Request.addProperty("Aadhar", "650686998958");
            Request.addProperty("Pan", pan);
            Request.addProperty("PanName", pan_name);
            Request.addProperty("Mobile", mobile);
            Request.addProperty("EmailID", emailid);
            Request.addProperty("AuthType", type);
            Request.addProperty("EmpName", emp_name);
            Request.addProperty("EmpDesig", emp_design);
            Request.addProperty("EmpCode", emp_code);
            Request.addProperty("EmpLoc", emp_location);
            Request.addProperty("Token", tokenn);
            Request.addProperty("packageName", "com.reliance.businesseasy");
            Request.addProperty("activityName", "com.reliance.businesseasy.SuccessActivity");
            Request.addProperty("failureActivity", "com.reliance.businesseasy.SuccessActivity");
            Request.addProperty("ipv", ipv);

            System.out.println("soap_request" + Request.toString());
            transport = new HttpTransportSE(URL1);
            transport.call("http://tempuri.org/GenerateDataRequest", soapEnvelope);
            resultString = ((SoapPrimitive) soapEnvelope.getResponse());
            result_string = resultString.toString();
            System.out.println("response1" + result_string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result_string;
    }

    @Override
    protected void onPostExecute(String result_string) {
        super.onPreExecute();
        caller.asyncRespone(result_string);
    }
}