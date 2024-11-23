package com.example.moodmelody;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnText = findViewById(R.id.btnText);
        Button btnVoice = findViewById(R.id.btnVoice);
        Button btnImage = findViewById(R.id.btnImage);

        // Set button click listeners
        btnText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TextActivity.class);
            startActivity(intent);
        });

        btnVoice.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VoiceActivity.class);
            startActivity(intent);
        });

        btnImage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(intent);
        });
    }
}
