package com.android.paris8.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    Checker checker;

    public static int[][] matrice;
    private static boolean[][] isSelected;
    int val = 0;
    boolean clickCase = false;

    private Paint contour;
    private Paint bigContour;
    private Paint text;
    private Paint cell;
    private Paint selectCaseContour;
    private Paint selectCaseBackground;
    private Paint selectBlockBackground;
    private Paint errorBackground;
    private Paint caseBlocked;

    private int hauteur = 50, largeur = 50;
    private int cases;
    private static int line = 0;
    private static int column = 0;

    private boolean in = true;
    Thread mThread;
    SurfaceHolder mSurfaceHolder;

    final int[][] level= {
            {0,3,7,0,6,1,2,9,0},
            {9,2,5,4,3,0,0,6,1},
            {0,0,0,0,0,0,7,5,0},
            {0,5,0,0,4,6,9,0,0},
            {3,0,9,0,0,0,6,0,2},
            {0,0,4,2,9,0,0,7,0},
            {0,9,2,0,0,0,0,0,0},
            {5,1,0,0,7,9,4,2,6},
            {0,6,8,3,2,0,5,1,0}
    };

    public GameView(Context context) {

        super(context);
        init();
        mThread = new Thread(this);
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        mThread = new Thread(this);
        setFocusable(true);

    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mThread = new Thread(this);
        setFocusable(true);
    }




    Paint backgroundVert;

    private void init() {

        backgroundVert = new Paint();
        backgroundVert.setColor(Color.GREEN);


        checker = new Checker();

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
        cell = new Paint();
        cell.setColor(Color.GRAY);
        cell.setTextSize(70);
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


        if ((mThread != null) && (!mThread.isAlive())) {
            mThread.start();
            Log.e("-FCT-", "cv_thread.start()");
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

        if (checker.win() == true) {
            canvas.drawRect(0, 0, getWidth(), getWidth(), backgroundVert);
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


                    // Ranger 1
                    if (i < 3 && j < 3) {
                        canvas.drawRect(0, 0, cases * 3, cases * 3, selectBlockBackground);
                    }

                    if (i < 3 && (j >= 3 && j < 6)) {
                        canvas.drawRect(0, 3 * cases, cases * 3, 3 * cases * 2, selectBlockBackground);
                    }

                    if (i < 3 && (j >= 6 && j < 9)) {
                        canvas.drawRect(0, 6 * cases, cases * 3, 3 * cases * 3, selectBlockBackground);
                    }

                    // Ranger 2

                    if ((i >= 3 && i < 6) && j < 3) {
                        canvas.drawRect(3 * cases, 0, 3 * cases * 2, cases * 3, selectBlockBackground);
                    }

                    if ((i >= 3 && i < 6) && (j >= 3 && j < 6)) {
                        canvas.drawRect(3 * cases, 3 * cases, 3 * cases * 2, 3 * cases * 2, selectBlockBackground);
                    }

                    if ((i >= 3 && i < 6) && (j >= 6 && j < 9)) {
                        canvas.drawRect(3 * cases, 6 * cases, 3 * cases * 2, 3 * cases * 3, selectBlockBackground);
                    }

                    // Ranger 3

                    if ((i >= 6 && i < 9) && j < 3) {
                        canvas.drawRect(6 * cases, 0, 6 * cases * 2, cases * 3, selectBlockBackground);
                    }

                    if ((i >= 6 && i < 9) && (j >= 3 && j < 6)) {
                        canvas.drawRect(6 * cases, 3 * cases, 6 * cases * 2, 3 * cases * 2, selectBlockBackground);
                    }

                    if ((i >= 6 && i < 9) && (j >= 6 && j < 9)) {
                        canvas.drawRect(6 * cases, 6 * cases, 6 * cases * 2, 3 * cases * 3, selectBlockBackground);
                    }


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

                // canvas.drawText(res, i * cases + cases / 4, j * cases + cases * 4 / 5, text); text size = 100
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


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("-> FCT <-", "surfaceCreated");
        //mThread.keepDrawing = true;
        //mThread.start();
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


        checker.checkLine();

        checker.checkCaseWrittenLine();
        checker.checkCaseWrittenColumn();


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


    @Override
    public void run() {

        Canvas c = null;
        while (in) {
            try {
                mThread.sleep(40);
                try {
                    c = mSurfaceHolder.lockCanvas(null);
                    //if (c!=null)
                    //nDraw(c);
                } finally {
                    if (c != null) {
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("-> RUN <-", "PB DANS RUN");
            }
        }
    }

}


