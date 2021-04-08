
package com.example.contacts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.contacts.R;
import com.example.contacts.custom_views.CustomProgressDialog;
import com.example.contacts.databasehelper.DatabaseHandler;
import com.example.contacts.model.ContactListModel;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity = null;
    private static final String TAG = "TodoActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;
    private AppCompatEditText edittextName,edittextAddress,edittextEmail,edittextMobile;
    private AppCompatTextView textviewCreate,textViewUpdate,textViewDelete;
    private LinearLayout linearLayoutupdate;
    private DatabaseHandler dhelper;
    private ArrayList<ContactListModel> contactLists=null;
    ContactListModel contactListmodel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        dhelper = new DatabaseHandler(this);
        activity = TodoActivity.this;
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
                startActivity(new Intent(activity, MainActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        AppCompatTextView txtHeaderNname = (AppCompatTextView) toolbar.findViewById(R.id.actv_header_name);
        txtHeaderNname.setText(getString(R.string.todo));
        edittextName = findViewById(R.id.edittextTitle);
        edittextMobile = findViewById(R.id.edittextMobile);
        edittextEmail=findViewById(R.id.edittextMail);


        textViewUpdate=findViewById(R.id.textviewUpdate);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Details");

            edittextName.setText(intent.getStringExtra("name"));
            edittextEmail.setText(intent.getStringExtra("email"));
            edittextMobile.setText(intent.getStringExtra("mobile"));


        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        contactListmodel=new ContactListModel(edittextName.getText().toString(),
                edittextEmail.getText().toString(),
                edittextMobile.getText().toString());
        dhelper.updateuser(contactListmodel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(activity, MainActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textviewUpdate:

                saveNote();
                break;

        }

    }
}