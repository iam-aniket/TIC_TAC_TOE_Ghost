package com.example.gameconnect3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//  android:background="#e5ff99"
import android.graphics.Color;
import android.media.Image;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gameconnect3.R;

public class Classicactivityghost extends AppCompatActivity {

    // 0: yellow, 1: red, 2: empty

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0, ct = 0;

    boolean gameActive = true;

    static Boolean isMovesLeft(int[] gameState) {
        for (int i = 0; i < 9; i++)
            if (gameState[i] == 2)
                return true;
        return false;
    }

    static int evaluate(int[] gameState) {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 9; row += 3) {
            if (gameState[row] == gameState[row + 1] && gameState[row + 1] == gameState[row + 2] && gameState[row] != 2) {
                if (gameState[row] == 1)
                    return +10;
                else if (gameState[row] == 0)
                    return -10;
            }
        }
        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++) {
            if (gameState[col] == gameState[col + 3] && gameState[col + 3] == gameState[col + 6] && gameState[col] != 2) {
                if (gameState[col] == 1)
                    return +10;
                else if (gameState[col] == 0)
                    return -10;
            }
        }
        // Checking for Diagonals for X or O victory.
        if (gameState[0] == gameState[4] && gameState[4] == gameState[8] && gameState[0] != 2) {
            if (gameState[0] == 1)
                return +10;
            else if (gameState[0] == 0)
                return -10;
        }
        if (gameState[2] == gameState[4] && gameState[4] == gameState[6] && gameState[2] != 2) {
            if (gameState[2] == 1)
                return +10;
            else if (gameState[2] == 0)
                return -10;
        }
        // Else if none of them have won then return 0
        return 0;
    }

    static int minimax(int[] gameState, int depth, Boolean isMax) {
        int score = evaluate(gameState);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft(gameState) == false)
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 9; i++) {
                if (gameState[i] == 2) {
                    gameState[i] = 1;
                    best = Math.max(best, minimax(gameState, depth + 1, !isMax));
                    gameState[i] = 2;
                }
            }
            return best;
        }

        // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 9; i++) {
                if (gameState[i] == 2) {
                    gameState[i] = 0;
                    best = Math.min(best, minimax(gameState, depth + 1, !isMax));
                    gameState[i] = 2;
                }
            }
            return best;
        }
    }

    static int findBestMove(int[] gameState) {
        int bestVal = -1000;
        int pos = -1;

        // Traverse all cells, evaluate minimax function
        // for all empty cells. And return the cell
        // with optimal value.
        for (int i = 0; i < 9; i++) {
            if (gameState[i] == 2) {
                gameState[i] = 1;
                int moveVal = minimax(gameState, 0, false);
                gameState[i] = 2;
                if (moveVal > bestVal) {
                    pos = i;
                    bestVal = moveVal;
                }
            }
        }
        return pos;
    }

    public void dropIn(View view) {
        Log.i("myTag", "This is my message");
        Log.i("ADebugTag", "Value = " + Integer.toString(ct));

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (activePlayer == 0) {

            if (gameState[tappedCounter] == 2 && gameActive) {
                ct++;
                gameState[tappedCounter] = activePlayer;

                counter.setTranslationY(-1500);

                if (activePlayer == 0) {

                    counter.setImageResource(R.drawable.yellow);

                    activePlayer = 1;

                } else {

                    counter.setImageResource(R.drawable.red);

                    activePlayer = 0;

                }

                counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
                GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

                for (int[] winningPosition : winningPositions) {

                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                        // Somone has won!

                        gameActive = false;

                        String winner = "";

                        if (activePlayer == 1) {

                            winner = "You";

                        } else {

                            winner = "AI";

                        }

                        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                        if (winner == "AI") {
                            winnerTextView.setTextColor(Color.RED);

                            winnerTextView.setText(winner + " has defeated you !");
                            winnerTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                        } else if (winner == "You") {
                            winnerTextView.setTextColor(Color.parseColor("#ffff00"));
                            winnerTextView.setBackgroundColor(Color.parseColor("#000000"));

                            winnerTextView.setText(winner + " defeated AI !");
                        }

                        ct = 0;

                        playAgainButton.setVisibility(View.VISIBLE);

                        winnerTextView.setVisibility(View.VISIBLE);

                    }
                }
                if (ct == 9) {
                    gameActive = false;

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
        if (activePlayer == 1) {
            new CountDownTimer(1000, 1000) {
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    if (gameActive) {
                        int computerPos = findBestMove(gameState);
                        if (computerPos == -1) {
                            //Toast.makeText(this, "No position Left", Toast.LENGTH_SHORT).show();
                        } else {
                            ct++;
                            gameState[computerPos] = 1;
                            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
                            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                                if (i == computerPos) {
                                    ImageView counter1 = (ImageView) gridLayout.getChildAt(i);
                                    counter1.setTranslationY(-1500);

                                    counter1.setImageResource(R.drawable.red);

                                    activePlayer = 0;

                                    counter1.animate().translationYBy(1500).rotation(3600).setDuration(300);
                                }
                            }
                            for (int[] winningPosition : winningPositions) {

                                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                                    // Somone has won!

                                    gameActive = false;

                                    String winner = "";

                                    if (activePlayer == 1) {

                                        winner = "You";

                                    } else {

                                        winner = "AI";

                                    }

                                    Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

                                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                                    if (winner == "AI") {
                                        winnerTextView.setTextColor(Color.RED);

                                        winnerTextView.setText(winner + " has defeated you !");
                                        winnerTextView.setBackgroundColor(Color.parseColor("#ffffff"));
                                    } else if (winner == "You") {
                                        winnerTextView.setTextColor(Color.parseColor("#ffff00"));
                                        winnerTextView.setBackgroundColor(Color.parseColor("#000000"));

                                        winnerTextView.setText(winner + " defeated AI !");
                                    }
                                    ct = 0;

                                    playAgainButton.setVisibility(View.VISIBLE);

                                    winnerTextView.setVisibility(View.VISIBLE);

                                }
                            }
                            if (ct == 9) {
                                gameActive = false;

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

                }

                public void onTick(long millisUntilFinished) {
                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();


        }
    }

    public void playAgain(View view) {

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        playAgainButton.setVisibility(View.INVISIBLE);

        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);

        }

        for (int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;

        }

        activePlayer = 0;
        ct = 0;

        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classicactivityghost);
    }
}

