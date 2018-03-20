package com.example.carlos.scarnesdicegame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView scoreDisplay;
    Button Roll, Reset, Hold;
    ImageView diceDisplay;
    Handler timer = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timer.postDelayed(this, 500);
        }
    };
    static final int WINNER = 100;
    public final static String TAG = "clicked";
    int userScore, userTurnScore, rivalScore, rivalTurnScore;

    public int[] diceFace = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreDisplay = findViewById(R.id.display);

        diceDisplay = findViewById(R.id.Dice);

        Roll = findViewById(R.id.Roll);
        Reset = findViewById(R.id.Reset);
        Hold = findViewById(R.id.Hold);
    }
     /*Scarne’s Dice is a turn-based dice game where players
     score points by rolling a die and then:

        if they roll a 1, score no points and lose their turn
        if they roll a 2 to 6:
        add the rolled value to their points
        choose to either re-roll or keep their score and end their turn*/

    private int rolledDice() {
        //method to emulate the rolling of a dice number 1 to 6
        Random random = new Random();
        int rNum = random.nextInt(6);
        diceDisplay.setImageResource(diceFace[rNum]);
        return rNum + 1;
    }

    private void updateScoreDisplay() {
        //update the text view when the user is rolling the dice
        scoreDisplay.setText("Your Score: " + userScore + "\n" + "Turn Score: " + userTurnScore + "\n" + "Computer Score: " + rivalScore);
    }

    private void updateRivalScoreDisplay(int n) {
        //updates the textview when it is the computers turn
        if (n <= 1) {
           //Toast.makeText(getBaseContext(), "Rival rolled a 1", Toast.LENGTH_SHORT).show();
            scoreDisplay.setText("Your Score: " + userScore + "\n" + "WOMP WOMP" + "\n" + "computerScore: " + rivalScore);

        } else if (n > 1) {
            String meh = ("Rival rolled a " + n);
            //Toast.makeText(getBaseContext(), meh, Toast.LENGTH_SHORT).show();
            scoreDisplay.setText("User Score: " + userScore + "\n" + "Computer Turn Score: " + rivalTurnScore + "\n" + "Computer Score:" + rivalScore);

        }
        if (rivalScore >= WINNER) {
            scoreDisplay.setText("COMPUTER WINS" + "\n" + rivalScore);
            Hold.setEnabled(false);
            Roll.setEnabled(false);

        }else {
            Hold.setEnabled(true);
            Roll.setEnabled(true);
        }
    }


    public void diceRoll(View view) {
        Log.d(TAG, "diceRoll");
        //enables for the roll button to be used
        //will handle the game conditions for the Player


        /*randomly select a dice value
          update the display to reflect the rolled value*/
        if (rolledDice() == 1) {
            //what happens when player rolls a 1
            userTurnScore = 0;
            scoreDisplay.setText("Your Score: " + userScore + "\n" + "WOMP WOMP" + "\n" + "computerScore" + rivalScore);
            //Toast.makeText(getBaseContext(), "Womp Womp", Toast.LENGTH_SHORT).show();
        } else if (rolledDice() > 1 && rolledDice() <= 6) {
            //what happens when the player rolls the left over choices
            userTurnScore = rolledDice();
            updateScoreDisplay();
            //remember the turn score gets added when the player hits the home button

        }
        if (userScore >= WINNER) {
            scoreDisplay.setText("YOU Win!!!!" + "\n" + userScore);
            Hold.setEnabled(false);
            Roll.setEnabled(false);
        }


    }

    public void resetDice(View view) {
        Log.d(TAG, "resetDice");
        Toast.makeText(getBaseContext(), "RESETTTTTTTTTT", Toast.LENGTH_SHORT).show();
        //resets the scores
        userScore = userTurnScore = rivalScore = rivalTurnScore = 0;
        updateScoreDisplay();

    }

    public void holdRoll(View view) {
        Log.d(TAG, "holdRoll");
        //player turn ends and cpu goes
        //adds userscore and turn score
        Toast.makeText(getBaseContext(), "Computers Turn", Toast.LENGTH_SHORT).show();
        userScore += userTurnScore;
        userTurnScore = 0;
        updateScoreDisplay();
        computerTurn();


    }

    private void computerTurn() {
        timer.postDelayed(runnable, 1000);


        if (rolledDice() == 1) {
            rivalTurnScore = 0;
            updateRivalScoreDisplay(rivalTurnScore);
        } else if (rolledDice() > 1 & rolledDice() <= 6) {
            rivalTurnScore = rolledDice();
            rivalScore += rivalTurnScore;
            updateRivalScoreDisplay(rivalScore);
        }


    }

    @Override
    public void onClick(View v) {

    }
}
