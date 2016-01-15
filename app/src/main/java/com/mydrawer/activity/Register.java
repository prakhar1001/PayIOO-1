package com.mydrawer.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dd.processbutton.iml.ActionProcessButton;
import com.mydrawer.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register extends ActionBarActivity implements ProgressGenerator.OnCompleteListener{
    EditText name,email,mobile;
    ActionProcessButton submit;
    final ProgressGenerator progressGenerator = new ProgressGenerator(this);
    protected com.mydrawer.functionalClasses.Request request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        mobile=(EditText)findViewById(R.id.mobile);
        submit=(ActionProcessButton)findViewById(R.id.btnRegister);
        submit.setMode(ActionProcessButton.Mode.ENDLESS);
        request=new com.mydrawer.functionalClasses.Request(getApplicationContext());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    if(!((String.valueOf(name.getText()).equals(""))||(String.valueOf(email.getText()).equals(""))||(String.valueOf(mobile.getText()).equals("")))) {
                        /*progressGenerator.start(submit);
                        */

                        /*Register1(name.getText().toString(), email.getText().toString(), mobile.getText().toString());*/
                        request.Register(name.getText().toString(), email.getText().toString(), mobile.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "Name, Email and Mobile cannot be empty", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    void Register1(String name,String email,String mobile){
        String url = "http://180.179.146.81/wallet/v1/customers/create";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("mobileNo", mobile);
            jsonObject.put("email",email);
            jsonObject.put("name_of_customer",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                // do something...
                String Consumer_id=null;
                try {
                    Consumer_id=response.getString("consumer_id");
                } catch (JSONException f) {
                    f.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                VolleyLog.d("***Volley***",error.getMessage());
                //Log.d("*****Volley Error*****", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                String credentials=Base64.encodeToString(("381492581182fc6fbbf6213078009730" + ":" + "29bc700b1e14956da9eeaf8a4e62f691").getBytes(), Base64.NO_WRAP);
                header.put("Authorization","Basic "+ credentials );
                header.put("Content-Type", "application/json");
                Log.d("*****Header ka His*****", header.toString());
                return header;

            }
        };
        requestQueue.add(jsonObjectRequest);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete() {

    }
}
