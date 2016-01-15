package com.mydrawer.functionalClasses;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swappnil on 15-01-2016.
 */
public class Request {
    Context context;
    String Consumer_id,Status;

    public Request(Context cxt){
        context=cxt;
    }

    public void Register(String name,String email, final String mobile){
        String url = "http://180.179.146.81/wallet/v1/customers/create";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobileNo", mobile);
            jsonObject.put("email",email);
            jsonObject.put("name_of_customer",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                // do something...
                String Consumer_id=null;
                try {
                    Consumer_id=response.getString("consumer_id");
                    String status;
                    status=sendOTP(Consumer_id);
                } catch (JSONException f) {
                    f.printStackTrace();
                }
                
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                VolleyLog.d("***Volley***", error.getMessage());
                String id=null;
                id=getCustomerID(mobile);

                //Log.d("*****Volley Error*****", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                String credentials= Base64.encodeToString(("381492581182fc6fbbf6213078009730" + ":" + "29bc700b1e14956da9eeaf8a4e62f691").getBytes(), Base64.NO_WRAP);
                header.put("Authorization","Basic "+ credentials );
                //header.put("Content-Type", "application/json");
                //Log.d("*****Header ka His*****", header.toString());
                return header;

            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    public String getCustomerID(String number){

        String url = "http://180.179.146.81/wallet/v1/customers/getConsumerID";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile_no", number);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                // do something...

                try {
                    Consumer_id =response.getString("consumer_id");
                    Log.d("***Consumer_id***",Consumer_id);
                } catch (JSONException f) {
                    f.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                VolleyLog.d("***Volley***", error.getMessage());
                //Log.d("*****Volley Error*****", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                String credentials= Base64.encodeToString(("381492581182fc6fbbf6213078009730" + ":" + "29bc700b1e14956da9eeaf8a4e62f691").getBytes(), Base64.NO_WRAP);
                header.put("Authorization","Basic "+ credentials );
                /*header.put("Content-Type", "application/json");
                Log.d("*****Header ka His*****", header.toString());*/
                return header;

            }
        };
        requestQueue.add(jsonObjectRequest);
        return Consumer_id;
    }

    public String sendOTP(String consumer_id){

        String url = "http://180.179.146.81/wallet/v1/customers/generateotp";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("consumer_id", consumer_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response", response.toString());
                // do something...

                try {
                    Status =response.getString("status");
                    Log.d("***Consumer_id***",Status);
                } catch (JSONException f) {
                    f.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                VolleyLog.d("***Volley***", error.getMessage());
                //Log.d("*****Volley Error*****", error.toString());
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                String credentials= Base64.encodeToString(("381492581182fc6fbbf6213078009730" + ":" + "29bc700b1e14956da9eeaf8a4e62f691").getBytes(), Base64.NO_WRAP);
                header.put("Authorization","Basic "+ credentials );
                /*header.put("Content-Type", "application/json");
                Log.d("*****Header ka His*****", header.toString());*/
                return header;

            }
        };
        requestQueue.add(jsonObjectRequest);
        return Consumer_id;
    }

}
