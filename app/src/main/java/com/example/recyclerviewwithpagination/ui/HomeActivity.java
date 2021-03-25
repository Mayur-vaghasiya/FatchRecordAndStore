package com.example.recyclerviewwithpagination.ui;

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
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerviewwithpagination.R;
import com.example.recyclerviewwithpagination.adapter.NoteAdapter;
import com.example.recyclerviewwithpagination.custom_views.CustomProgressDialog;
import com.example.recyclerviewwithpagination.model.Note;
import com.example.recyclerviewwithpagination.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,NoteAdapter.onItemClickListner {

    private Activity activity = null;
    private static final String TAG = "HomeActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;
    private RecyclerView recyclerView = null;
    private LinearLayoutManager layoutManager = null;
    private TodoListAdapter todoListAdapter=null;
    NoteAdapter noteAdapter;
    FloatingActionButton floatingActionButton;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
   private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activity=HomeActivity.this;
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
        recyclerView=findViewById(R.id.recycler_viewToDoList);
        floatingActionButton = findViewById(R.id.noteActivity_floatingButton);
        recyclerView = findViewById(R.id.recycler_viewToDoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(activity, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                // update Recycler View
                noteAdapter.setNotes(notes);
            }
        });

        floatingActionButton.setOnClickListener(this);
        noteAdapter.setOnItemClickListner(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.noteActivity_floatingButton:
                Intent intent = new Intent(activity,TodoActivity.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("note_title");
            String note_description = data.getStringExtra("note_description");
            int note_priority = data.getIntExtra("note_priority",0);

            noteViewModel.insert(new Note(note_title,note_description,note_priority));

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            String note_title = data.getStringExtra("note_title");
            String note_description = data.getStringExtra("note_description");
            int note_priority = data.getIntExtra("note_priority",0);
            int note_id = data.getIntExtra("id",0);

            Note note = new Note(note_title,note_description,note_priority);
            note.setId(note_id);
            noteViewModel.update(note);

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(activity,TodoActivity.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getTitle());
        intent.putExtra("description",note.getDescription());
        intent.putExtra("priority",note.getPriority());
        startActivityForResult(intent,EDIT_NOTE_REQUEST);
    }
}