package com.android.paris8.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private GameView mGameView;

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btn_gomme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGameView = (GameView) findViewById(R.id.GameView);
        mGameView.setVisibility(View.VISIBLE);

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btn1:
                    mGameView.val = 1;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn2:
                mGameView.val = 2;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn3:
                mGameView.val = 3;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn4:
                mGameView.val = 4;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn5:
                mGameView.val = 5;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn6:
                mGameView.val = 6;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn7:
                mGameView.val = 7;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn8:
                mGameView.val = 8;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn9:
                mGameView.val = 9;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_gomme:
                mGameView.val = 0;
                Toast.makeText(this, mGameView.val + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
