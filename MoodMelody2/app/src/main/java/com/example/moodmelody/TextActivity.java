package com.example.moodmelody;
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

        btnSearch.setOnClickListener(v -> {
            String feeling = editFeeling.getText().toString().trim();
            if (!feeling.isEmpty()) {
                Intent intent = new Intent(TextActivity.this, SongsActivity.class);
                intent.putExtra("inputType", "text");
                intent.putExtra("feeling", feeling);
                startActivity(intent);
            } else {
                Toast.makeText(TextActivity.this, "Please enter your feeling", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
