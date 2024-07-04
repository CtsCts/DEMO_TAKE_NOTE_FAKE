package com.example.demoviewmodel;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demoviewmodel.Model.Note;
import com.example.demoviewmodel.ViewModel.NoteViewModel;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private EditText noteEditText;
    private CheckBox noteCheckBox;
    private EditText noteCheckBoxText;
    private DrawingView drawingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteEditText = findViewById(R.id.noteEditText);
        noteCheckBox = findViewById(R.id.noteCheckBox);
        drawingView = findViewById(R.id.drawingView);
        noteCheckBoxText = findViewById(R.id.note_check_box);
        Button saveNoteButton = findViewById(R.id.saveNoteButton);
        Button viewNotesButton = findViewById(R.id.viewNotesButton);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        saveNoteButton.setOnClickListener(v -> saveNote());
        viewNotesButton.setOnClickListener(v -> viewNotes());


        noteViewModel.getDrawingBitmap().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                if (bitmap != null) {
                    drawingView.setDrawing(bitmap);
                }
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("note_text")) {
            noteEditText.setText(intent.getStringExtra("note_text"));
            noteCheckBox.setChecked(intent.getBooleanExtra("check_box", false));
            noteCheckBoxText.setText(intent.getStringExtra("check_box_text"));

            // Convert byte array to bitmap
//            byte[] drawingBytes = intent.getByteArrayExtra("note_drawing");
//            if (null != drawingBytes) {
//                Bitmap bitmap = Converters.fromByteArrayToBitmap(drawingBytes);
//                drawingView.setDrawing(bitmap);
//            }

        }

    }

    private void saveNote() {

        String textNote = noteEditText.getText().toString();
        String textCheckBox = noteCheckBoxText.getText().toString();
        boolean isImportant = noteCheckBox.isChecked();
        Bitmap drawing = drawingView.getBitmap();
        // Convert to byte array
        byte[] drawingBytes = Converters.fromBitmapToByte(drawing);

        if (textNote.isEmpty() && textCheckBox.isEmpty() && !isImportant && drawingBytes.length == 0) {
            return;
        }

        Note note = new Note(textNote, textCheckBox, isImportant, drawingBytes);
        noteViewModel.insert(note);

        noteEditText.setText("");
        noteCheckBoxText.setText("");
        noteCheckBox.setChecked(false);
        drawingView.clear();

        Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void viewNotes() {
        // Move to NoteListActivity
        startActivity(new Intent(MainActivity.this, NoteListActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the current drawing to the ViewModel
        noteViewModel.setDrawingBitmap(drawingView.getBitmap());
    }
}
