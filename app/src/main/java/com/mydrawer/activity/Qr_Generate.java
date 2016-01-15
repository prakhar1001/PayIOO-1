package com.mydrawer.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mydrawer.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Qr_Generate extends ActionBarActivity {

    protected ImageView imageView;
    public String code,encryptedMsg;
    protected TextView amount;
    protected String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__generate);
        imageView=(ImageView)findViewById(R.id.imageView);
        amount=(TextView)findViewById(R.id.amount);
        code=(String)getIntent().getCharSequenceExtra("String");
        url="http://www.payioo.com/testpage.php?SendID=encryptedCode";
        try {
            encryptedMsg = AESCrypt.encrypt("OOIyaPppawS", code);
        }catch (GeneralSecurityException e){
            //handle error
        }
        url=url.replace("encryptedCode",code);
        try {
            //generateQRCode_general(encryptedMsg,imageView);
            generateQRCode_general(code,imageView);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        amount.setText("Rs:"+code);

        SendRequest sendRequest=new SendRequest();
        sendRequest.getcode(code);
        sendRequest.execute();

        //Toast.makeText(getApplicationContext(), "Amount sent: " + encryptedMsg, Toast.LENGTH_SHORT).show();

    }
   public class SendRequest extends AsyncTask<Void, Integer, Void> {
       protected String code1;

       public void getcode(String a){
           code1=a;
       }

       @Override
       protected Void doInBackground(Void... params) {
           try{
               HttpClient httpClient=new DefaultHttpClient();
               String url="http://www.payioo.com/testpage.php?SendID=EncryptedCode";
               url=url.replace("EncryptedCode",code1);
               HttpPost httpPost=new HttpPost(url);

               HttpResponse response =
               httpClient.execute(httpPost);

           }catch (Exception e){
               e.printStackTrace();
           }
           return null;
       }
   }
    protected void generateQRCode_general(String data, ImageView qrcode)throws WriterException {
        com.google.zxing.Writer writer = new QRCodeWriter();
        String finaldata = Uri.encode(data, "utf-8");

        BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE,400, 400);

        Bitmap ImageBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < 400; i++) {//width
            for (int j = 0; j < 400; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK: Color.WHITE);
            }
        }

        if (ImageBitmap != null) {
            qrcode.setImageBitmap(ImageBitmap);
        } else {
            /*Toast.makeText(this, getResources().getString(bla),
                    Toast.LENGTH_SHORT).show();*/
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qr__generate, menu);
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
