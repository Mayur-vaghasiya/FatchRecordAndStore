package com.example.contacts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "Contact")
public class ContactListModel implements Serializable {
    public int getID() {
        return ID;
    }

    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "Name")
    private String Name;
    @ColumnInfo(name = "Email")
    private String Email;
    @ColumnInfo(name = "Gender")
    private String Gender;
    @ColumnInfo(name = "Address")
    private String Address;
    @ColumnInfo(name = "Mobile")
    private String Mobile;

    public ContactListModel(){

    }
    public ContactListModel(String name, String email,  String mobile) {

        Name = name;
        Email = email;
        Mobile = mobile;
    }

    public ContactListModel(int ID, String name, String email,  String mobile) {
        this.ID = ID;
        Name = name;
        Email = email;
        Mobile = mobile;
    }
    public ContactListModel( String name, String email,  String address,String gender, String mobile) {

        Name = name;
        Email = email;
        Gender = gender;
        Address = address;
        Mobile = mobile;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }



}
