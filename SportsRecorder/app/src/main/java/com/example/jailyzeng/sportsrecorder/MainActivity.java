package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener{

    private static TextView teamNameA;
    private static TextView teamNameB;

    private static Button teamAMinusButton;
    private static Button teamAPlusButton;
    private static Button teamBMinusButton;
    private static Button teamBPlusButton;
    private static LinearLayout background;
    private static TextView teamAScore;
    private static TextView teamBScore;

    private static Button summaryButton;

    private static boolean isFirstHalf = true;
    private float x1,x2;
    private static final int MIN_DISTANCE = 150;

    private float previousX;
    private float previousY;
    private int state;
    private int myScore;
    private int otherScore;
    private HashMap pointerMap; // map pointer id to last detected touch point

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all objects needed
        teamNameA = (TextView) findViewById(R.id.teamNameA_label);
        teamNameB = (TextView) findViewById(R.id.teamNameB_label);

        teamAMinusButton = (Button) findViewById(R.id.teamAMinusButton);
        teamAPlusButton = (Button) findViewById(R.id.teamAPlusButton);
        teamBMinusButton = (Button) findViewById(R.id.teamBMinusButton);
        teamBPlusButton = (Button) findViewById(R.id.teamBPlusButton);
        teamAMinusButton.setOnClickListener(this);
        teamAPlusButton.setOnClickListener(this);
        teamBMinusButton.setOnClickListener(this);
        teamBPlusButton.setOnClickListener(this);

        teamAScore = (TextView) findViewById(R.id.teamAScore);
        teamBScore = (TextView) findViewById(R.id.teamBScore);

        summaryButton = (Button) findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(this);

        background = (LinearLayout) findViewById(R.id.background);
        background.setOnClickListener(this);


        //Log.w("location", "location");

        // Show dialog box that sets the team names
        TeamNamesDialog teamNamesDialog = new TeamNamesDialog(this);
        teamNamesDialog.show();

        setTeamNamesLabels();

        state = 0;
    }

    public static void setTeamNamesLabels() {
        teamNameA.setText( Statistics.getTeamNameA() );
        teamNameB.setText( Statistics.getTeamNameB() );
    }

    public static void setTeamAScore() {
        if( isFirstHalf ) teamAScore.setText( Integer.toString( Statistics.getFirstScoreA() ) );
        else teamAScore.setText( Integer.toString( Statistics.getFirstScoreA() + Statistics.getSecondScoreA() ) );
    }

    public static void setTeamBScore() {
        if( isFirstHalf ) teamBScore.setText( Integer.toString( Statistics.getFirstScoreB() ) );
        else teamBScore.setText( Integer.toString(Statistics.getFirstScoreB() + Statistics.getSecondScoreB() ) );
    }

    public static void changeToSecondHalf() {
        isFirstHalf = false;
    }

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            Log.e("", "Longpress detected");
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

//        float x = e.getX();
//        float y = e.getY();
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                state = 1;
//            case MotionEvent.ACTION_UP:
//                if(state == 1){
//                    state = 0;
//                    previousX = x;
//                    previousY = y;
//                    Log.d("location", "location: X - " + Float.toString(previousX) + " || Y - " + Float.toString(previousY));
//                    return true;
//                }
//        }
//
//
//        return false;

        return gestureDetector.onTouchEvent(e);
    }

    public float getPreviousY() {
        return previousY;
    }

    public float getPreviousX() {
        return previousX;
    }
    
    @Override
    public void onClick(View v) {
        Log.d("click","click");
        if( v.getId() == R.id.teamAMinusButton ) {
            if( isFirstHalf ) Statistics.decrementFirstScoreA();
            else Statistics.decrementSecondScoreA();
            setTeamAScore();
        } else if( v.getId() == R.id.teamAPlusButton ) {
            if( isFirstHalf ) Statistics.incrementFirstScoreA();
            else Statistics.incrementSecondScoreA();
            setTeamAScore();
        } else if( v.getId() == R.id.teamBMinusButton ) {
            if( isFirstHalf ) Statistics.decrementFirstScoreB();
            else Statistics.decrementSecondScoreB();
            setTeamBScore();
        } else if( v.getId() == R.id.teamBPlusButton ) {
            if( isFirstHalf ) Statistics.incrementFirstScoreB();
            else Statistics.incrementSecondScoreB();
            setTeamBScore();
	    } else if(v.getId() == R.id.summaryButton) {
            int[] scores = {1,2,3,4,5,6,7,8};
            Bundle b = new Bundle();
            b.putIntArray("scores", scores);
            Intent myIntent = new Intent(this, SummaryActivity.class);
            myIntent.putExtras(b);
            myIntent.putExtra("scores", scores);
            startActivity(myIntent);
        }

    }

}
