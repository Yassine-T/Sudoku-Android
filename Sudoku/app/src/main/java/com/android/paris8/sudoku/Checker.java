package com.android.paris8.sudoku;
import android.util.Log;

import static com.android.paris8.sudoku.GameView.matrice;

public class Checker {


    public Checker() {

    }

    public boolean checkCaseWrittenLine()
    {
        int[] valeur = new int[9];
        int result = GameView.getValCaseSelected();
        int count = 0;

        for(int i=0;i<9;i++){

            valeur[i] = matrice[GameView.getLine()][i];

            if (result == valeur[i] && result != 0)
            {
                Log.d("Valeur", "valeur = " + valeur[i]);
                count++;
            }
            Log.d("Valeur", "count = " + count);
            if (count > 1)
            {
                return false;
            }


        }

        return true;
    }

    public boolean checkCaseWrittenColumn()
    {
        int[] valeur = new int[9];
        int result = GameView.getValCaseSelected();
        int count = 0;

        for(int i=0;i<9;i++){

            valeur[i] = matrice[i][GameView.getColumn()];

            if (result == valeur[i] && result != 0)
            {
                Log.d("Valeur", "valeur = " + valeur[i]);
                count++;
            }
            Log.d("Valeur", "count = " + count);
            if (count > 1)
            {
                return false;
            }


        }

        return true;
    }

    public boolean checkLine()
    {

        for(int i=0;i<9;i++){
            Log.d("Checkline", "check I: " + i);
            for(int j=0;j<9;j++){
                Log.d("Checkline", "check J: " + j);


            }
        }

        return false;
    }

    public boolean checkColumn()
    {
        return false;
    }

    public boolean checkBlock()
    {
        return false;
    }

    public boolean checkGrid()
    {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){

                if (matrice[j][i] == 0)
                {
                    return false;
                }

            }
        }


        return true;
    }
} 
