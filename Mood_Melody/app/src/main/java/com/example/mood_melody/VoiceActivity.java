package com.example.mood_melody;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class VoiceActivity extends AppCompatActivity {

    private TextView moodTextView;  // To display the recognized mood
    private Button speakButton;    // Button to input mood via voice
    private Button searchButton;   // Button to search for songs based on mood

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        // Initialize UI components
        moodTextView = findViewById(R.id.moodTextView);
        speakButton = findViewById(R.id.speakButton);
        searchButton = findViewById(R.id.searchButton);

        // Set click listener for Speak button
        speakButton.setOnClickListener(v -> startVoiceRecognition());

        // Set click listener for Search button
        searchButton.setOnClickListener(v -> navigateToSongsActivity());
    }

    // Starts voice recognition to capture mood
    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tell me your mood...");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                // Display the recognized mood in the TextView
                moodTextView.setText(results.get(0));
            } else {
                Toast.makeText(this, "No mood detected. Try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Navigate to SongsActivity with the recognized mood
    private void navigateToSongsActivity() {
        String mood = moodTextView.getText().toString().trim();

        if (mood.isEmpty()) {
            Toast.makeText(this, "Please speak or input your mood first.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Map the mood input to a valid feeling string (such as "Happy", "Sad", etc.)
        String feeling = mapMoodToFeeling(mood);

        // Ensure that we have a valid feeling to pass
        if (feeling != null && !feeling.isEmpty()) {
            Intent intent = new Intent(VoiceActivity.this, SongsActivity.class);
            intent.putExtra("FEELING", feeling); // Pass the feeling to SongsActivity
            startActivity(intent);
        } else {
            Toast.makeText(this, "Unable to recognize mood. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to map the recognized mood to a corresponding feeling (you can enhance this logic as needed)
    private String mapMoodToFeeling(String mood) {
        String normalizedMood = mood.toLowerCase();

        if (normalizedMood.contains("happy") || normalizedMood.contains("joyful") || normalizedMood.contains("cheerful")) {
            return "Happy";
        } else if (normalizedMood.contains("sad") || normalizedMood.contains("lonely") || normalizedMood.contains("heartbroken")) {
            return "Sad";
        } else if (normalizedMood.contains("romantic") || normalizedMood.contains("love")) {
            return "Romantic";
        } else {
            return ""; // Return empty if mood is not recognized
        }
    }
}