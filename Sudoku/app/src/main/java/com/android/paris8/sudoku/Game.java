package com.android.paris8.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;


public class Game extends AppCompatActivity implements View.OnClickListener {

    private GameView mGameView;
    private Checker checker;


    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btn_gomme;
    Button btn_checked[];


    TextView tv_timer;

    int minutes = 0;
    int secondes = 0;

    Chrono chrono;
    Thread mThreadChrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameView = (GameView) findViewById(R.id.GameView);
        mGameView.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        chrono = new Chrono(this);
        mThreadChrono = new Thread(chrono);
        mThreadChrono.start();
        chrono.start();

        tv_timer = (TextView) findViewById(R.id.tv_timer);

        btn_checked = new Button[10];


        checker = new Checker();


        btn1 = (Button) findViewById(R.id.btn1);            btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);            btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);            btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);            btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);            btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);            btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);            btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);            btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);            btn9.setOnClickListener(this);
        btn_gomme = (Button) findViewById(R.id.btn_gomme);  btn_gomme.setOnClickListener(this);

        btn_checked[0] = btn_gomme;
        btn_checked[1] = btn1;
        btn_checked[2] = btn2;
        btn_checked[3] = btn3;
        btn_checked[4] = btn4;
        btn_checked[5] = btn5;
        btn_checked[6] = btn6;
        btn_checked[7] = btn7;
        btn_checked[8] = btn8;
        btn_checked[9] = btn9;



    }

    public void updateTimerText(final String time)
    {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_timer.setText(time);
                //Log.d("chrono", "minutes : " + chrono.getMinutes() + " secondes: " + chrono.getSecondes());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn1:
                mGameView.val = 1;
                mGameView.clickCase = false;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;
                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[1].setBackgroundResource(R.drawable.btn_checked);
                mGameView.invalidate();

                break;

            case R.id.btn2:
                mGameView.val = 2;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;
                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[2].setBackgroundResource(R.drawable.btn_checked);
                mGameView.invalidate();

                break;

            case R.id.btn3:
                mGameView.val = 3;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;
                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[3].setBackgroundResource(R.drawable.btn_checked);
                mGameView.invalidate();

                break;

            case R.id.btn4:
                mGameView.val = 4;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[4].setBackgroundResource(R.drawable.btn_checked);
                mGameView.invalidate();

                break;

            case R.id.btn5:
                mGameView.val = 5;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[5].setBackgroundResource(R.drawable.btn_checked);

                break;

            case R.id.btn6:
                mGameView.val = 6;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[6].setBackgroundResource(R.drawable.btn_checked);

                break;

            case R.id.btn7:
                mGameView.val = 7;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[7].setBackgroundResource(R.drawable.btn_checked);

                break;

            case R.id.btn8:
                mGameView.val = 8;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[8].setBackgroundResource(R.drawable.btn_checked);

                break;

            case R.id.btn9:
                mGameView.val = 9;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[9].setBackgroundResource(R.drawable.btn_checked);

                break;

            case R.id.btn_gomme:
                mGameView.val = 0;
                mGameView.clickCase = false;
                if (mGameView.getValCaseSelected() == mGameView.val)
                    mGameView.clickCase = true;

                for (int i = 0; i < 10; i++)
                {
                    btn_checked[i].setBackgroundResource(R.drawable.btn_unchecked);
                }
                btn_checked[0].setBackgroundResource(R.drawable.btn_checked);

                break;
        }
    }



    @Override
    public void onBackPressed() {
        if (checker.win() == true) finish();
        new AlertDialog.Builder(Game.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Quitter la partie")
                .setMessage("Voulez-vous vraiment quitter la partie ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

        minutes = chrono.getMinutes();
        secondes = chrono.getSecondes();

        Log.d("pause", "onPause: " + minutes + " --------- " + secondes);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}

