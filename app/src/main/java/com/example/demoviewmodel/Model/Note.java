package com.example.demoviewmodel.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String textNote;
    private String txtCheckBox;
    private boolean isImportant;
    private byte[] drawing; // Use byte array to store the drawing

    // Constructor
    public Note(String textNote, String txtCheckBox, boolean isImportant, byte[] drawing) {
        this.textNote = textNote;
        this.txtCheckBox = txtCheckBox;
        this.isImportant = isImportant;
        this.drawing = drawing;
    }

    // Getters and setters
    public String getTxtCheckBox() {
        return txtCheckBox;
    }

    public void setTxtCheckBox(String checkBoxTxt) {
        this.txtCheckBox = checkBoxTxt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String text) {
        this.textNote = text;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    public byte[] getDrawing() {
        return drawing;
    }

    public void setDrawing(byte[] drawing) {
        this.drawing = drawing;
    }
}

