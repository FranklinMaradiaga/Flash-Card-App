package com.example.franklinsflashcardapp;

// This file creates the actual database.
// We tell it what kind of objects we are going to put in the database (Flashcard),
// and how we're planning to access the data (FlashcardDao).

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Flashcard.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashcardDao flashcardDao();
}
