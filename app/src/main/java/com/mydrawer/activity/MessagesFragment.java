package com.mydrawer.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mydrawer.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {

    protected EditText username,password;
    protected Button submit;
    protected String result;


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_messages, container, false);
        username=(EditText)v.findViewById(R.id.email);
        password=(EditText)v.findViewById(R.id.pass);
        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendRequest sendRequest=new SendRequest();
                sendRequest.getcode(String.valueOf(username.getText()),String.valueOf(password.getText()));
                sendRequest.execute();

            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    public class SendRequest extends AsyncTask<Void, Integer, Void> {
        protected String Email,Pass;
        protected int a;

        public void getcode(String a,String b){
            Email=a;
            Pass=b;

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://payioo.com/login.php");
            try{
                List<NameValuePair> nameValuePairList=new ArrayList<NameValuePair>(2);

                nameValuePairList.add(new BasicNameValuePair("user",Email));
                nameValuePairList.add(new BasicNameValuePair("pass",Pass));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

                HttpResponse response =httpClient.execute(httpPost);
                result = EntityUtils.toString(response.getEntity());
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //if(result.contains("true")){
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            //}
        }
    }
}
