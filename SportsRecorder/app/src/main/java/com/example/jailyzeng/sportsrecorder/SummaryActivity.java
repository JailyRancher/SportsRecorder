package com.example.jailyzeng.sportsrecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.Arrays;
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

        Bundle b = this.getIntent().getExtras();
        int[] scores = b.getIntArray("scores");
        ((TextView) findViewById(R.id.q1s1)).setText(Integer.toString(scores[0]));
        ((TextView) findViewById(R.id.q1s2)).setText(Integer.toString(scores[1]));
        ((TextView) findViewById(R.id.q2s1)).setText(Integer.toString(scores[2]));
        ((TextView) findViewById(R.id.q2s2)).setText(Integer.toString(scores[3]));
        ((TextView) findViewById(R.id.q3s1)).setText(Integer.toString(scores[4]));
        ((TextView) findViewById(R.id.q3s2)).setText(Integer.toString(scores[5]));
        ((TextView) findViewById(R.id.q4s1)).setText(Integer.toString(scores[6]));
        ((TextView) findViewById(R.id.q4s2)).setText(Integer.toString(scores[7]));

        TableLayout tl = (TableLayout) findViewById(R.id.eventInfo);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        for(int i = 0; i<20; i++) {
            TableRow tableRow = new TableRow(this);

            TextView textView = new TextView(this);
            textView.setText("11:05am");
            textView.setPadding(10, 10, 10, 10);
            textView.setGravity(Gravity.CENTER);

            TextView textView1 = new TextView(this);
            textView1.setText("Slam Dunk");
            textView1.setPadding(10, 10, 10, 10);
            textView1.setGravity(Gravity.CENTER);

            TextView textView2 = new TextView(this);
            textView2.setText("Missed");
            textView2.setPadding(10, 10, 10, 10);
            textView2.setGravity(Gravity.CENTER);

            tableRow.addView(textView);
            tableRow.addView(textView1);
            tableRow.addView(textView2);

            tl.addView(tableRow, layoutParams);
        }
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
        int[] scores = (int[]) this.getIntent().getSerializableExtra("scores");
        String shareBody = Arrays.toString(scores);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

}
