package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView blackTV, whiteTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whiteTV = findViewById(R.id.whiteTV);
        blackTV = findViewById(R.id.blackTV);


        new CountDownTimer(3605000, 1000) {

            public void onTick(long millisUntilFinished) {
                whiteTV.setText(getTime((int) (millisUntilFinished/1000)));
                blackTV.setText(getTime((int) (millisUntilFinished/1000)));
            }
            public void onFinish() {
            }
        }.start();
    }
    String getTime(int timeSeconds){
        String time;
        int h = timeSeconds/3600;
        String hours = String.valueOf(h);
        if(h<10) hours = "0"+h;
        int m = (timeSeconds-(h*3600))/60;
        String minutes = String.valueOf(m);
        if(m<10) minutes = "0"+m;
        int s = timeSeconds%60;
        String seconds = String.valueOf(s);
        if(s<10) seconds = "0"+s;
        time = hours+":"+minutes+":"+seconds;
        return time;
    }





}
