package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class SetClockActivity extends AppCompatActivity {

    private RelativeLayout blackRL;
    private Switch similarSwitch;
    private NumberPicker whiteNP_H, whiteNP_M, whiteNP_S, whiteNP_increment,
                 blackNP_H, blackNP_M, blackNP_S, blackNP_increment;
    private TextView errorTV;
    private int hoursW, minutesW, secondsW, incrementW,
                hoursB, minutesB, secondsB, incrementB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_clock);
        findViewsByIds();
        setClocks();
    }
    private void findViewsByIds(){
        blackRL = findViewById(R.id.blackRL);
        similarSwitch = findViewById(R.id.similarSwitch);
        similarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                blackRL.setVisibility(isChecked ? View.GONE : View.VISIBLE);
            }
        });
        whiteNP_H = findViewById(R.id.whiteNP_H);
        whiteNP_M = findViewById(R.id.whiteNP_M);
        whiteNP_S = findViewById(R.id.whiteNP_S);
        whiteNP_increment = findViewById(R.id.whiteNP_add);
        blackNP_H = findViewById(R.id.blackNP_H);
        blackNP_M = findViewById(R.id.blackNP_M);
        blackNP_S = findViewById(R.id.blackNP_S);
        blackNP_increment = findViewById(R.id.blackNP_add);
        errorTV = findViewById(R.id.errorTV);
    }
    private void setClocks(){
        whiteNP_H.setMinValue(0);
        whiteNP_H.setMaxValue(23);
        whiteNP_M.setMinValue(0);
        whiteNP_M.setMaxValue(59);
        whiteNP_S.setMinValue(0);
        whiteNP_S.setMaxValue(59);
        whiteNP_increment.setMinValue(0);
        whiteNP_increment.setMaxValue(59);
        blackNP_H.setMinValue(0);
        blackNP_H.setMaxValue(23);
        blackNP_M.setMinValue(0);
        blackNP_M.setMaxValue(59);
        blackNP_S.setMinValue(0);
        blackNP_S.setMaxValue(59);
        blackNP_increment.setMinValue(0);
        blackNP_increment.setMaxValue(59);
    }
    private void displayError(){
        errorTV.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                errorTV.setVisibility(View.GONE);
            }
        }, 1000);
    }
    private void getValues(){
        hoursW = whiteNP_H.getValue();
        minutesW = whiteNP_M.getValue();
        secondsW = whiteNP_S.getValue();
        incrementW = whiteNP_increment.getValue();
        hoursB = blackNP_H.getValue();
        minutesB = blackNP_M.getValue();
        secondsB = blackNP_S.getValue();
        incrementB = blackNP_increment.getValue();
    }
    public void start(View view){
        getValues();
        if(similarSwitch.isChecked() && (hoursW + minutesW + secondsW == 0)){
            displayError();
            return;
        }
        if(((hoursW + minutesW + secondsW == 0) || (hoursB + minutesB + secondsB == 0))
                && !similarSwitch.isChecked()){
            displayError();
            return;
        }
        long whiteTimeMillis = (hoursW*3600000) + (minutesW*60000) + (secondsW*1000);
        long whiteIncrement  = incrementW*1000;
        long blackTimeMillis = (hoursB*3600000) + (minutesB*60000) + (secondsB*1000);
        long blackIncrement  = incrementB*1000;
        if(similarSwitch.isChecked()){
            blackIncrement = whiteIncrement;
            blackTimeMillis = whiteTimeMillis;
        }
        startActivity(
                new Intent(getApplicationContext(), ClockActivity.class)
                        .putExtra("whiteTimeMillis", whiteTimeMillis)
                        .putExtra("whiteIncrement", whiteIncrement)
                        .putExtra("blackTimeMillis", blackTimeMillis)
                        .putExtra("blackIncrement", blackIncrement));
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
