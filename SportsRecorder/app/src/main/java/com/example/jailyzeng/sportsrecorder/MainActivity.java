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
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{

    private ArrayList<String> time;
    private ArrayList<String> desc;
    private ArrayList<String> hit;
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

    private float previousX;
    private float previousY;
    private int state;
    private int myScore;
    private int otherScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all objects needed
        time = new ArrayList<String>();
        desc = new ArrayList<String>();
        hit = new ArrayList<String>();

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

        background.setOnTouchListener(this);
        background.setOnClickListener(this);

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

    public float getPreviousY() {
        return previousY;
    }

    public float getPreviousX() {
        return previousX;
    }

    @Override
    public void onClick(View v) {
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
	    }
        else if(v.getId() == R.id.summaryButton) {
            int[] scores = {1,2,3,4,5,6,7,8};
            Bundle b = new Bundle();
            b.putIntArray("scores", scores);
            Intent myIntent = new Intent(this, SummaryActivity.class);
            myIntent.putExtras(b);
            myIntent.putExtra("scores", scores);
            startActivity(myIntent);
        }

    }

    @Override
    public boolean onLongClick(View v)
    {
        Log.d("", "longclick");
        Toast.makeText(this, "Long Touch", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Log.d("DEBUG", "Receiving touch event");
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        float x = event.getX(index);
        float y = event.getY(index);
        Calendar c = Calendar.getInstance();
        int m = c.get(Calendar.MINUTE);
        String ms = m < 10 ? "0"+Integer.toString(m) : Integer.toString(m);
        final String date = c.get(Calendar.HOUR)+":"+ms+(c.get(Calendar.AM_PM)==0?"AM":"PM");
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("","actiondown");
                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(), " Free Throw Miss", Toast.LENGTH_SHORT).show();
                        Statistics.incrementFirstScoreA();
                        MainActivity.setTeamAScore();
                        time.add(date);
                        desc.add("Free Throw");
                        hit.add("Miss");
                        return true;
                    }
                });
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "Free Throw Hit!", Toast.LENGTH_SHORT).show();
                        Statistics.incrementFirstScoreA();
                        MainActivity.setTeamAScore();
                        time.add(date);
                        desc.add("Free Throw");
                        hit.add("Hit");
                    }
                });
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if(event.getPointerCount()==2) {
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(getApplicationContext(), "2 Pointer Miss", Toast.LENGTH_SHORT).show();
                            time.add(date);
                            desc.add("2 Pointer");
                            hit.add("Miss");
                            return true;
                        }

                    });
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "2 Pointer Hit!", Toast.LENGTH_SHORT).show();
                            Statistics.incrementFirstScoreA();
                            Statistics.incrementFirstScoreA();
                            MainActivity.setTeamAScore();
                            time.add(date);
                            desc.add("2 Pointer");
                            hit.add("Hit");
                        }
                    });
                }
                else if(event.getPointerCount()==3){
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(getApplicationContext(), "3 Pointer Miss", Toast.LENGTH_SHORT).show();
                            time.add(date);
                            desc.add("3 Pointer");
                            hit.add("Miss");
                            return true;
                        }

                    });
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "3 Pointer Hit!", Toast.LENGTH_SHORT).show();
                            Statistics.incrementFirstScoreA();
                            Statistics.incrementFirstScoreA();
                            Statistics.incrementFirstScoreA();
                            time.add(date);
                            desc.add("3 Pointer");
                            hit.add("Hit");
                            MainActivity.setTeamAScore();
                        }
                    });
                }
                Log.d("fingers", Integer.toString(event.getPointerCount()));
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
            default:
                break;
        }
        return false;
    }

}
