package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class TeamNamesDialog extends Dialog implements View.OnClickListener {

    private EditText teamNameA;
    private EditText teamNameB;

    private Button okButton;

    private Button teamARadioButton;
    private Button teamBRadioButton;
    public TeamNamesDialog(Activity a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_teamnames);

        teamNameA = (EditText) findViewById(R.id.teamNameA_editText);
        teamNameB = (EditText) findViewById(R.id.teamNameB_editText);

        okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(this);

        teamARadioButton = (Button) findViewById(R.id.teamARadioButton);
        teamBRadioButton = (Button) findViewById(R.id.teamBRadioButton);

        teamARadioButton.setOnClickListener(this);
        teamBRadioButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okButton:
                // Set team name labels
                Statistics.setTeamNameA(teamNameA.getText().toString());
                Statistics.setTeamNameB(teamNameB.getText().toString());
                MainActivity.setTeamNamesLabels();
                // Set isTeamA
                if( !teamARadioButton.isEnabled() ) {
                    MainActivity.toggleIsTeamA();
                }
                dismiss();
                break;
            case R.id.teamARadioButton:
                MainActivity.setTeam(true);
                break;
            case R.id.teamBRadioButton:
                MainActivity.setTeam(false);
            default:
                break;
        }
    }
}
