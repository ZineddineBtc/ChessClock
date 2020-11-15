package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class ClockActivity extends AppCompatActivity {

    ImageView backIV;
    Button resetButton, pauseButton;
    LinearLayout whiteLL, blackLL;
    TextView whiteTV, blackTV;
    CountDownTimer whiteTimer, blackTimer;
    long whiteTime=70000, blackTime=70000, whiteTimeLeft, blackTimeLeft,
            whiteAdd=7000, blackAdd=7000;
    boolean timeStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Objects.requireNonNull(getSupportActionBar()).hide();
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        findViewsByIds();
        setUI();
    }
    private void findViewsByIds(){
        backIV = findViewById(R.id.backIV);
        resetButton = findViewById(R.id.resetButton);
        //pauseButton = findViewById(R.id.pauseButton);
        whiteLL = findViewById(R.id.whiteLL);
        blackLL = findViewById(R.id.blackLL);
        whiteTV = findViewById(R.id.whiteTV);
        blackTV = findViewById(R.id.blackTV);
    }
    private void setUI(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        whiteTimer = new CountDownTimer(whiteTimeLeft+whiteAdd, 1000) {
            public void onTick(long millisUntilFinished) {
                whiteTimeLeft =  millisUntilFinished;
                whiteTV.setText(getTime((int) (millisUntilFinished/1000)));
                if(millisUntilFinished<10000)
                    whiteTV.setTextColor(getColor(R.color.dark_red));
            }
            public void onFinish() {
            }
        }.start();
        /*pausedAll = false;
        blackPaused = true;
        whitePaused = false;*/
        whiteLL.setBackgroundColor(getColor(R.color.dark_grey));
        blackLL.setBackgroundColor(getColor(R.color.light_grey));
        pauseButton.setBackgroundColor(getColor(R.color.dark_grey));
    }
    private void blackCounter(){
        blackTimer = new CountDownTimer(blackTimeLeft+blackAdd, 1000) {
            public void onTick(long millisUntilFinished) {
                blackTimeLeft =  millisUntilFinished;
                blackTV.setText(getTime((int) (millisUntilFinished/1000)));
                if(millisUntilFinished<10000)
                    blackTV.setTextColor(getColor(R.color.dark_red));
            }
            public void onFinish() {
            }
        }.start();
        /*pausedAll = false;
        blackPaused = false;
        whitePaused = true;*/
        blackLL.setBackgroundColor(getColor(R.color.dark_grey));
        whiteLL.setBackgroundColor(getColor(R.color.light_grey));
        pauseButton.setBackgroundColor(getColor(R.color.dark_grey));
    }
    private void onClickWhiteTimer(){
        if(!timeStarted) return;
        //if(whitePaused && !blackPaused) return;
        if(whiteTimer!=null) whiteTimer.cancel();
        blackCounter();
    }
    private void onClickBlackTimer() {
        if (!timeStarted){
            whiteCounter();
            timeStarted = true;
        }else{
            //if(blackPaused && !whitePaused) return;
            if(blackTimer!=null)blackTimer.cancel();
            whiteCounter();
        }
    }
    private void reset(){
        whiteTimer.cancel();
        blackTimer.cancel();
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        pauseButton.setBackgroundColor(getColor(R.color.light_grey));
        setUI();
        timeStarted = false;
    }
    /*private void pause(){
        if(pausedAll) return;
        if(blackPaused && !whitePaused && (whiteTimer!=null)){
            whiteTimer.cancel();
            whitePaused = true;
            blackPaused = false;
            blackLL.setBackgroundColor(getColor(R.color.dark_grey));
            whiteLL.setBackgroundColor(getColor(R.color.light_grey));
        }else if(!blackPaused && whitePaused && (whiteTimer!=null)){
            blackTimer.cancel();
            blackPaused = true;
            whitePaused = false;
            whiteLL.setBackgroundColor(getColor(R.color.dark_grey));
            blackLL.setBackgroundColor(getColor(R.color.light_grey));
        }
        pausedAll = true;
        pauseButton.setBackgroundColor(getColor(R.color.light_grey));
    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SetClockActivity.class));
    }
}
