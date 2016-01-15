package com.mydrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mydrawer.R;
import com.dd.processbutton.iml.ActionProcessButton;

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


public class PayIoo extends ActionBarActivity  implements ProgressGenerator.OnCompleteListener {

    protected EditText number,password,name,email;
    protected TextView numberHint,nameHint,emailHint,passHint;
    final ProgressGenerator progressGenerator = new ProgressGenerator(this);
    Button signup;
    TextView register;

    protected String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ioo);
        number=(EditText)findViewById(R.id.number);
        password=(EditText)findViewById(R.id.pass);
        name=(EditText)findViewById(R.id.name);
        name.setVisibility(View.GONE);
        email=(EditText)findViewById(R.id.email);
        email.setVisibility(View.GONE);
        numberHint=(TextView)findViewById(R.id.numberHint);
        numberHint.setVisibility(View.GONE);
        nameHint=(TextView)findViewById(R.id.nameHint);
        nameHint.setVisibility(View.GONE);
        emailHint=(TextView)findViewById(R.id.emailHint);
        emailHint.setVisibility(View.GONE);
        passHint=(TextView)findViewById(R.id.passwordHint);
        passHint.setVisibility(View.GONE);
        signup=(Button)findViewById(R.id.reg);
        register=(TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(number.getText().toString().length()>0&&numberHint.getVisibility()==View.GONE){
                    numberHint.setText(number.getHint());
                    numberHint.setVisibility(View.VISIBLE);
                    hint_slide_up(getApplicationContext(), numberHint);
                }
                else if(number.getText().toString().length()==0&&numberHint.getVisibility()==View.VISIBLE){
                    slide_down(getApplicationContext(), numberHint);
                    numberHint.setText("");
                    numberHint.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(name.getText().toString().length()>0&&nameHint.getVisibility()==View.GONE){
                    nameHint.setText(name.getHint());
                    nameHint.setVisibility(View.VISIBLE);
                    hint_slide_up(getApplicationContext(), nameHint);
                }
                else if(name.getText().toString().length()==0&&nameHint.getVisibility()==View.VISIBLE){
                    slide_down(getApplicationContext(), nameHint);
                    nameHint.setText("");
                    nameHint.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //final ActionProcessButton submit=(ActionProcessButton)findViewById(R.id.btnSignIn);

        /*submit.setMode(ActionProcessButton.Mode.ENDLESS);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    if(!((String.valueOf(username.getText()).equals(""))||(String.valueOf(password.getText()).equals("")))) {
                        progressGenerator.start(submit);
                        SendRequest sendRequest = new SendRequest();
                        sendRequest.getcode(String.valueOf(username.getText()), String.valueOf(password.getText()));
                        sendRequest.execute();
                    }else{
                        Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_LONG).show();

                }
            }
        });*/

    }
    public void toggle_contents(View v){
        /*name.setVisibility( name.isShown()
                ? View.GONE
                : View.VISIBLE );
        email.setVisibility( email.isShown()
                ? View.GONE
                : View.VISIBLE );*/
        if(!(name.isShown())){
            name.setVisibility(View.VISIBLE);
            slide_down(getApplicationContext(), name);
            if(name.getText().toString().length()>0) {
                nameHint.setText(name.getHint());
                nameHint.setVisibility(View.VISIBLE);
                hint_slide_up(getApplicationContext(), nameHint);
            }

        }else{
            slide_up(getApplicationContext(),name);
            name.setVisibility(View.GONE);
            slide_down(getApplicationContext(), nameHint);
            nameHint.setText("");
            nameHint.setVisibility(View.GONE);
        }
        if(!(email.isShown())){
            email.setVisibility(View.VISIBLE);
            slide_down(getApplicationContext(), email);
            if(email.getText().toString().length()>0) {
                emailHint.setText(email.getHint());
                emailHint.setVisibility(View.VISIBLE);
                hint_slide_up(getApplicationContext(), emailHint);
            }

        }else{
            slide_up(getApplicationContext(), email);
            email.setVisibility(View.GONE);
            slide_down(getApplicationContext(), emailHint);
            emailHint.setText("");
            emailHint.setVisibility(View.GONE);
        }
    }
    public static void slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void slide_up(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void hint_slide_up(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.hint_slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    public static void hint_slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.hint_slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    @Override
    public void onComplete() {
        /*Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();*/
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
            if(result.contains("true")){
            //Toast.makeText(getApplicationContext(), "Post Execute "+result, Toast.LENGTH_LONG).show();
            progressGenerator.mProgress=100;
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_ioo, menu);
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

}
