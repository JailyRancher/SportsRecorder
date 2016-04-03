package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button shareButton;
    private Button summaryButton;

    private float previousX;
    private float previousY;
    private int state;
    private int myScore;
    private int otherScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.w("location", "location");

        summaryButton = (Button) findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(this);

        state = 0;
    }

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
    
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.summaryButton) {
            int[] scores = {1,2,3,4,5,6,7,8};
            Bundle b = new Bundle();
            b.putIntArray("scores", scores);
            Intent myIntent = new Intent(this, SummaryActivity.class);
            myIntent.putExtras(b);
            startActivity(myIntent);
        }
    }
}
