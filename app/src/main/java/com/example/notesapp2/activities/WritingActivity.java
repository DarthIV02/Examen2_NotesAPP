package com.example.notesapp2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notesapp2.database.DatabaseOperations;
import com.example.notesapp2.databinding.ActivityWritingBinding;
import com.example.notesapp2.models.Note;

import java.util.Date;

public class WritingActivity extends AppCompatActivity {

    ActivityWritingBinding binding;

    DatabaseOperations mDBOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWritingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String note_Id = intent.getStringExtra("Note_id");

        mDBOperations = new DatabaseOperations(WritingActivity.this);

        try {
            if (!note_Id.isEmpty()) {
                Note note = mDBOperations.getNote(note_Id);
                binding.titleinput.setText(note.getTitle());
                binding.fecha.setText(note.getLastModified());
                binding.descriptioninput.setText(note.getText());
            }
        } catch (NullPointerException e){
            binding.fecha.setText(new Date().toString());
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.descriptioninput.getText().toString();
                String title = binding.titleinput.getText().toString();
                try {
                    if (!note_Id.isEmpty()){
                        mDBOperations.updateNote(note_Id, text, title);
                    }
                } catch (NullPointerException e) {
                    mDBOperations.createNote(text, title);
                }
                Intent intent=new Intent(WritingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}