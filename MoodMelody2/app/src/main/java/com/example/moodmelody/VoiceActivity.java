package com.example.moodmelody;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class VoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        Button btnRecord = findViewById(R.id.btnRecordVoice);
        Button btnSearch = findViewById(R.id.btnSearchVoice);

        btnRecord.setOnClickListener(v -> {
            // Placeholder for voice recording logic
            Toast.makeText(VoiceActivity.this, "Voice input captured", Toast.LENGTH_SHORT).show();
        });

        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(VoiceActivity.this, SongsActivity.class);
            intent.putExtra("inputType", "voice");
            startActivity(intent);
        });
    }
}
