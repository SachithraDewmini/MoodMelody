package com.example.moodmelody;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private Uri imageUri;
    private TextView emotionTextView;
    private Button uploadButton;
    private Bitmap selectedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.btnUploadImage);
        emotionTextView = findViewById(R.id.emotionTextView);

        imageView = findViewById(R.id.imageView);

        Button btnSearch = findViewById(R.id.btnSearchImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnSearch.setOnClickListener(v -> {
            if (imageUri != null) {
                Intent intent = new Intent(ImageActivity.this, SongsActivity.class);
                intent.putExtra("inputType", "image");
                intent.putExtra("imageUri", imageUri.toString());
                startActivity(intent);
            } else {
                Toast.makeText(ImageActivity.this, "Please upload an image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

