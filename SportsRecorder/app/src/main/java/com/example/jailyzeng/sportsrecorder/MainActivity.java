package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        shareButton = (Button) findViewById(R.id.shareButton);
//        shareButton.setOnClickListener(this);

        Intent myIntent = new Intent(this, SummaryActivity.class);
        startActivity(myIntent);
    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.shareButton) {
//            shareAsEmail();
//        }
//    }
//
//    private void shareAsEmail() {
//        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//        sharingIntent.setType("text/plain");
//        String shareBody = "Here is the share content body";
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
//        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
//
//        startActivity(Intent.createChooser(sharingIntent, "Share via"));
//    }

}