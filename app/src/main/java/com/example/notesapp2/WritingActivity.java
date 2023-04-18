package com.example.notesapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notesapp2.database.DatabaseOperations;
import com.example.notesapp2.databinding.ActivityWritingBinding;
import com.example.notesapp2.models.Note;

import org.jetbrains.annotations.NotNull;

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
            // Don't update anything
        }

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.descriptioninput.getText().toString();
                String title = binding.titleinput.getText().toString();
                if (!note_Id.isEmpty()){
                    mDBOperations.updateNote(note_Id, text, title);
                } else {
                    mDBOperations.createNote(text, title);
                }
                Intent intent=new Intent(WritingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}