
package com.example.recyclerviewwithpagination.ui;

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
import android.widget.Switch;

import com.example.recyclerviewwithpagination.R;
import com.example.recyclerviewwithpagination.custom_views.CustomProgressDialog;

public class TodoActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity = null;
    private static final String TAG = "TodoActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;
    private AppCompatEditText edittextTitle,edittextDescription;
    private AppCompatTextView textviewCreate,textViewUpdate,textViewDelete;
    private LinearLayout linearLayoutupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

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
        txtHeaderNname.setText(getString(R.string.todo));
        edittextTitle = findViewById(R.id.edittextTitle);
        edittextDescription = findViewById(R.id.edittextDescription);
        textviewCreate=findViewById(R.id.textviewCreate);
        textViewUpdate=findViewById(R.id.textviewUpdate);
        textViewDelete=findViewById(R.id.textviewdelete);
        linearLayoutupdate=findViewById(R.id.linearlayoutupdatedelete);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("Edit Note");
            edittextTitle.setText(intent.getStringExtra("title"));
            edittextDescription.setText(intent.getStringExtra("description"));

        } else {
            setTitle("Add Note");
        }
    }

    private void saveNote() {
        String note_title = edittextTitle.getText().toString();
        String note_description = edittextDescription.getText().toString();

        Intent intent = new Intent();
        intent.putExtra("note_title", note_title);
        intent.putExtra("note_description", note_description);
        int id = getIntent().getIntExtra("id",-1);
        if (id != -1){
            intent.putExtra("id",id);
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textviewCreate:
                saveNote();
                break;
            case R.id.textviewUpdate:
                break;
            case R.id.textviewdelete:
                break;
        }

    }
}