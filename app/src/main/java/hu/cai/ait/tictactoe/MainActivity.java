package hu.cai.ait.tictactoe;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import hu.cai.ait.tictactoe.View.TicTacToeView;


public class MainActivity extends AppCompatActivity {

    private TextView tvPlayer;
    private Chronometer crossTimer;
    private Chronometer circleTimer;
    private long crossTimeWhenStopped = 0;
    private long circleTimeWhenStopped = 0;
    private boolean crossFirstTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TicTacToeView gameView = (TicTacToeView) findViewById(R.id.gameView);
        tvPlayer = (TextView) findViewById(R.id.tvPlayer);


        Button btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                gameView.resetGame();
                resetClocks();

            }
        });

        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) findViewById(
                R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();

        circleTimer = (Chronometer) findViewById(R.id.circleTimer);
        crossTimer = (Chronometer) findViewById(R.id.crossTimer);

    }

        public void setPlayerText(String text){
        tvPlayer.setText(text);
    }

        public void switchTimers(short nextPlayer){
            if (nextPlayer==1){
                circleTimer.setBase(SystemClock.elapsedRealtime()+circleTimeWhenStopped);
                circleTimer.start();
                if(!crossFirstTurn) {
                    crossTimeWhenStopped = crossTimer.getBase() - SystemClock.elapsedRealtime();
                    crossTimer.stop();
                }else {
                    crossFirstTurn = false;
                }
            }else{
                crossTimer.setBase(SystemClock.elapsedRealtime()+crossTimeWhenStopped);
                crossTimer.start();
                circleTimeWhenStopped = circleTimer.getBase() - SystemClock.elapsedRealtime();
                circleTimer.stop();
            }
        }

        public void resetClocks(){
            circleTimer.stop();
            circleTimer.setBase(SystemClock.elapsedRealtime());
            circleTimeWhenStopped = 0;

            crossTimer.stop();
            crossTimer.setBase(SystemClock.elapsedRealtime());
            crossTimeWhenStopped = 0;
        }

        public void stopTimers(){
            circleTimer.stop();
            crossTimer.stop();
        }




}
