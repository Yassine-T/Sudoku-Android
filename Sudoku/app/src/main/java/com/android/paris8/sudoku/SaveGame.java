package com.android.paris8.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveGame {



    private SharedPreferences sharedPref;

    String keySaveGame = "com.android.paris8.sudoku.KEYSAVEGAME";
    String keyCase = "com.android.paris8.sudoku.KEYCASE";
    String keyStateGame = "com.android.paris8.sudoku.KEYSTATEGAME";
    String keyTime = "com.android.paris8.sudoku.KEYTIME";
    String keyTime2 = "com.android.paris8.sudoku.KEYTIME2";
    String keyLevelName = "com.android.paris8.sudoku.KEYLEVELNAME";

    boolean saved = false;
    boolean saveBtnClick = false;

    public SaveGame(Context context) {

        sharedPref = context.getSharedPreferences(keySaveGame, Context.MODE_PRIVATE);

    }

    public boolean getStateGameSaved()
    {
        saved = sharedPref.getBoolean(keyStateGame, false);
        return saved;
    }

    public void setStateGameSaved(boolean saved)
    {
        this.saved = saved;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(keyStateGame, saved);
        editor.commit();

    }

    public void saveMatrice(Context context, int[][] matrice)
    {

        int[] tab = new int[81];

        int k = 0;

        for(int i=0;i<9;i++) {
            for (int j = 0; j < 9; j++) {
                tab[k] = matrice[i][j];



                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(keyCase + k, tab[k]);
                editor.commit();
                k++;

            }
        }
    }


    public int[][] getMatrice()
    {


        int[][] matrice = new int[9][9];
        int[] tab = new int[81];
        int k = 0;

        for (int l = 0; l < 81; l++)
        {
            tab[l] = sharedPref.getInt(keyCase + l, 0);
        }

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                matrice[i][j] = tab[k];

                k++;
            }
        }
        return matrice;
    }

    public void setSaveTime(long time)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(keyTime, time);
        editor.commit();
    }

    public long getSaveTime()
    {
        return sharedPref.getLong(keyTime, 0);
    }


    public void setSaveLevelName(String name)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyLevelName, name);
        editor.commit();
    }

    public String getSaveLevelName()
    {
        return sharedPref.getString(keyLevelName, "");
    }


    public boolean getClickBtnSave()
    {
        return saveBtnClick;
    }

    public void setClickBtnSave(boolean s)
    {
        this.saveBtnClick = s;
    }
}
