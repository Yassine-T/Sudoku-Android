package com.android.paris8.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Game extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPref;
    public final static String KEYSOUND = "com.android.paris8.sudoku.KEYSOUND";
    public final static String keyBtnSound = "com.android.paris8.sudoku.KEYBTNSOUND";
    MediaPlayer buttonSound;
    boolean stateSound = true;
    private MenuItem sound;
    private MenuItem no_sound;

    private GameView mGameView;
    private Checker checker;
    private SaveGame saveGame;


    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button btn_gomme;
    private Button btn_checked[];


    private TextView tv_timer;
    private TextView tv_niveau;


    Chrono chrono;
    Thread mThreadChrono;

    String facile, moyen, difficile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameView = (GameView) findViewById(R.id.GameView);
        mGameView.setVisibility(View.VISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        saveGame = new SaveGame(this);

        chrono = new Chrono(this);
        mThreadChrono = new Thread(chrono);
        mThreadChrono.start();
        chrono.start();

        sharedPref = getSharedPreferences(KEYSOUND, Context.MODE_PRIVATE);
        stateSound = sharedPref.getBoolean(keyBtnSound, true);

        Log.d("sound", "onCreate: " + stateSound);

        buttonSound = MediaPlayer.create(this, R.raw.sound_btn);

        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_niveau = (TextView) findViewById(R.id.tv_toolbar_niveau);

        int level = GameView.typeLevel;

        if (level == 0)
        {
            tv_niveau.setText(saveGame.getSaveLevelName());

            if (saveGame.getSaveLevelName().charAt(0) == 'F') facile = tv_niveau.getText().toString();
            else if (saveGame.getSaveLevelName().charAt(0) == 'M') moyen = tv_niveau.getText().toString();
            else difficile = tv_niveau.getText().toString();

        } else if (level == 1) {
            tv_niveau.setText("Facile : ");

        } else if (level == 2) {
            tv_niveau.setText("Moyen : ");

        } else {
            tv_niveau.setText("Difficile : ");

        }


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
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn1:

                if (stateSound) buttonSound.start();

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

                if (stateSound) buttonSound.start();

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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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

                if (stateSound) buttonSound.start();


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
        if (mGameView.isDialog == true)
        {
            Intent i = new Intent(Game.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        new AlertDialog.Builder(Game.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Quitter la partie")
                .setMessage("Voulez-vous vraiment quitter la partie ? La partie sera sauvegardé.")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (stateSound) buttonSound.start();


                        saveG(Game.this);
                        mThreadChrono.interrupt();
                        chrono.stop();
                        Toast.makeText(Game.this, "La partie à été sauvegardé", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Game.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (stateSound) buttonSound.start();

                    }
                }).show();


    }

    @Override
    protected void onResume() {
        super.onResume();

        chrono.pause = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

        chrono.pause = true;

        saveG(Game.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveG(Game.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sound = menu.findItem(R.id.enable_sound);
        no_sound = menu.findItem(R.id.disable_sound);

        if (stateSound)
        {
            sound.setChecked(true);
        } else {
            no_sound.setChecked(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home:

                new AlertDialog.Builder(Game.this)
                        .setIcon(R.drawable.ic_warning)
                        .setTitle("Quitter la partie")
                        .setMessage("Voulez-vous vraiment quitter la partie ? La partie sera sauvegardé.")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (stateSound) buttonSound.start();


                                saveG(Game.this);

                                mThreadChrono.interrupt();
                                chrono.stop();

                                Toast.makeText(Game.this, "La partie à été sauvegardé", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Game.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }

                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                saveGame.saveMatrice(this, GameView.matrice);
                break;

            case R.id.save_game:

                if (stateSound) buttonSound.start();


                Toast.makeText(this, "La partie à été sauvegardé", Toast.LENGTH_SHORT).show();

                saveG(Game.this);


                break;

            case R.id.enable_sound:
                sound.setChecked(true);

                if (stateSound) buttonSound.start();

                stateSound = true;
                SharedPreferences.Editor edit = sharedPref.edit();
                edit.putBoolean(keyBtnSound, stateSound);
                edit.commit();

                break;

            case R.id.disable_sound:
                no_sound.setChecked(true);

                if (stateSound) buttonSound.start();

                stateSound = false;
                SharedPreferences.Editor e = sharedPref.edit();
                e.putBoolean(keyBtnSound, stateSound);
                e.commit();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void saveG(Context context)
    {
        saveGame.setStateGameSaved(true);
        saveGame.saveMatrice(context, GameView.matrice);
        saveGame.setSaveLevelName(tv_niveau.getText().toString());
        saveGame.setClickBtnSave(true);
    }
}


