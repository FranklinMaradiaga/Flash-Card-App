package com.example.franklinsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView flashCardQuestion = findViewById(R.id.flashcard_question_TextView);
        TextView flashCardAnswer = findViewById(R.id.flashcard_answer_TextView);

        findViewById(R.id.flashcard_question_TextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashCardQuestion.setVisibility(View.INVISIBLE);
                flashCardAnswer.setVisibility(View.VISIBLE);
            }
        });
    }
}