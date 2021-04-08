package com.example.contacts.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contacts.model.ContactListModel;
import com.example.contacts.model.ContactsModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    //@Insert
   // void insert(ContactListModel contact);

    //@Delete
    //void delete(ContactListModel contact);

    //@Update
    //void update(ContactListModel contact);

    //@Query("DELETE FROM Contact")
    //void deleteAll();

    //@Query("SELECT * FROM Contact")
    //LiveData<ArrayList<ContactListModel>> getAllNotes();
}

