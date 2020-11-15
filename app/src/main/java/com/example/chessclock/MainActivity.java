package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button resetButton;
    LinearLayout whiteLL, blackLL;
    TextView whiteTV, blackTV;
    CountDownTimer whiteTimer, blackTimer;
    long whiteTime=70000, blackTime=70000, whiteTimeLeft, blackTimeLeft;
    boolean timeStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        findViewsByIds();
        setUI();
    }
    private void findViewsByIds(){
        resetButton = findViewById(R.id.resetButton);
        whiteLL = findViewById(R.id.whiteLL);
        blackLL = findViewById(R.id.blackLL);
        whiteTV = findViewById(R.id.whiteTV);
        blackTV = findViewById(R.id.blackTV);
    }
    private void setUI(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        whiteLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onClickWhiteTimer();
            }
        });
        blackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onClickBlackTimer();
            }
        });
        whiteTV.setText(String.valueOf(getTime((int) (whiteTimeLeft/1000))));
        blackTV.setText(String.valueOf(getTime((int) (blackTimeLeft/1000))));
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
    private void whiteCounter(){
        whiteTimer = new CountDownTimer(whiteTimeLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                whiteTimeLeft =  millisUntilFinished;
                whiteTV.setText(getTime((int) (millisUntilFinished/1000)));
            }
            public void onFinish() {
            }
        }.start();
    }
    private void blackCounter(){
        blackTimer = new CountDownTimer(blackTimeLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                blackTimeLeft =  millisUntilFinished;
                blackTV.setText(getTime((int) (millisUntilFinished/1000)));
            }
            public void onFinish() {
            }
        }.start();
    }
    private void onClickWhiteTimer(){
        if(!timeStarted) return;
        whiteTimer.cancel();
        blackCounter();
    }
    private void onClickBlackTimer() {
        if (!timeStarted){
            whiteCounter();
            timeStarted = true;
        }else{
            blackTimer.cancel();
            whiteCounter();
        }
    }
    private void reset(){
        whiteTimer.cancel();
        blackTimer.cancel();
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        setUI();
        timeStarted = false;
    }
}
