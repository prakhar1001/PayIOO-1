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


public class FriendsFragment extends Fragment {

    protected EditText name,email,pass,mobile;
    protected Button submit;
    public String Name,Email,Pass,Mobile;


    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_friends, container, false);
        name=(EditText)v.findViewById(R.id.name);
        email=(EditText)v.findViewById(R.id.email);
        pass=(EditText)v.findViewById(R.id.pass);
        mobile=(EditText)v.findViewById(R.id.mobile);
        submit=(Button)v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                Name=name.getText().toString();
                Email=email.getText().toString();
                Pass=pass.getText().toString();
                Mobile=mobile.getText().toString();
                /*HttpClient httpClient=new DefaultHttpClient();
                ///String url="http://www.payioo.com/testpage.php?SendID=EncryptedCode";
                //url=url.replace("EncryptedCode",code1);
                HttpPost httpPost=new HttpPost("http://payioo.com/registration.php");
                try{
                    List<NameValuePair> nameValuePairList=new ArrayList<NameValuePair>(4);
                    nameValuePairList.add(new BasicNameValuePair("Name",Name));
                    nameValuePairList.add(new BasicNameValuePair("Email",Email));
                    nameValuePairList.add(new BasicNameValuePair("Pass",Pass));
                    nameValuePairList.add(new BasicNameValuePair("Mobile",Mobile));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList));

                    HttpResponse response =
                            httpClient.execute(httpPost);
                    Toast.makeText(getActivity(),"Done"+Name+Email+Pass+Mobile,Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    e.printStackTrace();
                }*/

                SendRequest sendRequest=new SendRequest();
                sendRequest.getcode(Name,Email,Pass,Mobile);
                sendRequest.execute();
            }
        });

        return v;
    }
    public class SendRequest extends AsyncTask<Void, Integer, Void> {
        protected String Namee,Emaill,Passs,Mobilee,result;
        protected int a;

        public void getcode(String a,String b,String c,String d){
            Namee=a;
            Emaill=b;
            Passs=c;
            Mobilee=d;
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost("http://payioo.com/registration.php");
            try{
                List<NameValuePair> nameValuePairList=new ArrayList<NameValuePair>(4);
                nameValuePairList.add(new BasicNameValuePair("Name",Namee));
                nameValuePairList.add(new BasicNameValuePair("Email",Emaill));
                nameValuePairList.add(new BasicNameValuePair("Pass",Passs));
                nameValuePairList.add(new BasicNameValuePair("Mobile",Mobilee));
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
                Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
            //}
        }
    }


}

