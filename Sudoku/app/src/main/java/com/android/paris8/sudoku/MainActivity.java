package com.android.paris8.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public final static String KEYTYPELEVEL = "com.android.paris8.sudoku.KEYTYPELEVEL";
    Button btn_facile, btn_moyen, btn_difficile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_facile = (Button) findViewById(R.id.btn_facile);
        btn_moyen = (Button) findViewById(R.id.btn_moyen);
        btn_difficile = (Button) findViewById(R.id.btn_difficile);

        btn_facile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameView.typeLevel = 1;
                Intent i = new Intent(MainActivity.this, Game.class);
                startActivity(i);

            }
        });

        btn_moyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameView.typeLevel = 2;
                Intent i = new Intent(MainActivity.this, Game.class);
                startActivity(i);

            }
        });


        btn_difficile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameView.typeLevel = 3;
                Intent i = new Intent(MainActivity.this, Game.class);
                startActivity(i);

            }
        });

    }

}