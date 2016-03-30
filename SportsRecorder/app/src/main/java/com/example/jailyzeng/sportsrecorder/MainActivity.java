package com.example.jailyzeng.sportsrecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private float previousX;
    private float previousY;
    private int state;
    private int myScore;
    private int otherScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = 0;
    }

/*
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                state = 1;
            case MotionEvent.ACTION_UP:
                if(state == 1){
                    state = 0;
                    previousX = x;
                    previousY = y;
                    Log.d("location", "location: X - " + Float.toString(previousX) + " || Y - " + Float.toString(previousY));
                    return true;
                }
        }


        return false;
    }

    public float getPreviousY() {
        return previousY;
    }

    public float getPreviousX() {
        return previousX;
    }
    */
}
