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

    private static ArrayList<String> time;
    private static ArrayList<String> desc;
    private static ArrayList<String> hit;

    private static int time_halftime_size;
    private static int desc_halftime_size;
    private static int hit_halftime_size;

    private static TextView teamNameA;
    private static TextView teamNameB;

    private Button teamAMinusButton;
    private Button teamAPlusButton;
    private Button teamBMinusButton;
    private Button teamBPlusButton;
    private LinearLayout background;
    private TextView teamAScore;
    private TextView teamBScore;

    private static Button undoButton;
    private Button summaryButton;
    private static Button halftimeButton;

    private static boolean isFirstHalf = true;

    private static boolean isTeamA = true;

    private float previousX;
    private float previousY;
    private int state;
    private int myScore;
    private int otherScore;

    public static void setTime_halftime_size() {
        MainActivity.time_halftime_size = time.size();
    }

    public static void setDesc_halftime_size() {
        MainActivity.desc_halftime_size = desc.size();
    }

    public static void setHit_halftime_size() {
        MainActivity.hit_halftime_size = hit.size();
    }

    public static void toggleIsTeamA() {
        isTeamA = !isTeamA;
    }

    public static void setTeam(boolean a) {
        isTeamA = a;
    }

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

        undoButton = (Button) findViewById(R.id.undoButton);
        undoButton.setOnClickListener(this);

        halftimeButton = (Button) findViewById(R.id.halftimeButton);
        halftimeButton.setOnClickListener(this);

        summaryButton = (Button) findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(this);

        background = (LinearLayout) findViewById(R.id.background);

        background.setOnTouchListener(this);
        background.setOnClickListener(this);
        background.setOnTouchListener(this);

        // Show dialog box that sets the team names
        TeamNamesDialog teamNamesDialog = new TeamNamesDialog(this);
        teamNamesDialog.show();

        setTeamNamesLabels();

        state = 0;
    }

    public static void changeToSecondHalf() {
        isFirstHalf = false;
    }

    public static void disableHalfTimeButton() {
        halftimeButton.setEnabled(false);
    }

    public static void setTeamNamesLabels() {
        teamNameA.setText( Statistics.getTeamNameA() );
        teamNameB.setText( Statistics.getTeamNameB() );
    }

    public void setTeamAScore() {
        if( isFirstHalf ) teamAScore.setText( Integer.toString( Statistics.getFirstScoreA() ) );
        else teamAScore.setText( Integer.toString( Statistics.getFirstScoreA() + Statistics.getSecondScoreA() ) );
    }

    public void setTeamBScore() {
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
        if (v.getId() == R.id.teamAMinusButton) {
            if (isFirstHalf) Statistics.decrementFirstScoreA();
            else Statistics.decrementSecondScoreA();
            setTeamAScore();
        } else if (v.getId() == R.id.teamAPlusButton) {
            if (isFirstHalf) Statistics.incrementFirstScoreA();
            else Statistics.incrementSecondScoreA();
            setTeamAScore();
        } else if (v.getId() == R.id.teamBMinusButton) {
            if (isFirstHalf) Statistics.decrementFirstScoreB();
            else Statistics.decrementSecondScoreB();
            setTeamBScore();
        } else if (v.getId() == R.id.teamBPlusButton) {
            if (isFirstHalf) Statistics.incrementFirstScoreB();
            else Statistics.incrementSecondScoreB();
            setTeamBScore();
        } else if(v.getId() == R.id.undoButton) {

            int timeSize = time.size();
            int descSize = desc.size();
            int hitSize = hit.size();
            if(isTeamA){
                if( (isFirstHalf && timeSize > 0 && descSize > 0 && hitSize > 0) ||
                        (!isFirstHalf && timeSize > time_halftime_size && descSize > desc_halftime_size && hitSize > hit_halftime_size) ) {
                    // Decrement the score if needed
                    if( hit.get(hitSize-1).equals("Hit") ) {
                        String desc_str = desc.get(descSize-1);
                        if( desc_str.equals("Free Throw") ) {
                            if( isFirstHalf ) Statistics.decrementFirstScoreA();
                            else Statistics.decrementSecondScoreA();
                        } else if( desc_str.equals("2 Pointer") ) {
                            if( isFirstHalf ) {
                                Statistics.decrementFirstScoreA();
                                Statistics.decrementFirstScoreA();
                            } else {
                                Statistics.decrementSecondScoreA();
                                Statistics.decrementSecondScoreA();
                            }
                        } else if( desc_str.equals("3 Pointer") ) {
                            if( isFirstHalf ) {
                                Statistics.decrementFirstScoreA();
                                Statistics.decrementFirstScoreA();
                                Statistics.decrementFirstScoreA();
                            } else {
                                Statistics.decrementSecondScoreA();
                                Statistics.decrementSecondScoreA();
                                Statistics.decrementSecondScoreA();
                            }
                        }
                        setTeamAScore();
                    }
                    // Pop item from the array lists
                    time.remove( timeSize - 1 );
                    desc.remove( descSize - 1 );
                    hit.remove( hitSize - 1 );
                }
            }
            else{
                if( (isFirstHalf && timeSize > 0 && descSize > 0 && hitSize > 0) ||
                        (!isFirstHalf && timeSize > time_halftime_size && descSize > desc_halftime_size && hitSize > hit_halftime_size) ) {
                    // Decrement the score if needed
                    if( hit.get(hitSize-1).equals("Hit") ) {
                        String desc_str = desc.get(descSize-1);
                        if( desc_str.equals("Free Throw") ) {
                            if( isFirstHalf ) Statistics.decrementFirstScoreA();
                            else Statistics.decrementSecondScoreB();
                        } else if( desc_str.equals("2 Pointer") ) {
                            if( isFirstHalf ) {
                                Statistics.decrementFirstScoreB();
                                Statistics.decrementFirstScoreB();
                            } else {
                                Statistics.decrementSecondScoreB();
                                Statistics.decrementSecondScoreB();
                            }
                        } else if( desc_str.equals("3 Pointer") ) {
                            if( isFirstHalf ) {
                                Statistics.decrementFirstScoreB();
                                Statistics.decrementFirstScoreB();
                                Statistics.decrementFirstScoreB();
                            } else {
                                Statistics.decrementSecondScoreB();
                                Statistics.decrementSecondScoreB();
                                Statistics.decrementSecondScoreB();
                            }
                        }
                        setTeamBScore();
                    }
                    // Pop item from the array lists
                    time.remove( timeSize - 1 );
                    desc.remove( descSize - 1 );
                    hit.remove( hitSize - 1 );
                }
            }

        } else if(v.getId() == R.id.halftimeButton) {

            if( isFirstHalf ) {
                HalfTimeDialog halfTimeDialog = new HalfTimeDialog(this);
                halfTimeDialog.show();
            }

        } else if(v.getId() == R.id.summaryButton) {

            int[] scores = { Statistics.getFirstScoreA(), Statistics.getFirstScoreB(),
                    Statistics.getFirstScoreA() + Statistics.getSecondScoreA(),
                    Statistics.getFirstScoreB() + Statistics.getSecondScoreB() };
            Bundle b = new Bundle();
            b.putIntArray("scores", scores);
            Intent myIntent = new Intent(this, SummaryActivity.class);
            myIntent.putExtras(b);
            myIntent.putExtra("scores", scores);
            myIntent.putStringArrayListExtra("time", time);
            myIntent.putStringArrayListExtra("desc", desc);
            myIntent.putStringArrayListExtra("hit", hit);
            myIntent.putExtra("teamA", Statistics.getTeamNameA());
            myIntent.putExtra("teamB", Statistics.getTeamNameB());
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
        Log.d("flag", isTeamA?"true":"false");
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        float x = event.getX(index);
        float y = event.getY(index);
        Calendar c = Calendar.getInstance();
        int m = c.get(Calendar.MINUTE);
        String ms = m < 10 ? "0"+Integer.toString(m) : Integer.toString(m);
        final String date = c.get(Calendar.HOUR)+":"+ms+(c.get(Calendar.AM_PM)==0?"AM":"PM");
        if(isTeamA) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    Log.d("", "actiondown");

                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(getApplicationContext(), " Free Throw Miss", Toast.LENGTH_SHORT).show();
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
                            if (isFirstHalf) Statistics.incrementFirstScoreA();
                            else Statistics.incrementSecondScoreA();
                            setTeamAScore();
                            time.add(date);
                            desc.add("Free Throw");
                            hit.add("Hit");
                        }
                    });
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    if (event.getPointerCount() == 2) {
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
                                if (isFirstHalf) {
                                    Statistics.incrementFirstScoreA();
                                    Statistics.incrementFirstScoreA();
                                } else {
                                    Statistics.incrementSecondScoreA();
                                    Statistics.incrementSecondScoreA();
                                }
                                setTeamAScore();
                                time.add(date);
                                desc.add("2 Pointer");
                                hit.add("Hit");
                            }
                        });
                    } else if (event.getPointerCount() == 3) {
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
                                if (isFirstHalf) {
                                    Statistics.incrementFirstScoreA();
                                    Statistics.incrementFirstScoreA();
                                    Statistics.incrementFirstScoreA();
                                } else {
                                    Statistics.incrementSecondScoreA();
                                    Statistics.incrementSecondScoreA();
                                    Statistics.incrementSecondScoreA();
                                }
                                time.add(date);
                                desc.add("3 Pointer");
                                hit.add("Hit");
                                setTeamAScore();
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
        }
            else{
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(getApplicationContext(), " Free Throw Miss", Toast.LENGTH_SHORT).show();
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
                            if( isFirstHalf ) Statistics.incrementFirstScoreB();
                            else Statistics.incrementSecondScoreB();
                            setTeamBScore();
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
                                if( isFirstHalf ) {
                                    Statistics.incrementFirstScoreB();
                                    Statistics.incrementFirstScoreB();
                                } else {
                                    Statistics.incrementSecondScoreB();
                                    Statistics.incrementSecondScoreB();
                                }
                                setTeamBScore();
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
                                if( isFirstHalf ) {
                                    Statistics.incrementFirstScoreB();
                                    Statistics.incrementFirstScoreB();
                                    Statistics.incrementFirstScoreB();
                                } else {
                                    Statistics.incrementSecondScoreB();
                                    Statistics.incrementSecondScoreB();
                                    Statistics.incrementSecondScoreB();
                                }
                                time.add(date);
                                desc.add("3 Pointer");
                                hit.add("Hit");
                                setTeamBScore();
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
        }
        return false;
    }

}
