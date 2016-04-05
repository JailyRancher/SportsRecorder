package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HalfTimeDialog extends Dialog implements View.OnClickListener {

    private TextView msg;

    private Button yesButton;
    private Button noButton;

    public HalfTimeDialog(Activity a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_halftime);

        yesButton = (Button) findViewById(R.id.halftimeYesButton);
        yesButton.setOnClickListener(this);

        noButton = (Button) findViewById(R.id.halftimeNoButton);
        noButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.halftimeYesButton:
                MainActivity.changeToSecondHalf();
                dismiss();
                break;
            case R.id.halftimeNoButton:
                dismiss();
                break;
            default:
                break;
        }
    }

}
