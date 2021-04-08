package com.example.contacts.api;


import com.example.contacts.model.ContactsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequestData {
    @GET("contacts")
    Call<ContactsModel> getContacts();


}
