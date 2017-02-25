package hu.cai.ait.tictactoe.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hu.cai.ait.tictactoe.MainActivity;
import hu.cai.ait.tictactoe.TicTacToeModel;

/**
 * Created by caiglencross on 2/20/17.
 * you bitch you ;)
 */

public class TicTacToeView extends View {

    private Paint paintBg;
    private Paint paintLine;

    private int xColor= Color.RED;
    private int oColor= Color.BLUE;
    private int gridColor = Color.WHITE;

    private boolean gameHasStarted=false;


    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setStyle(Paint.Style.FILL);



        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);




    }

    private void drawGameBoard(Canvas canvas) {
        //setPaintColor
        paintLine.setColor(gridColor);
        // border
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(), getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0, getWidth() / 3, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0, 2 * getWidth() / 3, getHeight(),
                paintLine);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,0, getWidth(), getHeight(), paintBg);

        drawGameBoard(canvas);

        drawPlayers(canvas);

    }

    private void drawPlayers(Canvas canvas){

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 8;

                    paintLine.setColor(oColor);
                    canvas.drawCircle(centerX, centerY, radius, paintLine);

                } else if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CROSS) {
                    int bufferDist = 50;
                    paintLine.setColor(xColor);
                    canvas.drawLine((i * getWidth() / 3) +bufferDist, (j * getHeight() / 3)+bufferDist,
                            (i + 1) * getWidth() / 3 -bufferDist,
                            (j + 1) * getHeight() / 3 -bufferDist, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 3 -bufferDist, j * getHeight() / 3 +bufferDist,
                            i * getWidth() / 3 +bufferDist, (j + 1) * getHeight() / 3 -bufferDist, paintLine);
                }
            }
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
        if (gameHasStarted) {
            int tx = (int) event.getX() / (getWidth() / 3);
            int ty = (int) event.getY() / (getHeight() / 3);

            if (TicTacToeModel.getInstance().getFieldContent(tx, ty) == TicTacToeModel.EMPTY
                    && TicTacToeModel.getInstance().checkWinner() == 0) {
                TicTacToeModel.getInstance().makeMove(tx, ty,
                        TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();
                ((MainActivity) getContext()).switchTimers(TicTacToeModel.getInstance().getNextPlayer());

            }


            if (TicTacToeModel.getInstance().checkWinner() == 0) {
                String playerUp = (TicTacToeModel.getInstance().getNextPlayer() == 1) ? "Circle" : "Cross";
                ((MainActivity) getContext()).setPlayerText("Next player is " + playerUp);
            } else {
                short winner = TicTacToeModel.getInstance().checkWinner();
                if (winner == 1) {
                    ((MainActivity) getContext()).setPlayerText("Circle Wins!!!");
                } else if (winner == 2) {
                    ((MainActivity) getContext()).setPlayerText("Cross Wins!!!");
                } else {
                    ((MainActivity) getContext()).setPlayerText("Cat Wins :(");
                }
                ((MainActivity) getContext()).stopTimers();


            }
            invalidate();
        }else{
            gameHasStarted = true;
            ((MainActivity) getContext()).setPlayerText("Next Player is Circle");
            ((MainActivity) getContext()).switchTimers(TicTacToeModel.CIRCLE);


        }
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }

    public void resetGame(){
        TicTacToeModel.getInstance().resetModel();
        ((MainActivity) getContext()).setPlayerText("Touch Board to Start Game");
        gameHasStarted=false;
        invalidate();
    }


}
