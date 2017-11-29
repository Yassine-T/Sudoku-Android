package com.android.paris8.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {



    private static int[][] matrice;
    private static boolean[][] isSelected;
    int val = 0;

    private Paint contour;
    private Paint bigContour;
    private Paint text;
    private Paint cell;
    private Paint selectCaseContour;
    private Paint selectCaseBackground;
    private Paint selectBlocBackground;
    Paint caseBloque;

    private int hauteur = 50, largeur = 50;
    private int cases;

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





    private void init() {


        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        matrice = new int[9][9];
        isSelected = new boolean[9][9];

        contour = new Paint();
        contour.setColor(Color.BLACK);
        contour.setStrokeWidth(5);
        bigContour = new Paint();
        bigContour.setColor(Color.BLACK);
        bigContour.setStrokeWidth(10);
        cell = new Paint();
        cell.setColor(Color.GRAY);
        cell.setTextSize(70);
        text = new Paint();
        text.setColor(Color.BLACK);
        text.setTextSize(100);
        text.setFakeBoldText(true);
        caseBloque = new Paint();
        caseBloque.setColor(Color.GRAY);

        selectCaseContour = new Paint();
        selectCaseContour.setColor(Color.BLACK);
        selectCaseContour.setStrokeWidth(15);

        selectCaseBackground = new Paint();
        selectCaseBackground.setColor(Color.BLUE);
        selectCaseBackground.setStyle(Paint.Style.FILL);

        selectBlocBackground = new Paint();
        selectBlocBackground.setColor(Color.YELLOW);
        selectBlocBackground.setStyle(Paint.Style.FILL);
        selectBlocBackground.setAlpha(100);



        for(int i=0;i<9;i++){

            for(int j=0;j<9;j++){
                matrice[j][i]=level[j][i];
                //Log.d("LEVEL", matrice[i][j] + "");

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


        paintCaseBloquer(canvas);
        paintCaseSelected(canvas);
        paintNumber(canvas);

        paintGrid(canvas);



        Log.d("Yassine", "onDraw");

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


    private void paintCaseSelected(Canvas canvas) {


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (isSelected[j][i] == false) continue;

                canvas.drawRect(0, j * cases, getWidth(), j * cases + cases, selectBlocBackground);
                canvas.drawRect(i * cases, 0, i * cases + cases, getWidth(), selectBlocBackground);


                // Ranger 1
                if (i < 3 && j < 3) {
                     canvas.drawRect(0, 0, cases * 3, cases * 3, selectBlocBackground);
                }

                if (i < 3 && (j >= 3 && j < 6)) {
                    canvas.drawRect(0, 3 * cases, cases * 3, 3 * cases * 2, selectBlocBackground);
                }

                if (i < 3 && (j >= 6 && j < 9)) {
                    canvas.drawRect(0, 6 * cases, cases * 3, 3 * cases * 3, selectBlocBackground);
                }

                // Ranger 2

                if ((i >= 3 && i < 6) && j < 3) {
                    canvas.drawRect(3 * cases, 0, 3 * cases * 2, cases * 3, selectBlocBackground);
                }

                if ((i >= 3 && i < 6) && (j >= 3 && j < 6)) {
                    canvas.drawRect(3 * cases, 3 * cases, 3 * cases * 2, 3 * cases * 2, selectBlocBackground);
                }

                if ((i >= 3 && i < 6) && (j >= 6 && j < 9)) {
                    canvas.drawRect(3 * cases, 6 * cases, 3 * cases * 2, 3 * cases * 3, selectBlocBackground);
                }

                // Ranger 3

                if ((i >= 6 && i < 9) && j < 3) {
                    canvas.drawRect(6 * cases, 0, 6 * cases * 2, cases * 3, selectBlocBackground);
                }

                if ((i >= 6 && i < 9) && (j >= 3 && j < 6)) {
                    canvas.drawRect(6 * cases, 3 * cases, 6 * cases * 2, 3 * cases * 2, selectBlocBackground);
                }

                if ((i >= 6 && i < 9) && (j >= 6 && j < 9)) {
                    canvas.drawRect(6 * cases, 6 * cases, 6 * cases * 2, 3 * cases * 3, selectBlocBackground);
                }


                    canvas.drawRect(i * cases, j * cases, i * cases + cases, j * cases + cases, selectCaseBackground);
                    canvas.drawLine(i * cases + cases, j * cases, i * cases + cases, j * cases + cases, selectCaseContour); // Right
                    canvas.drawLine(i * cases, j * cases + cases, i * cases + cases, j * cases + cases, selectCaseContour); // Bottom
                    canvas.drawLine(i * cases, j * cases, i * cases, j * cases + cases, selectCaseContour); // Left
                    canvas.drawLine(i * cases, j * cases, i * cases + cases, j * cases, selectCaseContour); // Top

                }
            }

    }

    private void paintNumber(Canvas canvas) {


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrice[j][i] == 0) continue;
                String res = String.valueOf(matrice[j][i]);

                canvas.drawText(res, i * cases + cases / 4, j * cases + cases * 4 / 5, text);


            }
        }
    }

    private void paintCaseBloquer(Canvas canvas) {


        for(int j=0;j<9;j++){
            for(int k=0;k<9;k++){

                if (level[j][k] != 0)
                {
                    canvas.drawRect(k * cases, j * cases, k * cases + cases, j * cases + cases, caseBloque);
                }
            }
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
        Canvas c = null;
        Log.i("-> FCT <-", "onTouchEvent: " + event.getX());
        //float x = event.getX();
        //float y = event.getY();
        x = (int) event.getX() / cases;
        y = (int) event.getY() / cases;
        Toast.makeText(getContext(), "x = " + x + " y = " + y, Toast.LENGTH_SHORT).show();

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
        }

        for(int j=0;j<9;j++){
            for(int k=0;k<9;k++){

                Log.d("Case", isSelected[k][j] + "");
            }
        }

        invalidate();

        return super.onTouchEvent(event);
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


