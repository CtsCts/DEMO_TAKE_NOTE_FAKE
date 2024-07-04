package com.example.demoviewmodel.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoviewmodel.Model.Note;
import com.example.demoviewmodel.R;

import java.lang.ref.WeakReference;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;

    private WeakReference<OnNoteActionsListener> mNoteListener;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public NoteAdapter(List<Note> notes, OnNoteActionsListener noteActionsListener) {
        this.notes = notes;
        this.mNoteListener = new WeakReference<>(noteActionsListener);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.noteTextView.setText(currentNote.getTextNote());
        holder.noteCheckBox.setChecked(currentNote.isImportant());
        holder.noteCheckBox.setClickable(false);
        holder.noteTxtCheckBox.setText(currentNote.getTxtCheckBox());

        if (currentNote.getDrawing() != null) {
            Bitmap drawingBitmap = BitmapFactory.decodeByteArray(currentNote.getDrawing(), 0, currentNote.getDrawing().length);
            holder.noteImageView.setImageBitmap(drawingBitmap);
        } else {
            holder.noteImageView.setImageResource(android.R.color.transparent);
        }

        holder.deleteImg.setOnClickListener(view -> {
            if (null != mNoteListener) {
                mNoteListener.get().onNoteDelete(position);
            }
        });

        holder.editImg.setOnClickListener(view -> {
            if (null != mNoteListener) {
                mNoteListener.get().onNoteEdit(currentNote);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotes(int position) {
        if (null != notes) {
            return notes.get(position);
        }
        return null;
    }

    public void deleteNoteAtPosition(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTextView;
        private final CheckBox noteCheckBox;
        private final TextView noteTxtCheckBox;
        private final ImageView noteImageView;
        private final ImageView deleteImg;
        private final ImageView editImg;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
            noteCheckBox = itemView.findViewById(R.id.noteCheckBox);
            noteTxtCheckBox = itemView.findViewById(R.id.note_check_box);
            noteImageView = itemView.findViewById(R.id.noteImageView);
            deleteImg = itemView.findViewById(R.id.delete_img);
            editImg = itemView.findViewById(R.id.edit_img);
        }
    }

    public interface OnNoteActionsListener {
        void onNoteDelete(int position);

        void onNoteEdit(Note note);
    }
}
