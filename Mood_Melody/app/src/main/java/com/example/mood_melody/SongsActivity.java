package com.example.mood_melody;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SongsActivity extends AppCompatActivity {

    private TextView songsTextView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        songsTextView = findViewById(R.id.songsTextView);

        // Get the feeling passed from the ImageActivity
        String feeling = getIntent().getStringExtra("FEELING");

        if (feeling != null && !feeling.isEmpty()) {
            // Show relevant songs based on the feeling
            showSongsForFeeling(feeling);
        } else {
            Toast.makeText(this, "No feeling passed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSongsForFeeling(String feeling) {
        // Map for songs based on feelings (with URLs)
        Map<String, String[]> songsMap = new HashMap<>();
        songsMap.put("Happy", new String[] {
                "<a href='https://youtu.be/YaEG2aWJnZ8?si=Tk0THIkI4-KDZzHb'>Unstoppable</a>",
                "<a href='https://youtu.be/kffacxfA7G4?si=doO3JKcmgUVNDnV3'>Baby -Justin Bieber</a>",
                "<a href='https://youtu.be/5GFtYgFRkx4?si=AnFAQHEtmRKcolOp'>One direction-Night Changes</a>",
                "<a href='https://youtu.be/t4H_Zoh7G5A?si=yFunfX_5AcvJ2TVf'>Jennifer Lopez -On the floor</a>",
                "<a href='https://youtu.be/kJQP7kiw5Fk?si=-J0eRKuLSJWWs7_t'>Luis fonsi -Despacito</a>"
        });
        songsMap.put("Sad", new String[] {
                "<a href='https://youtu.be/Jkj36B1YuDU?si=f2XhJzyPbUJe2Qaw'>Tom Odell - Another Love</a>",
                "<a href='https://youtu.be/GgASxM_Ju_c?si=IhF7RqZyiSQNe_yZ'>Love Is Gone</a>",
                "<a href='https://youtu.be/0yXlpgGqC9Y?si=cPHg2-r4xBtwMvSv'>Miss You</a>",
                "<a href='https://youtu.be/p0nEw4qhOlY?si=QEmJB6ql-AVtLILv'>Arash - Broken Angel ( Feat.Helena)</a>",
                "<a href='https://youtu.be/8ba8rQ_SJdc?si=-sck_VWp-ZoRG4M7'>One Day - ARASH feat Helena</a>"
        });
        songsMap.put("Romantic", new String[] {
                "<a href='https://youtu.be/tRFLs_-54gE?si=NBMRKouET_GD4suK'>Taylor Swift - Love Story</a>",
                "<a href='https://youtu.be/bJZmeIQ9L8A?si=4Mj83mox72_BlvQC'>Pink Sweat$ - At My Worst</a>",
                "<a href='https://youtu.be/BgmY2MkrY0I?si=qRgsE4t388_KcCXB'>Calum Scott - You Are The Reason</a>",
                "<a href='https://youtu.be/cNGjD0VG4R8?si=i0JXIgoqj1NRKaSJ'>Ed Sheeran - Perfect</a>",
                "<a href='https://youtu.be/1AoU--FMCrU?si=co9tSRlwSMICvjgy'>Stephen Sanchez, Em Beihold - Until I Found You</a>"
        });
        songsMap.put("Angry", new String[] {
                "<a href='https://youtu.be/WcIcVapfqXw?si=TML7dkml9lUZn1Xz'>Calm Down</a>",
                "<a href='https://youtu.be/2Vv-BfVoq4g?si=8qC8Nz3sFXcyWWuT'>Perfect - Ed sheeran</a>",
                "<a href='https://youtu.be/60ItHLz5WEA?si=iQUprkIMDMT7fNE7'>Faded</a>",
                "<a href='https://youtu.be/euCqAq6BRa4?si=27DEPI-XgZF3TAsg'>Let me love you</a>",
                "<a href='https://youtu.be/oyEuk8j8imI?si=ApGvTQ1TeQD2Xed4'>Love yourself</a>"
        });
        // Add more feelings as needed

        String[] songs = songsMap.get(feeling);

        if (songs != null) {
            // Format the HTML links for clickable song URLs
            StringBuilder songListBuilder = new StringBuilder();
            for (String song : songs) {
                songListBuilder.append(song).append("<br><br>");
            }
            // Set the song list with HTML content
            songsTextView.setText(Html.fromHtml(songListBuilder.toString()));
            // Enable the clickable links
            songsTextView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        } else {
            songsTextView.setText("No songs found for this feeling.");
        }
    }

    // Method to play the selected song by URL
    public void playSong(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            Toast.makeText(this, "Error playing song", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
