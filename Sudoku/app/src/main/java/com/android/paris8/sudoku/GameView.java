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

public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    private static int[][] matrice;

    private Paint contour;
    private Paint bigContour;
    private Paint text;
    private Paint cell;

    boolean val = false;

    private int hauteur = 50, largeur = 50;
    private int cases;

    private boolean in = true;
    DrawingThread mThread;
    SurfaceHolder mSurfaceHolder;

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




    /*public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        initparameters();

        // creation du thread
        cv_thread = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);
    }*/


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
        text.setTextSize(70);

        mThread = new DrawingThread();

    }


    // initialisation du jeu
    public void initparameters() {
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
        text.setTextSize(70);



        /*if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }*/
        //paintGrid(canvas);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        paintGrid(canvas);

        if (val == true) {
            canvas.drawText("0", cases / 5, cases * 4 / 5, text);
        }


        Log.d("Yassine", "Erreur");

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

        Log.d("Yassine", "Erreur");
    }

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {

        paintGrid(canvas);
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


    // fonction permettant de recuperer les evenements tactiles
    public boolean onTouchEvent(MotionEvent event) {
        Canvas c = null;
        Log.i("-> FCT <-", "onTouchEvent: " + event.getX());
        //float x = event.getX();
        //float y = event.getY();
        int x = (int) event.getX() / cases;
        int y = (int) event.getY() / cases;
        Toast.makeText(getContext(), "x = " + x + " y = " + y, Toast.LENGTH_SHORT).show();

        if (x == 0 && y == 0) {
            val = true;
        }


        return super.onTouchEvent(event);
    }


    private class DrawingThread extends Thread {
        // Utilisé pour arrêter le dessin quand il le faut
        boolean keepDrawing = true;

        @Override
        public void run() {

            while (keepDrawing) {
                Canvas canvas = null;

                try {
                    // On récupère le canvas pour dessiner dessus
                    canvas = mSurfaceHolder.lockCanvas();
                    // On s'assure qu'aucun autre thread n'accède au holder
                    synchronized (mSurfaceHolder) {
                        // Et on dessine
                        onDraw(canvas);
                    }
                } finally {
                    // Notre dessin fini, on relâche le Canvas pour que le dessin s'affiche
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                // Pour dessiner à 50 fps
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}


