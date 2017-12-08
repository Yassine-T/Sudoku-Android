package com.android.paris8.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    Checker checker;
    Level niveau;
    SaveGame saveGame;



    static int typeLevel;

    public static int[][] matrice;
    private static boolean[][] isSelected;
    int val;
    boolean clickCase = false;

    Paint backgroundVert;
    private Paint contour;
    private Paint bigContour;
    private Paint text;
    private Paint selectCaseContour;
    private Paint selectCaseBackground;
    private Paint selectBlockBackground;
    private Paint errorBackground;
    private Paint caseBlocked;

    private int hauteur = 50, largeur = 50;
    private int cases;
    private static int line;
    private static int column;

    SurfaceHolder mSurfaceHolder;

    int[][] level;

    int count;
    boolean bDialog;
    TextView tv_level;
    private String score;

    boolean isDialog = false;

    String keyBestScoreEasyMinutes = "com.android.paris8.sudoku.KEYBESTSCOREEASYMINUTES";
    String keyBestScoreEasySecondes = "com.android.paris8.sudoku.KEYBESTSCOREEASYSECONDES";
    String strBestScoreEasy;
    String KeyBestScoreEasy = "com.android.paris8.sudoku.KEYBESTSCOREEASY";

    String keyBestScoreMediumMinutes = "com.android.paris8.sudoku.KEYBESTSCOREMEDIUMMINUTES";
    String keyBestScoreMediumSecondes = "com.android.paris8.sudoku.KEYBESTSCOREMEDIUMSECONDES";
    String strBestScoreMedium;
    String KeyBestScoreMedium = "com.android.paris8.sudoku.KEYBESTSCOREMEDIUM";

    String keyBestScoreHardMinutes = "com.android.paris8.sudoku.KEYBESTSCOREHARDMINUTES";
    String keyBestScoreHardSecondes = "com.android.paris8.sudoku.KEYBESTSCOREHARDSECONDES";
    String strBestScoreHard;
    String KeyBestScoreHard = "com.android.paris8.sudoku.KEYBESTSCOREHARD";

    public static final String PREF_FILE_NAME = "PrefFile";

    SharedPreferences sharedPref;

    int secondes;
    int minutes;

    TextView tv_best_score;
    TextView tv_score;


    public GameView(Context context) {

        super(context);
        init();
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setFocusable(true);

    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setFocusable(true);
    }






    private void init() {

        sharedPref = getContext().getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);

        backgroundVert = new Paint();
        backgroundVert.setColor(Color.GREEN);


        checker = new Checker();
        niveau = new Level();
        saveGame = new SaveGame(getContext());

        line = 0;
        column = 0;
        val = 0;
        count = 0;
        bDialog = false;

        if (typeLevel == 0) level = saveGame.getMatrice();
        else if (typeLevel == 1)
            level = niveau.selectEasyLevel();
        else if (typeLevel == 2)
            level = niveau.selectMediumLevel();
        else level = niveau.selecthardLevel();

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        matrice = new int[9][9];
        isSelected = new boolean[9][9];

        contour = new Paint();
        contour.setColor(Color.BLACK);
        contour.setStrokeWidth(2);
        bigContour = new Paint();
        bigContour.setColor(Color.BLACK);
        bigContour.setStrokeWidth(8);
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(85);
        //text.setFakeBoldText(true);

        // Gris
        caseBlocked = new Paint();
        caseBlocked.setColor(Color.parseColor("#CFCFCF"));

        selectCaseContour = new Paint();
        selectCaseContour.setColor(Color.BLACK);
        selectCaseContour.setStrokeWidth(12);

        // Bleu
        selectCaseBackground = new Paint();
        selectCaseBackground.setColor(Color.parseColor("#B3E5FC"));
        selectCaseBackground.setStyle(Paint.Style.FILL);

        // Jaune
        selectBlockBackground = new Paint();
        selectBlockBackground.setColor(Color.parseColor("#FFFF00"));
        selectBlockBackground.setStyle(Paint.Style.FILL);
        selectBlockBackground.setAlpha(50);

        // Rouge
        errorBackground = new Paint();
        errorBackground.setColor(Color.parseColor("#F44336"));
        errorBackground.setStyle(Paint.Style.FILL);



        for(int i=0;i<9;i++){

            for(int j=0;j<9;j++){
                matrice[i][j]=level[i][j];

            }

        }

    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint background = new Paint();
        background.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getWidth(), background);




        paintCaseBlocked(canvas);


        paintCaseSelected(canvas);

        paintLineError(canvas);
        paintColumnError(canvas);
        paintBlockError(canvas);

        if (checker.win() == true)
        {
            canvas.drawRect(0, 0, getWidth(), getWidth(), backgroundVert);
            saveGame.setStateGameSaved(false);
            if (bDialog == false) {
                dialogScore(getContext());
                bDialog = true;
            }
        }

        paintContourCaseSelected(canvas);
        paintNumber(canvas);

        paintGrid(canvas);

    }

    private void paintGrid(Canvas canvas) {



        hauteur = this.getMeasuredHeight();
        largeur = this.getMeasuredWidth();
        hauteur = largeur;
        this.setMinimumHeight(largeur);


        cases = largeur / 9;
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                canvas.drawLine(i * cases, 0, i * cases, largeur, bigContour);
                canvas.drawLine(0, i * cases, largeur, i * cases, bigContour);
            } else {
                canvas.drawLine(i * cases, 0, i * cases, largeur, contour);
                canvas.drawLine(0, i * cases, largeur, i * cases, contour);
            }

        }


    }


    private void paintContourCaseSelected(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (isSelected[j][i] == false) continue;

                canvas.drawLine(i * cases + cases - cases / 18, j * cases, i * cases + cases - cases / 18, j * cases + cases, selectCaseContour); // Right
                canvas.drawLine(i * cases, j * cases + cases - cases / 18, i * cases + cases, j * cases + cases - cases / 18, selectCaseContour); // Bottom
                canvas.drawLine(i * cases + cases / 16, j * cases, i * cases + cases / 16, j * cases + cases, selectCaseContour); // Left
                canvas.drawLine(i * cases, j * cases + cases / 16, i * cases + cases, j * cases + cases / 16, selectCaseContour); // Top

            }
        }
    }

    private void paintCaseSelected(Canvas canvas) {



        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                if (isSelected[j][i] == false) {
                    if (clickCase == true)
                    {
                        if (matrice[j][i] == getValCaseSelected() && getValCaseSelected() != 0)
                            canvas.drawRect(i * cases, j * cases, i * cases + cases, j * cases + cases, selectCaseBackground);
                    } else {
                        if (matrice[j][i] == val && val != 0)
                            canvas.drawRect(i * cases, j * cases, i * cases + cases, j * cases + cases, selectCaseBackground);
                    }

                } else {

                    canvas.drawRect(0, j * cases, getWidth(), j * cases + cases, selectBlockBackground);
                    canvas.drawRect(i * cases, 0, i * cases + cases, getWidth(), selectBlockBackground);


                    int l = getLine();
                    int c = getColumn();
                    if (l < 3) l = 0;
                    else if (l < 6) l = 3;
                    else  l = 6;

                    if (c < 3) c = 0;
                    else if (c < 6) c = 3;
                    else  c = 6;

                    canvas.drawRect(c * cases, l * cases, c * cases + cases * 3, l * cases + cases * 3, selectBlockBackground);



                    if (matrice[j][i] == getValCaseSelected() && getValCaseSelected() != 0 && clickCase == true) {
                        canvas.drawRect(i * cases, j * cases, i * cases + cases, j * cases + cases, selectCaseBackground);

                    }


                }
            }
        }
    }

    private void paintNumber(Canvas canvas) {


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrice[j][i] == 0) continue;
                String res = String.valueOf(matrice[j][i]);

                canvas.drawText(res, i * cases + cases * 2 / 7, j * cases + cases * 3 / 4, text);


            }
        }
    }

    private void paintCaseBlocked(Canvas canvas) {


        for(int j=0;j<9;j++){
            for(int k=0;k<9;k++){

                if (level[j][k] != 0)
                {
                    canvas.drawRect(k * cases, j * cases, k * cases + cases, j * cases + cases, caseBlocked);
                }
            }
        }
    }


    private void paintLineError(Canvas canvas) {

        if (checker.checkCaseWrittenLine() == false)
        {
            canvas.drawRect(0, getLine() * cases, getWidth(), getLine() * cases + cases, errorBackground);

        }
    }

    private void paintColumnError(Canvas canvas) {

        if (checker.checkCaseWrittenColumn() == false)
        {
            canvas.drawRect(getColumn() * cases, 0, getColumn() * cases + cases, getWidth(), errorBackground);
        }
    }

    private void paintBlockError(Canvas canvas) {

        if (checker.checkCaseWrittenBlock() == false)
        {

            int l = getLine();
            int c = getColumn();
            if (l < 3) l = 0;
            else if (l < 6) l = 3;
            else  l = 6;

            if (c < 3) c = 0;
            else if (c < 6) c = 3;
            else  c = 6;

            canvas.drawRect(c * cases, l * cases, c * cases + cases * 3, l * cases + cases * 3, errorBackground);

        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceDestroyed");

    }



    int x, y;
    // fonction permettant de recuperer les evenements tactiles
    public boolean onTouchEvent(MotionEvent event) {

        Log.i("-> FCT <-", "onTouchEvent: " + event.getX());
        x = (int) event.getX() / cases;
        y = (int) event.getY() / cases;
        Log.d("CoordMatrice", "x = " + x + " y = " + y);

        for(int j=0;j<9;j++){
            for(int k=0;k<9;k++){

                isSelected[k][j] = false;

            }
        }

        if (event.getY() <= largeur)
        {
            isSelected[y][x] = true;
            if (level[y][x] == 0)
                matrice[y][x] = val;

            getValCaseSelected();
            line = y;
            column = x;
            clickCase = true;
        }

        if (((Game)getContext()).stateSound) ((Game)getContext()).buttonSound.start();

        invalidate();

        return super.onTouchEvent(event);
    }

    public static int getValCaseSelected()
    {
        int valeur = 0;
        for(int j=0;j<9;j++){
            for(int k=0;k<9;k++){

                if (isSelected[k][j] == true)
                {
                    valeur = matrice[k][j];
                }

            }
        }


        return valeur;
    }

    public static int getLine()
    {
        return line;
    }

    public static int getColumn()
    {
        return column;
    }



    public void dialogScore(Context context)
    {

        
        secondes = ((Game)getContext()).chrono.getSecondes();
        minutes = ((Game)getContext()).chrono.getMinutes();
        score = minutes + " : " + secondes;
        if (minutes < 10 && secondes < 10)
        {
            score = "0" + minutes + " : 0" + secondes;
        }
        if (minutes < 10 )
        {
            score = "0" + minutes + " : " + secondes;
        }

        if (secondes < 10 )
        {
            score = minutes + " : 0" + secondes;
        }




        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_score);
        dialog.setTitle("Partie Terminée");
        tv_score = (TextView) dialog.findViewById(R.id.tv_score);
        tv_score.setText("Score :    " + score);

        tv_level = (TextView) dialog.findViewById(R.id.tv_level);
        tv_best_score = (TextView) dialog.findViewById(R.id.tv_best_score);


        Button btn_main = (Button) dialog.findViewById(R.id.btn_main);


        if (typeLevel == 0)
        {

            if (saveGame.getSaveLevelName() == ((Game)getContext()).facile)
            {
                dialogFacile();
            } else if (saveGame.getSaveLevelName() == ((Game)getContext()).moyen) {
                dialogMoyen();
            } else {
                dialogDifficile();
            }
        }
        if (typeLevel == 1)
        {
            dialogFacile();
        }


        if (typeLevel == 2)
        {
            dialogMoyen();
        }


        if (typeLevel == 3)
        {
            dialogDifficile();
        }


        btn_main.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((Game)getContext()).stateSound) ((Game)getContext()).buttonSound.start();

                Intent i = new Intent(getContext(), MainActivity.class);
                getContext().startActivity(i);
                ((Game) getContext()).finish();
            }
        });


        isDialog = true;

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);


    }



    public void dialogFacile()
    {
        tv_level.setText("Niveau Facile");

        strBestScoreEasy = sharedPref.getString(KeyBestScoreEasy, "00 : 00");
        int bestScoreSecondes = sharedPref.getInt(keyBestScoreEasySecondes, 0);
        int bestScoreMinutes = sharedPref.getInt(keyBestScoreEasyMinutes, 0);



        if (bestScoreSecondes == 0 && bestScoreMinutes == 0)
        {

            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;

            strBestScoreEasy = score;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreEasy, strBestScoreEasy);
            editor.commit();
            editor.putInt(keyBestScoreEasySecondes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreEasyMinutes, bestScoreMinutes);
            editor.commit();
        }


        if (minutes < bestScoreMinutes || (secondes < bestScoreSecondes && minutes == bestScoreMinutes)) {

            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;
            if (bestScoreMinutes < 10 )
            {
                score = "0" + minutes + " : " + secondes;
            }
            strBestScoreEasy = score;
            tv_score.setText("Nouveau Record : " + strBestScoreEasy);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreEasy, strBestScoreEasy);
            editor.commit();

            editor.putInt(keyBestScoreEasySecondes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreEasyMinutes, bestScoreMinutes);
            editor.commit();
        }


        strBestScoreEasy = sharedPref.getString(KeyBestScoreEasy, "00 : 00");
        tv_best_score.setText("Meilleur Score :    " + sharedPref.getString(KeyBestScoreEasy, "00 : 00"));

    }

    public void dialogMoyen()
    {
        tv_level.setText("Niveau Moyen");

        strBestScoreMedium = sharedPref.getString(KeyBestScoreMedium, "00 : 00");
        int bestScoreSecondes = sharedPref.getInt(keyBestScoreMediumSecondes, 0);
        int bestScoreMinutes = sharedPref.getInt(keyBestScoreMediumMinutes, 0);



        if (bestScoreSecondes == 0 && bestScoreMinutes == 0)
        {

            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;
            strBestScoreMedium = score;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreMedium, strBestScoreMedium);
            editor.commit();
            editor.putInt(keyBestScoreEasySecondes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreMediumMinutes, bestScoreMinutes);
            editor.commit();

        }


        if (minutes < bestScoreMinutes || (secondes < bestScoreSecondes && minutes == bestScoreMinutes)) {
            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;
            strBestScoreMedium = score;
            tv_score.setText("Nouveau Record :   " + strBestScoreMedium);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreMedium, strBestScoreMedium);
            editor.commit();

            editor.putInt(keyBestScoreMediumSecondes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreMediumMinutes, bestScoreMinutes);
            editor.commit();

        }


        strBestScoreMedium = sharedPref.getString(KeyBestScoreMedium, "00 : 00");
        tv_best_score.setText("Meilleur Score :    " + sharedPref.getString(KeyBestScoreMedium, "00 : 00"));

    }

    public void dialogDifficile()
    {
        tv_level.setText("Niveau Difficile");

        strBestScoreHard = sharedPref.getString(KeyBestScoreHard, "00 : 00");
        int bestScoreSecondes = sharedPref.getInt(keyBestScoreHardSecondes, 0);
        int bestScoreMinutes = sharedPref.getInt(keyBestScoreHardMinutes, 0);



        if (bestScoreSecondes == 0 && bestScoreMinutes == 0)
        {

            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;
            strBestScoreHard= score;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreHard, strBestScoreHard);
            editor.commit();
            editor.putInt(keyBestScoreHardSecondes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreHardMinutes, bestScoreMinutes);
            editor.commit();

        }


        if (minutes < bestScoreMinutes || (secondes < bestScoreSecondes && minutes == bestScoreMinutes)) {
            bestScoreSecondes = secondes;
            bestScoreMinutes = minutes;
            strBestScoreHard= score;
            tv_score.setText("Nouveau Record :   " + strBestScoreHard);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KeyBestScoreHard, strBestScoreHard);
            editor.commit();

            editor.putInt(keyBestScoreHardMinutes, bestScoreSecondes);
            editor.commit();
            editor.putInt(keyBestScoreHardMinutes, bestScoreMinutes);
            editor.commit();

        }


        strBestScoreMedium = sharedPref.getString(KeyBestScoreHard, "00 : 00");
        tv_best_score.setText("Meilleur Score :    " + sharedPref.getString(KeyBestScoreHard, "00 : 00"));

    }
}


