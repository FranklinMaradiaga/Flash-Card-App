package com.example.franklinsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        // Variable for the cancel button
        ImageView flashCardCancelButton = findViewById(R.id.flashcard_cancel_button);

        // Use the variable to use the Click Listener to listen when the user click on the cancel button
        flashCardCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the user clicks the cancel button, then this class gets destroyed and goes back to the MainActivity class
                finish();
            }
        });

        // Variable for the Save Button
        ImageView flashCardSaveButton = findViewById(R.id.flashcard_save_button);

        // Use the variable to use the Click Listener to listen when the user click on the save button
        flashCardSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText questionEditText = findViewById(R.id.flashcard_question_EditText);
                EditText answerEditText = findViewById(R.id.flashcard_answer_EditText);

                // What the user writes on the EditText's will be on the following String variables
                String inputQuestion = questionEditText.getText().toString();
                String inputAnswer = answerEditText.getText().toString();

                // Create an Intent object to pass the data back to MainActivity
                Intent data = new Intent();
                // Key's are used for the MainActivity to know how to obtain the different data
                data.putExtra("QUESTION_KEY", inputQuestion);
                data.putExtra("ANSWER_KEY", inputAnswer);
                // To make sure the data is passed correctly, and the intent object with the data
                setResult(RESULT_OK, data);
                // Destroy this class when the data has been sent to the MainActivity class
                finish();
            }
        });
    }
}