package com.example.notesapp2.activities;

import static java.lang.Math.ceil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.notesapp2.R;
import com.example.notesapp2.database.DatabaseOperations;
import com.example.notesapp2.databinding.ActivityMainBinding;
import com.example.notesapp2.models.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseOperations mDBOperations;

    List<Note> notes;

    TableLayout tl;
    TableRow tr_title;
    TableRow tr_text;
    TextView tv_title;
    TextView tv_text;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final float scale = MainActivity.this.getResources().getDisplayMetrics().density;
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDBOperations = new DatabaseOperations(MainActivity.this);

        notes = mDBOperations.getAllNotes();

        tl = (TableLayout) binding.tableNotes;

        try{
            for(int i = 0; i < notes.size(); i++){
                if(i % 3 == 0){
                    if(i != 0){
                        tl.addView(tr_title, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        tl.addView(tr_text, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                    tr_text = new TableRow(MainActivity.this);
                    tr_text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                    tr_title = new TableRow(MainActivity.this);
                    tr_title.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                }
                tv_title = new TextView(MainActivity.this);
                tv_title.setWidth((int) (115 * scale + 0.5f));
                tv_title.setHeight((int) (30 * scale + 0.5f));
                //tv_title.setBackgroundColor(R.color.purple_200);
                tv_title.setClickable(true);
                tv_title.setText(notes.get(i).getTitle());
                tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tv_title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrieveNote(v);
                    }
                });
                tv_title.setTag(notes.get(i).getId());
                tv_title.setBackgroundResource(R.drawable.redondeo_item_bottom);

                tv_text = new TextView(MainActivity.this);
                tv_text.setWidth((int) (115 * scale + 0.5f));
                tv_text.setHeight((int) (30 * scale + 0.5f));
                tv_text.setBackgroundColor(R.color.purple_200);
                tv_text.setClickable(true);
                tv_text.setText(notes.get(i).getText());
                tv_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                tv_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RetrieveNote(v);
                    }
                });
                tv_text.setTag(notes.get(i).getId());
                tv_text.setBackgroundResource(R.drawable.redondeo_item);

                tr_title.addView(tv_title);
                tr_text.addView(tv_text);
            }

            tl.addView(tr_title, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tl.addView(tr_text, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        } catch (Exception e){
            // No notes to be added
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                startActivity(intent);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDBOperations.deleteNotes();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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