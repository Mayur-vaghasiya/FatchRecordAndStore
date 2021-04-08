package com.example.contacts.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactsModel implements Serializable {

    @SerializedName("contacts")
    @Expose
    private ArrayList<Contact> contacts = null;
    private final static long serialVersionUID = -2289352755633535937L;

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Entity(tableName = "Contact")
    public static class Contact implements Serializable {
        @PrimaryKey(autoGenerate = true)
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("phone")
        @Expose
        private Phone phone;
        private final static long serialVersionUID = 656476785554870479L;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public class Phone implements Serializable {

            @SerializedName("mobile")
            @Expose
            private String mobile;
            @SerializedName("home")
            @Expose
            private String home;
            @SerializedName("office")
            @Expose
            private String office;
            private final static long serialVersionUID = 1136513203664122629L;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getHome() {
                return home;
            }

            public void setHome(String home) {
                this.home = home;
            }

            public String getOffice() {
                return office;
            }

            public void setOffice(String office) {
                this.office = office;
            }

        }

    }

}