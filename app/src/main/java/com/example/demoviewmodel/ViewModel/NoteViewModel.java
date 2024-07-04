package com.example.demoviewmodel.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.demoviewmodel.Model.Note;
import com.example.demoviewmodel.Repo.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository mRepository;
    private final LiveData<List<Note>> mAllNotes;

    private final MutableLiveData<Bitmap> mDrawingBitmap = new MutableLiveData<>();

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public void insert(Note note) {
        mRepository.insert(note);
    }

    public void setDrawingBitmap(Bitmap bitmap) {
        mDrawingBitmap.setValue(bitmap);
    }

    public LiveData<Bitmap> getDrawingBitmap() {
        return mDrawingBitmap;
    }

    public void deleteNoteAtPosition(int position) {
        List<Note> currentNotes = mAllNotes.getValue();
        if (currentNotes != null && position >= 0 && position < currentNotes.size()) {
            Note noteToDelete = currentNotes.get(position);
            mRepository.delete(noteToDelete);
        }
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
}
