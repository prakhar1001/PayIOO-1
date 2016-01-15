package com.mydrawer.activity;

import com.dd.processbutton.ProcessButton;

import android.os.Handler;

import java.util.Random;

public class ProgressGenerator {

    public interface OnCompleteListener {

        public void onComplete();
    }
    public interface onPostExecuteListener{
        public void onPostExecute();
    }

    private OnCompleteListener mListener;

    protected int mProgress;

    public ProgressGenerator(OnCompleteListener listener) {
        mListener = listener;
    }

    public void start(final ProcessButton button) {
        final Handler handler = new Handler();
        mProgress=10;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //mProgress += 10;
                //mProgress = 10;
                button.setProgress(mProgress);
                if (mProgress < 100) {
                    handler.postDelayed(this, generateDelay());
                } else {
                    mListener.onComplete();
                    //pListner.onPostExecute();
                }
            }
        }, generateDelay());
    }

    private Random random = new Random();

    private int generateDelay() {
        return random.nextInt(1000);
    }
}
