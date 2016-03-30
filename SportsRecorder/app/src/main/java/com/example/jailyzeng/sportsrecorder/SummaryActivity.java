package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;

/**
 * Created by liwingyee on 15/3/16.
 */
public class SummaryActivity extends Activity implements View.OnClickListener {

    private TextView quarterInfo;
    private TextView eventInfo;

    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // TODO: make the two parts scrollable
        //quarterInfo = (TableLayout) findViewById(R.id.quarterInfo);
        //quarterInfo.setMovementMethod(new ScrollingMovementMethod());

        //eventInfo = (TextView) findViewById(R.id.eventInfo);
        //eventInfo.setMovementMethod(new ScrollingMovementMethod());

        shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.shareButton) {
            shareAsEmail();
        }
    }

    private void shareAsEmail() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
