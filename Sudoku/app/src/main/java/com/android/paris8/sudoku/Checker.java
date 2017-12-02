package com.android.paris8.sudoku;

import static com.android.paris8.sudoku.GameView.getColumn;
import static com.android.paris8.sudoku.GameView.getLine;
import static com.android.paris8.sudoku.GameView.matrice;

public class Checker {


    public Checker() {

    }

    public boolean checkCaseWrittenLine()
    {
        int[] valeur = new int[9];

        for(int i=0;i<9;i++){

            valeur[i] = matrice[getLine()][i];

        }


        if (getOccurence(valeur) == true) return false;

        return true;
    }

    public boolean checkCaseWrittenColumn()
    {
        int[] valeur = new int[9];

        for(int i=0;i<9;i++){

            valeur[i] = matrice[i][getColumn()];

        }

        if (getOccurence(valeur) == true) return false;


        return true;
    }

    public boolean checkCaseWrittenBlock()
    {
        int[] valeur = new int[9];
        int line = getLine();
        int column = getColumn();
        int k = 0;

        if (line < 3) line = 0;
        else if (line < 6) line = 3;
        else  line = 6;

        if (column < 3) column = 0;
        else if (column < 6) column = 3;
        else  column = 6;


        for(int i=0;i<3;i++){
            for (int j=0; j<3; j++) {

                valeur[k] = matrice[line + i][column + j];
                k++;

            }
        }


        if (getOccurence(valeur) == true) return false;

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

        int[] valeur = new int[9];
        int resultat = 0;

        for(int i=0;i<9;i++){
            resultat = 0;
            for(int j=0;j<9;j++){
                valeur[j] = matrice[3*(i%3)+j%3][3*(i/3)+j/3];
                resultat += valeur[j];


            }


            if (getOccurence(valeur) == true || resultat != 45) return false;

        }
        return true;
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

            if (checkLine() == true && checkColumn() == true && checkBlock() == true)
                return true;
        }

        return false;
    }

} 
