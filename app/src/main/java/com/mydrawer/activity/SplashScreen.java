package com.mydrawer.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mydrawer.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class SplashScreen extends ActionBarActivity {

    DonutProgress smile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        MyCount counter = new MyCount(3000,40);
        counter.start();
        smile= (DonutProgress) findViewById(R.id.donut_progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub

            Intent i=new Intent(getApplicationContext(),PayIoo.class);
            startActivity(i);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            if(smile.getProgress()!=50){
                smile.setProgress(smile.getProgress()+1);
            }
            //textView1.setText((int) (millisUntilFinished/1000));

        }
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
