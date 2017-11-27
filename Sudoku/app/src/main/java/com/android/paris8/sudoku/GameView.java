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
import android.widget.Toast;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    private static int[][] matrice;
    int val = 9;

    private Paint contour;
    private Paint bigContour;
    private Paint text;
    private Paint cell;

    private int hauteur = 50, largeur = 50;
    private int cases;

    private boolean in = true;
    Thread mThread;
    SurfaceHolder mSurfaceHolder;

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

        contour = new Paint();
        contour.setColor(Color.BLACK);
        contour.setStrokeWidth(3);
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

        if ((mThread != null) && (!mThread.isAlive())) {
            mThread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {


        paintGrid(canvas);

            canvas.drawText(matrice[x][y] + "", cases * 2 / 8, cases - cases / 6, text);



        Log.d("Yassine", "onDraw");

    }

    private void paintGrid(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(Color.WHITE);
        canvas.drawRect(0, 0, getWidth(), getWidth(), background);

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


            /*if (val == true)
            {
                canvas.drawText("0", cases / 5, cases * 4 / 5, text);
            }*/
        }


    }

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    /*private void nDraw(Canvas canvas) {

        paintGrid(canvas);

        if (val == true) {
            canvas.drawText("0", cases / 5, cases * 4 / 5, text);
        }


        Log.d("Yassine", "Erreur");
        Log.d("Yassine", "nDraw");

    }*/


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
        /*mThread.keepDrawing = false;

        boolean joined = false;
        while (!joined) {
            try {
                mThread.join();
                joined = true;
            } catch (InterruptedException e) {}
        }*/
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

        matrice[x][y] = val;

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


