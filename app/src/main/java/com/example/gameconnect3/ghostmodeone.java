package com.example.gameconnect3;

import android.graphics.Color;
import android.media.Image;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gameconnect3.R;

public class ghostmodeone extends AppCompatActivity {

    // 0: yellow, 1: red, 2: empty

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0,ct = 0;

    boolean gameActive = true;

    ImageView counter;

    public void dropIn(View view) {
        Log.i("infoTag", "GHOST message");

        Log.i("ADebugTag", "Value in GHOST = " + Integer.toString(ct));

        counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive)
        {
            ct++;
            //      Toast.makeText(getApplicationContext(),"CT = "+ct,Toast.LENGTH_SHORT).show();

            gameState[tappedCounter] = activePlayer;
            Log.i("infoTag", "TAG ------ Set = "+Integer.toString(tappedCounter));
            //      Toast.makeText(getApplicationContext(),"Game state set to = "+gameState[tappedCounter],Toast.LENGTH_SHORT).show();

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;

            } else {

                counter.setImageResource(R.drawable.red);

                activePlayer = 0;

            }

            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            counter.setVisibility(View.VISIBLE);
            new CountDownTimer(300, 300) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    counter.setVisibility(View.INVISIBLE);
                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    // Somone has won!

                    gameActive = false;
                    GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
                    for(int i=0; i<gridLayout.getChildCount(); i++) {
                        ImageView ct = (ImageView) gridLayout.getChildAt(i);
                        ct.setVisibility(View.VISIBLE);
                    }

                    String winner = "";

                    if (activePlayer == 1) {

                        winner = "Yellow";

                    } else {

                        winner = "Red";

                    }
                    ct = 0;

                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    if(winner == "Red") {
                        winnerTextView.setTextColor(Color.RED);
                        winnerTextView.setBackgroundColor(Color.parseColor("#ffffff")); }
                    else if(winner == "Yellow") {
                        winnerTextView.setTextColor(Color.parseColor("#ffff00"));
                        winnerTextView.setBackgroundColor(Color.parseColor("#000000"));
                    }
                    winnerTextView.setText(winner + " has won!");

                    playAgainButton.setVisibility(View.VISIBLE);

                    winnerTextView.setVisibility(View.VISIBLE);

                }

            }
            if (ct >= 9) {
                gameActive = false;
                GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
                for(int i=0; i<gridLayout.getChildCount(); i++) {
                    ImageView ct = (ImageView) gridLayout.getChildAt(i);
                    ct.setVisibility(View.VISIBLE);
                }
                String winner = "Its a DRAW ! ";


                Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                winnerTextView.setTextColor(Color.parseColor("#000000"));
                winnerTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                winnerTextView.setGravity(1);
                winnerTextView.setText(winner + " Better continue");


                ct = 0;

                playAgainButton.setVisibility(View.VISIBLE);

                winnerTextView.setVisibility(View.VISIBLE);
            }
        }

    }

    public void playAgain(View view) {

        ct = 0;

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for (int i=0; i<gameState.length; i++) {

            gameState[i] = 2;

        }

        activePlayer = 0;

        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghostmodeone);
        Toast.makeText(this,"MODE HINT :- \n Remember your move as well as opponent's move to succeed in winning ! Better aim for a draw :) ",Toast.LENGTH_LONG).show();
    }
}
