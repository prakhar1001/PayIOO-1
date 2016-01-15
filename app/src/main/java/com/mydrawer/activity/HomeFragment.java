package com.mydrawer.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mydrawer.R;

import java.security.GeneralSecurityException;


public class HomeFragment extends Fragment {
    protected ImageView Send,Receive;

    protected String contents,format,messageAfterDecrypt,correctMessage;
    protected EditText InputValue;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Send=(ImageView)rootView.findViewById(R.id.sbutton);
        Receive=(ImageView)rootView.findViewById(R.id.rbutton);
        Send.setImageResource(R.drawable.send);
        Receive.setImageResource(R.drawable.receive);

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),Send_Amount.class);
                startActivity(intent);
                /*Send.setVisibility(View.GONE);
                Receive.setVisibility(View.GONE);
                InputValue.setVisibility(View.VISIBLE);
                InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(InputValue,InputMethodManager.SHOW_IMPLICIT);*/
            }
        });

        Receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent= new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE","QR_CODE_MODE");
                startActivityForResult(intent,0);*/
                Intent intent = new Intent(getActivity(),CaptureActivity.class);
                intent.setAction("com.google.zxing.client.android.SCAN");
                intent.putExtra("SAVE_HISTORY", false);
                startActivityForResult(intent, 0);
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==0){
            if(resultCode==Activity.RESULT_OK){
                contents=intent.getStringExtra("SCAN_RESULT");
                format=intent.getStringExtra("SCAN_RESULT_FORMAT");
                correctMessage=contents.replaceAll("%3D","=");

                try {
                    messageAfterDecrypt = AESCrypt.decrypt("OOIyaPppawS", correctMessage);
                }catch (GeneralSecurityException e){
                    //handle error - could be due to incorrect password or tampered encryptedMsg
                }
                Toast.makeText(getActivity(), "Amount received: "+messageAfterDecrypt, Toast.LENGTH_SHORT).show();

            }else if(resultCode==Activity.RESULT_CANCELED){

            }
        }
    }
    /*public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, intent);

        if (scanResult != null) {

            contents=intent.getStringExtra("SCAN_RESULT");
            format=intent.getStringExtra("SCAN_RESULT_FORMAT");
            Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();

        }
        *//*Toast.makeText(getActivity(),"Done"+scanResult.toString(),Toast.LENGTH_LONG).show();*//*
    }*/
    protected void generateQRCode_general(String data, ImageView qrcode)throws WriterException {
        com.google.zxing.Writer writer = new QRCodeWriter();
        String finaldata = Uri.encode(data, "utf-8");

        BitMatrix bm = writer.encode(finaldata, BarcodeFormat.QR_CODE,400, 400);

        Bitmap ImageBitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < 400; i++) {//width
            for (int j = 0; j < 400; j++) {//height
                ImageBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLUE: Color.WHITE);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
