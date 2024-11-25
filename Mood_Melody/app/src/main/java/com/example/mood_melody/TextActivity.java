package com.example.mood_melody;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {

    private EditText editFeeling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        editFeeling = findViewById(R.id.editFeeling);
        Button btnSearch = findViewById(R.id.btnSearchText);

        // Set up button click listener
        btnSearch.setOnClickListener(v -> {
            String feeling = editFeeling.getText().toString().trim(); // Get the feeling from EditText

            // Check if the feeling input is empty
            if (!feeling.isEmpty()) {
                // Intent to navigate to SongsActivity with the entered feeling
                Intent intent = new Intent(TextActivity.this, SongsActivity.class);
                intent.putExtra("FEELING", feeling); // Pass the feeling to SongsActivity
                startActivity(intent); // Start SongsActivity
            } else {
                // Show a toast message if the feeling is not entered
                Toast.makeText(TextActivity.this, "Please enter your feeling", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
