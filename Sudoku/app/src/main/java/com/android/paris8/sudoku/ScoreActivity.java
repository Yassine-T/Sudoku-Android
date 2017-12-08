package com.android.paris8.sudoku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    public static final String PREF_FILE_NAME = "PrefFile";

    SharedPreferences sharedPref;

    String KeyBestScoreEasy = "com.android.paris8.sudoku.KEYBESTSCOREEASY";
    String KeyBestScoreMedium = "com.android.paris8.sudoku.KEYBESTSCOREMEDIUM";
    String KeyBestScoreHard = "com.android.paris8.sudoku.KEYBESTSCOREHARD";

    private String bestScoreEasy;
    private String bestScoreMedium;
    private String bestScoreHard;

    private TextView tv_score_easy;
    private TextView tv_score_medium;
    private TextView tv_score_hard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);

        bestScoreEasy = sharedPref.getString(KeyBestScoreEasy, "00 : 00");
        bestScoreMedium = sharedPref.getString(KeyBestScoreMedium, "00 : 00");
        bestScoreHard = sharedPref.getString(KeyBestScoreHard, "00 : 00");

        tv_score_easy = (TextView) findViewById(R.id.tv_score_easy);
        tv_score_medium = (TextView) findViewById(R.id.tv_score_medium);
        tv_score_hard = (TextView) findViewById(R.id.tv_score_hard);

        tv_score_easy.setText(bestScoreEasy);
        tv_score_medium.setText(bestScoreMedium);
        tv_score_hard.setText(bestScoreHard);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ScoreActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
