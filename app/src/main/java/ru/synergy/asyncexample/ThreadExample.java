package ru.synergy.asyncexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ThreadExample extends AppCompatActivity {

    int mCounter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            TextView mInfoTextView = (TextView) findViewById(R.id.textViewInfo);
            mInfoTextView.setText("Сегодня варон было" + mCounter + " штук");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_example);



    }

    public void  onClick(View v){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 20 * 1000;
                while (System.currentTimeMillis() < endTime){
                    synchronized (this){
                        try{
                            wait(endTime-System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("SPARROWS", ("Сегодня варон было" + mCounter++ + " штук"));

                    handler.sendEmptyMessage(0);
                }

                    // Так писать нельзя
//                TextView mInfoTextView = (TextView) findViewById(R.id.textViewInfo);
//                mInfoTextView.setText("Сегодня варон было" + mCounter++ + " штук");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }
}