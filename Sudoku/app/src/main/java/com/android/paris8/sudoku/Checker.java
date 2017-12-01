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

            /*if (result == valeur[i] && result != 0)
            {
                Log.d("Valeur", "valeur = " + valeur[i]);
                count++;
            }
            Log.d("Valeur", "count = " + count);


            if (count > 1)
            {
                return false;
            }*/


        }


        if (getOccurence(valeur) == true) return false;

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

        int[] valeur = new int[9];
        int resultat = 0;

        for(int i=0;i<9;i++){
            resultat = 0;
            for(int j=0;j<9;j++){
                valeur[j] = matrice[i][j];
                resultat += valeur[j];
            }

            if (getOccurence(valeur) == true || resultat != 45) return false;

        }


        return true;
    }

    public boolean checkColumn()
    {

        int[] valeur = new int[9];
        int resultat = 0;

        for(int i=0;i<9;i++){
            resultat = 0;
            for(int j=0;j<9;j++){
                valeur[j] = matrice[j][i];
                resultat += valeur[j];
            }

            if (getOccurence(valeur) == true || resultat != 45) return false;

        }


        return true;
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


    public boolean getOccurence(int[] tab)
    {
        int nbre;
        int occ = 0;
        for (int i=0; i<tab.length; i++)
        {
            nbre = tab[i];
            occ = 0;
            for (int j=0; j<tab.length; j++) {
                if (tab[j] == nbre && tab[j] != 0)
                {
                    occ++;
                    if (occ == 2) return true;
                }
            }

        }

        return false;
    }


    public boolean win()
    {
        if (checkGrid() == true) {

            if (checkColumn() == true && checkLine() == true)
                return true;
        }

        return false;
    }

} 
