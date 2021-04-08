package com.example.contacts.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contacts.dao.ContactDao;
import com.example.contacts.dao.NoteDao;
import com.example.contacts.model.Note;


@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    public abstract ContactDao contactDao();

    private static NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,NoteDatabase.class,"ContactManager")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
