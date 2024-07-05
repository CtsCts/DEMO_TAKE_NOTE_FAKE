package com.example.demoviewmodel;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoviewmodel.Model.Note;
import com.example.demoviewmodel.ViewModel.NoteViewModel;
import com.example.demoviewmodel.adapter.NoteAdapter;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteActionsListener {

    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteAdapter = new NoteAdapter(noteViewModel.getAllNotes().getValue(), this);
        noteRecyclerView.setAdapter(noteAdapter);

        noteViewModel.getAllNotes().observe(this, notes -> {
            noteAdapter.setNotes(notes); // Ensure you have setNotes method in NoteAdapter
            noteAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void onNoteDelete(int position) {
        noteViewModel.deleteNoteAtPosition(position);
        noteAdapter.deleteNoteAtPosition(position);
    }

    @Override
    public void onNoteEdit(Note note) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("note_text", note.getTextNote());
        intent.putExtra("check_box", note.isImportant());
        intent.putExtra("check_box_text", note.getTxtCheckBox());
        Toast.makeText(this, "It will be fixed and advance soon", Toast.LENGTH_SHORT).show();
//        intent.putExtra("note_drawing", note.getDrawing());
        startActivity(intent);

    }

    @Override
    public void onNoteOption() {
        Toast.makeText(this, "It feature will be coming soon by lack of time advanced", Toast.LENGTH_SHORT).show();
    }
}
