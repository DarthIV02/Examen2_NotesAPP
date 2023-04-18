package com.example.notesapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.notesapp2.database.DatabaseOperations;
import com.example.notesapp2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseOperations mDBOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDBOperations = new DatabaseOperations(MainActivity.this);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void RetrieveNote(View v) {
        String value = (String) v.getTag();
        Intent intent = new Intent(MainActivity.this, WritingActivity.class);
        intent.putExtra("Note_id", value);
        startActivity(intent);
    }
}