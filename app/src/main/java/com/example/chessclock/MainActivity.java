package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout whiteLL, blackLL;
    TextView whiteTV, blackTV;
    CountDownTimer whiteTimer, blackTimer;
    long whiteTimeLeft, blackTimeLeft;
    boolean whitePaused, blackPaused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        setTimers();
    }
    private void findViewsByIds(){
        whiteLL = findViewById(R.id.whiteLL);
        whiteLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleWhiteTimer();
            }
        });
        blackLL = findViewById(R.id.blackLL);
        blackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleBlackTimer();
            }
        });
        whiteTV = findViewById(R.id.whiteTV);
        blackTV = findViewById(R.id.blackTV);
    }
    private void setTimers(){
        whiteTimer = new CountDownTimer(3605000, 1000) {
            public void onTick(long millisUntilFinished) {
                whiteTimeLeft =  millisUntilFinished;
                whiteTV.setText(getTime((int) (millisUntilFinished/1000)));
            }
            public void onFinish() {
            }
        };
        blackTimer = new CountDownTimer(3605000, 1000) {
            public void onTick(long millisUntilFinished) {
                blackTimeLeft = millisUntilFinished;
                blackTV.setText(getTime((int) (millisUntilFinished/1000)));
            }
            public void onFinish() {
            }
        };
        whiteTimer.start();
        blackTimer.start();
    }
    private String getTime(int timeSeconds){
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
    private void toggleWhiteTimer(){
        if(whitePaused){
            whiteTimer = new CountDownTimer(whiteTimeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    whiteTimeLeft =  millisUntilFinished;
                    whiteTV.setText(getTime((int) (millisUntilFinished/1000)));
                }
                public void onFinish() {
                }
            }.start();
        }else{
            whiteTimer.cancel();
        }
        whitePaused = !whitePaused;
    }
    private void toggleBlackTimer(){
        if(blackPaused){
            blackTimer = new CountDownTimer(blackTimeLeft, 1000) {
                public void onTick(long millisUntilFinished) {
                    blackTimeLeft =  millisUntilFinished;
                    blackTV.setText(getTime((int) (millisUntilFinished/1000)));
                }
                public void onFinish() {
                }
            }.start();
        }else{
            blackTimer.cancel();
        }
        blackPaused = !blackPaused;
    }

}
