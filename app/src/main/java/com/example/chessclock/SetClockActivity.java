package com.example.chessclock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TimePicker;

public class SetClockActivity extends AppCompatActivity {

    TimePicker whiteTP, blackTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_clock);
        findViewsByIds();
    }
    private void findViewsByIds(){
        whiteTP = findViewById(R.id.whiteTP);
        blackTP = findViewById(R.id.blackTP);
        whiteTP.setIs24HourView(true);
        blackTP.setIs24HourView(true);
    }
    private void openClock(){
        
    }
}
