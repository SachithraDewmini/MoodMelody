package com.example.mood_melody;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;  // Import List

public class ImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView emotionTextView;
    private List<Integer> imageResources; // Store references to drawable images
    private Bitmap selectedBitmap; // To store the selected image as a Bitmap
    private String identifiedFeeling = ""; // Store the identified feeling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        // Initialize views
        imageView = findViewById(R.id.imageView);
        emotionTextView = findViewById(R.id.emotionTextView);
        Button selectImageButton = findViewById(R.id.btnSelectImage);
        Button searchButton = findViewById(R.id.btnsearch);

        // Initialize the list of drawable resource IDs
        imageResources = new ArrayList<>();
        imageResources.add(R.drawable.image1); // image1 corresponds to angry
        imageResources.add(R.drawable.image2); // image2 corresponds to sad
        imageResources.add(R.drawable.image3); // image3 corresponds to happy

        // Set up the button click listener for selecting an image
        selectImageButton.setOnClickListener(v -> showImageSelectionDialog());

        // Set up the button click listener for searching songs based on identified feeling
        searchButton.setOnClickListener(v -> {
            if (!identifiedFeeling.isEmpty()) {
                // Pass the identified feeling to SongsActivity
                Intent intent = new Intent(ImageActivity.this, SongsActivity.class);
                intent.putExtra("FEELING", identifiedFeeling);
                startActivity(intent);
            } else {
                emotionTextView.setText("Please select an image first.");
            }
        });
    }

    private void showImageSelectionDialog() {
        // Create an AlertDialog to show the list of images
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an Image");

        // Convert image resource IDs to user-friendly names
        String[] imageNames = {"Image 1", "Image 2", "Image 3"};

        builder.setItems(imageNames, (dialog, which) -> {
            // Get the selected image resource ID
            int selectedImageResId = imageResources.get(which);

            // Set the selected image in the ImageView
            imageView.setImageResource(selectedImageResId);

            // Convert the selected image to a Bitmap
            selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedImageResId);

            // Identify the emotion directly based on the selected image
            identifyEmotionBasedOnImage(which);
        });

        builder.show();
    }

    private void identifyEmotionBasedOnImage(int imageIndex) {
        // Map image index to the emotion
        switch (imageIndex) {
            case 0:
                identifiedFeeling = "Angry"; // image1 corresponds to "Angry"
                emotionTextView.setText("Feeling: Angry");
                break;
            case 1:
                identifiedFeeling = "Sad"; // image2 corresponds to "Sad"
                emotionTextView.setText("Feeling: Sad");
                break;
            case 2:
                identifiedFeeling = "Happy"; // image3 corresponds to "Happy"
                emotionTextView.setText("Feeling: Happy");
                break;
            default:
                identifiedFeeling = "";
                emotionTextView.setText("Unknown feeling");
        }
    }
}
