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
    LinearLayout whiteLL, blackLL;
    TextView incrementTV, whiteTV, blackTV;
    CountDownTimer whiteTimer, blackTimer;
    long whiteTime, blackTime, whiteTimeLeft, blackTimeLeft,
            whiteIncrement, blackIncrement;
    boolean timeStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getTimeControl();
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        findViewsByIds();
        setUI();
    }
    private void getTimeControl(){
        whiteTime = getIntent().getLongExtra("whiteTimeMillis", 0);
        whiteIncrement = getIntent().getLongExtra("whiteIncrement", 0);
        blackTime = getIntent().getLongExtra("blackTimeMillis", 0);
        blackIncrement = getIntent().getLongExtra("blackIncrement", 0);
    }
    private void findViewsByIds(){
        backIV = findViewById(R.id.backIV);
        incrementTV = findViewById(R.id.incrementTV);
        whiteLL = findViewById(R.id.whiteLL);
        blackLL = findViewById(R.id.blackLL);
        whiteTV = findViewById(R.id.whiteTV);
        blackTV = findViewById(R.id.blackTV);
    }
    private void setUI(){
        int w = (int) (whiteIncrement/1000);
        int b = (int) (blackIncrement/1000);
        String increments = "White: +"+w+"s | Black: +"+b+"s";
        incrementTV.setText(increments);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
        whiteLL.setBackgroundColor(getColor(R.color.light_grey));
        blackTV.setText(String.valueOf(getTime((int) (blackTimeLeft/1000))));
        blackLL.setBackgroundColor(getColor(R.color.dark_grey));
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
                if(millisUntilFinished<10000)
                    whiteTV.setTextColor(getColor(R.color.dark_red));
            }
            public void onFinish() {
            }
        }.start();
        whiteLL.setBackgroundColor(getColor(R.color.dark_grey));
        blackLL.setBackgroundColor(getColor(R.color.light_grey));
    }
    private void blackCounter(){
        blackTimer = new CountDownTimer(blackTimeLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                blackTimeLeft =  millisUntilFinished;
                blackTV.setText(getTime((int) (millisUntilFinished/1000)));
                if(millisUntilFinished<10000)
                    blackTV.setTextColor(getColor(R.color.dark_red));
            }
            public void onFinish() {
            }
        }.start();
        blackLL.setBackgroundColor(getColor(R.color.dark_grey));
        whiteLL.setBackgroundColor(getColor(R.color.light_grey));
    }
    private void onClickWhiteTimer() {
        if(whiteTimeLeft==0 || blackTimeLeft==0) return;
        if(!timeStarted) return;
        if(whiteTimer!=null) {
            whiteTimer.cancel();
            whiteTimeLeft += whiteIncrement;
            whiteTV.setText(getTime((int) (whiteTimeLeft/1000)));
        }
        blackCounter();
    }
    private void onClickBlackTimer() {
        if(whiteTimeLeft==0 || blackTimeLeft==0) return;
        if (!timeStarted){
            whiteCounter();
            timeStarted = true;
        }else{
            if(blackTimer!=null){
                blackTimer.cancel();
                blackTimeLeft += blackIncrement;
                blackTV.setText(getTime((int) (blackTimeLeft/1000)));
            }
            whiteCounter();
        }
    }
    public void reset(View view){
        if(whiteTimer!=null) whiteTimer.cancel();
        if(blackTimer!=null) blackTimer.cancel();
        whiteTimeLeft = whiteTime;
        blackTimeLeft = blackTime;
        setUI();
        timeStarted = false;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SetClockActivity.class));
    }
}
