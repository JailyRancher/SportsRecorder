package com.example.jailyzeng.sportsrecorder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

/**
 * Created by jailyzeng on 3/30/16.
 */
public class TouchSpot extends View implements View.OnTouchListener {
    private Paint mPaint;  // styling for drawing
    private Bitmap mBitmap; // cache for the drawing
    private Canvas mCanvas; // tools to draw on the cache
    private int dotRadius;
    private HashMap pointerMap; // map pointer id to last detected touch point


    public TouchSpot(Context context) {
        super(context);
        initTouchSpot();
    }

    public TouchSpot(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTouchSpot();
    }

    public TouchSpot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTouchSpot();
    }

    public TouchSpot(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initTouchSpot();
    }

    private void initTouchSpot() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND); // this will make the strokes look nice
        dotRadius = 10;
        pointerMap = new HashMap();
        setOnTouchListener(this);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    public boolean onTouch(View v, MotionEvent event) {
        // Log.d("DEBUG", "Receiving touch event");
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        float x = event.getX(index);
        float y = event.getY(index);

        switch (action) {
            case MotionEvent.ACTION_DOWN:


                v.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub
                        Statistics.incrementFirstScoreA();
                        //MainActivity.setTeamAScore();
                        return true;
                    }

                });
                break;

//                mCanvas.drawColor(20);
//                mCanvas.drawCircle(event.getX(), event.getY(), 50, mPaint);
//                invalidate();
                //break;
            case MotionEvent.ACTION_POINTER_DOWN:

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                pointerMap.remove(id);
                break;

            default:
                break;
        }
        return true;
    }
}
