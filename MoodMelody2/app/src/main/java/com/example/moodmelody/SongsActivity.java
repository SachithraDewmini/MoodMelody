package com.example.moodmelody;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SongsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        TextView textView = findViewById(R.id.textViewSongs);
        String inputType = getIntent().getStringExtra("inputType");

        if (inputType != null) {
            switch (inputType) {
                case "text":
                    String feeling = getIntent().getStringExtra("feeling");
                    textView.setText("Displaying songs for feeling: " + feeling);
                    break;
                case "voice":
                    textView.setText("Displaying songs based on voice input.");
                    break;
                case "image":
                    textView.setText("Displaying songs based on uploaded image.");
                    break;
                default:
                    textView.setText("No input provided.");
            }
        }
    }
}
