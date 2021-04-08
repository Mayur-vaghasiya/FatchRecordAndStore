package com.example.contacts.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.contacts.R;
import com.example.contacts.adapter.ContactAdapter;
import com.example.contacts.custom_views.CustomProgressDialog;
import com.example.contacts.databasehelper.DatabaseHandler;
import com.example.contacts.model.ContactListModel;
import com.example.contacts.model.ContactsModel;
import com.example.contacts.model.Note;
import com.example.contacts.util.CleanableEditText;
import com.example.contacts.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, ContactAdapter.onItemClickListner {

    private Activity activity = null;
    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;
    private RecyclerView recyclerView = null;
    private LinearLayoutManager layoutManager = null;
    private DatabaseHandler dhelper;
    ContactAdapter contactAdapter;
    FloatingActionButton floatingActionButton;
    private CleanableEditText search;
    private ArrayList<ContactsModel.Contact> contactsList = null;

    private ArrayList<ContactListModel> contactLists=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity = HomeActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AppCompatTextView txtHeaderNname = (AppCompatTextView) toolbar.findViewById(R.id.actv_header_name);
        txtHeaderNname.setText(getString(R.string.todolist));
        search = (CleanableEditText) findViewById(R.id.search);

        dhelper = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerviewContact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

       // contactsList = dhelper.getAllContacts();
        contactLists=dhelper.getAllContacts();
        contactAdapter = new ContactAdapter(new WeakReference<Context>(activity), contactLists);
        recyclerView.setAdapter(contactAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                contactAdapter.getNoteAt(i);
                dhelper.deleteEntry(i);
                contactLists.remove(i);
                contactAdapter.notifyDataSetChanged();
                Toast.makeText(activity, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        contactAdapter.setOnItemClickListner(this);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

                if (null != contactsList) {
                    String text = search.getText().toString().toLowerCase(Locale.getDefault());
                    contactAdapter.filters(text);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        contactAdapter = new ContactAdapter(new WeakReference<Context>(activity), contactLists);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onItemClick(ContactListModel note) {
        Intent intent = new Intent(activity, TodoActivity.class);
        intent.putExtra("id", note.getID());
        intent.putExtra("name", note.getName());
        intent.putExtra("email", note.getEmail());
        intent.putExtra("mobile", note.getMobile());
         startActivity(intent);
    }
}