package com.android.paris8.sudoku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public final static String KEYTYPELEVEL = "com.android.paris8.sudoku.KEYTYPELEVEL";
    String KEYSOUND = "com.android.paris8.sudoku.KEYSOUND";
    String keyBtnSound = "com.android.paris8.sudoku.KEYBTNSOUND";

    SharedPreferences sharedPref;

    SaveGame saveGame;

    Button btn_facile, btn_moyen, btn_difficile, btn_score, btn_continue;

    private MenuItem sound;
    private MenuItem no_sound;

    private MediaPlayer buttonSound;
    private boolean stateSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveGame = new SaveGame(this);
        sharedPref = getSharedPreferences(KEYSOUND, Context.MODE_PRIVATE);
        stateSound = sharedPref.getBoolean(keyBtnSound, true);

        buttonSound = MediaPlayer.create(this, R.raw.sound_btn);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_facile = (Button) findViewById(R.id.btn_facile);
        btn_moyen = (Button) findViewById(R.id.btn_moyen);
        btn_difficile = (Button) findViewById(R.id.btn_difficile);
        btn_score = (Button) findViewById(R.id.btn_score);
        btn_continue = (Button) findViewById(R.id.btn_continue);


        if (!saveGame.getStateGameSaved()) btn_continue.setEnabled(false);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stateSound) buttonSound.start();

                GameView.typeLevel = 0;

                Intent i = new Intent(MainActivity.this, Game.class);
                startActivity(i);
                finish();
            }
        });


        btn_facile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stateSound) buttonSound.start();

                GameView.typeLevel = 1;
                if (saveGame.getStateGameSaved())
                {
                    dialogNewGame();
                } else {
                    Intent i = new Intent(MainActivity.this, Game.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        btn_moyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stateSound) buttonSound.start();

                GameView.typeLevel = 2;
                if (saveGame.getStateGameSaved())
                {
                    dialogNewGame();
                } else {
                    Intent i = new Intent(MainActivity.this, Game.class);
                    startActivity(i);
                    finish();
                }

            }
        });


        btn_difficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stateSound) buttonSound.start();

                GameView.typeLevel = 3;
                if (saveGame.getStateGameSaved())
                {
                    dialogNewGame();
                } else {
                    Intent i = new Intent(MainActivity.this, Game.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    public void dialogNewGame()
    {
        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Cette action supprimera la partie en cours")
                .setMessage("Si vous lancez la partie vous supprimerez la partie en cours.\nLancer la partie ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (stateSound) buttonSound.start();
                        saveGame.setStateGameSaved(false);
                        Intent i = new Intent(MainActivity.this, Game.class);
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
    public void onBackPressed() {

        new AlertDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Quitter le jeu")
                .setMessage("Voulez-vous vraiment quitter le jeu ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (stateSound) buttonSound.start();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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

            case R.id.regle_du_jeu:
                if (stateSound) buttonSound.start();

                Intent rules = new Intent(MainActivity.this, Rules.class);
                startActivity(rules);
                finish();
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

            case R.id.about:
                if (stateSound) buttonSound.start();

                Intent about = new Intent(MainActivity.this, About.class);
                startActivity(about);
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}