package com.android.paris8.sudoku;

import android.content.Context;
import android.util.Log;

public class Chrono extends Checker implements Runnable{

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context mContext;
    private long mStartTime;
    private boolean mIsRunning;
    private int seconds;
    private int minutes;


    public Chrono(Context mContext) {
        this.mContext = mContext;
    }

    public Chrono(Context context, long startTime) {
        this(context);
        mStartTime = startTime;
    }

    public void start() {
        if(mStartTime == 0) { //if the start time was not set before! e.g. by second constructor
            mStartTime = System.currentTimeMillis();
        }
        mIsRunning = true;
    }

    public void pause()
    {

    }

    public void stop()
    {
        mIsRunning = false;
    }



    public boolean isRunning() {
        return mIsRunning;
    }

    public int getSecondes()
    {
        return seconds;
    }

    public int getMinutes()
    {
        return minutes;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public void run() {

        while (mIsRunning)
        {
            long since = System.currentTimeMillis() - mStartTime;

            //int millis = (int) since / 1000;
            seconds = (int) ((since / 1000) % 60);
            minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
            //int hours = (int) ((since / MILLIS_TO_HOURS) % 24);

            ((Game)mContext).updateTimerText(String.format("%02d:%02d", minutes, seconds));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (win() == true)
            {
                mIsRunning = false;
            }
        }
    }
}
