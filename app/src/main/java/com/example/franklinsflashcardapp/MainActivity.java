package com.example.franklinsflashcardapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Class variables for the Text Views on the XML file
    TextView questionTextView;
    TextView answerTextView;

    // Instance of our database so that we can read / write to it
    FlashcardDatabase flashcardDatabase;

    // To hold all our flashcard objects
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.flashcard_question_TextView);
        answerTextView = findViewById(R.id.flashcard_answer_TextView);

        // On Click Listener to hide the question when the user clicks it, then show the answer instead
        questionTextView.setOnClickListener(new View.OnClickListener() { // OR: findViewById(R.id.flashcard_question_TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View answerSideView = findViewById(R.id.flashcard_answer_TextView);

// get the center for the clipping circle
                int cx = answerSideView.getWidth() / 2;
                int cy = answerSideView.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerSideView, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                questionTextView.setVisibility(View.INVISIBLE);
                answerSideView.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();



//                questionTextView.setVisibility(View.INVISIBLE);
//                answerTextView.setVisibility(View.VISIBLE);
            }
        });

        // Variable for the add button on the Flash Card app
        ImageView addButton = findViewById(R.id.flashcard_add_button);

        // On Click Listener will listen when the user clicks on the add button,
        // Then it will take the user to the Add Card Activity
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This is how you initialize the Intent object to go from MainActivity to AddCardActivity when the add button is clicked
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
//                startActivity(intent)
                // We use the startActivityForResult method because we're expecting some data from the AddCardActivity class
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_in);
            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext()); // OR FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard firstCard = allFlashcards.get(0);
            questionTextView.setText(firstCard.getQuestion());
            answerTextView.setText(firstCard.getAnswer());
        }

        ImageView nextButton = findViewById(R.id.flashcard_next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards == null || allFlashcards.size() == 0) {
                    return;
                }

                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex >= allFlashcards.size()) {
                    Snackbar.make(v,
                            "You've reached the end of the cards! Going back to the start.",
                            Snackbar.LENGTH_SHORT)
                            .show();

                    currentCardDisplayedIndex = 0; // Reset index so user can go back to the beginning of the cards
                }

                Flashcard currentCard = allFlashcards.get(currentCardDisplayedIndex);
                questionTextView.setText(currentCard.getQuestion());
                answerTextView.setText(currentCard.getAnswer());

                final Animation leftOutAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.left_in);
                final Animation rightInAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                rightInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                findViewById(R.id.flashcard_question_TextView).startAnimation(leftOutAnim);
                findViewById(R.id.flashcard_question_TextView).startAnimation(rightInAnim);
            }
        });
    }

    // This method will be called if any data is received from the AddCardActivity class
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // This is the same code that MainActivity and AddActivity have in common to share data
        if (requestCode == 100) {
            // get data
            if (data != null) { // Check if there's an Intent
                // Using the key from the AddCardActivity, we can grab the data in String variables
                String questionString = data.getExtras().getString("QUESTION_KEY");
                String answerString = data.getExtras().getString("ANSWER_KEY");

                // Setting the text views from MainActivity screen with the data received from the AddCardActivity
                questionTextView.setText(questionString);
                answerTextView.setText(answerString);

                Flashcard flashcard = new Flashcard(questionString, answerString);
                flashcardDatabase.insertCard(flashcard);

                // Update the list of flashcards from the databasec
                allFlashcards = flashcardDatabase.getAllCards();
            }
        }
    }
}