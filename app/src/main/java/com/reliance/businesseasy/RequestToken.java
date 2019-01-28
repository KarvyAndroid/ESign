package com.reliance.businesseasy;
import android.content.Context;
import android.os.AsyncTask;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import javax.net.ssl.HttpsURLConnection;

public class RequestToken extends AsyncTask<Object, Object, String> {
    OnAsyncRequestComplete caller;
    Context context;
    String methodname, userid, password, result_string;
    SoapPrimitive resultString;
    String NAMESPACE = "http://tempuri.org/";
    String URL1 = "https://ekyc.karvykra.com/partners/Clients-ver2_5.asmx?";
    HttpTransportSE transport;
    HttpsURLConnection httpsConnection = null;

    public RequestToken(Context a, String method, String userid, String passsword, OnAsyncRequestComplete caller) {
        this.caller = caller;
        this.context = a;
        this.methodname = method;
        this.userid = userid;
        this.password = passsword;
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
            Request.addProperty("TransactionID", userid);
            Request.addProperty("PassKey", password);
            transport = new HttpTransportSE(URL1);
            transport.call("http://tempuri.org/" + methodname, soapEnvelope);
            resultString = ((SoapPrimitive) soapEnvelope.getResponse());
            result_string = resultString.toString();
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