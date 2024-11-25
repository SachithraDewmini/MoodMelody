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

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        imageResources.add(R.drawable.image1); // Replace with your drawable file names
        imageResources.add(R.drawable.image2); // Replace with your drawable file names
        imageResources.add(R.drawable.image3); // Replace with your drawable file names

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
        String[] imageNames = {"Image 1", "Image 2", "Image 3"}; // Replace with your image names

        builder.setItems(imageNames, (dialog, which) -> {
            // Get the selected image resource ID
            int selectedImageResId = imageResources.get(which);

            // Set the selected image in the ImageView
            imageView.setImageResource(selectedImageResId);

            // Convert the selected image to a Bitmap
            selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedImageResId);

            // Analyze the image to detect feelings
            analyzeImageWithMLKit(selectedBitmap);
        });

        builder.show();
    }

    private void analyzeImageWithMLKit(Bitmap bitmap) {
        // Create an InputImage object from the Bitmap
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        // Get an ImageLabeler instance with default options
        ImageLabeler labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);

        // Process the image
        labeler.process(image)
                .addOnSuccessListener(labels -> {
                    // Map labels to feelings
                    Map<String, String> labelToFeelingMap = getLabelToFeelingMap();
                    boolean feelingDetected = false;

                    // Minimum confidence threshold
                    float minConfidenceThreshold = 0.7f;

                    StringBuilder feelings = new StringBuilder();

                    for (ImageLabel label : labels) {
                        String detectedLabel = label.getText().toLowerCase();
                        float confidence = label.getConfidence();

                        // Debugging: Log all detected labels
                        System.out.println("Label: " + detectedLabel + ", Confidence: " + confidence);

                        // Check if the label maps to a human feeling and meets the confidence threshold
                        if (labelToFeelingMap.containsKey(detectedLabel) && confidence >= minConfidenceThreshold) {
                            String feeling = labelToFeelingMap.get(detectedLabel);
                            feelings.append(feeling).append("\n"); // Only add the feeling without confidence
                            identifiedFeeling = feeling; // Store the identified feeling
                            feelingDetected = true;
                        }
                    }

                    // If no specific feeling detected, show a fallback message
                    if (!feelingDetected) {
                        feelings.append("No specific feelings detected. Try another image.");
                    }

                    // Display the detected feelings in the emotionTextView
                    emotionTextView.setText(feelings.toString());
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    emotionTextView.setText("Failed to detect feelings: " + e.getMessage());
                });
    }

    private Map<String, String> getLabelToFeelingMap() {
        // Define a mapping from detected labels to feelings
        Map<String, String> labelToFeelingMap = new HashMap<>();

        // Human emotion mappings

        labelToFeelingMap.put("face", "Neutral");
        labelToFeelingMap.put("sad", "Sad");
        labelToFeelingMap.put("angry", "Angry");
        labelToFeelingMap.put("love", "Romantic");
        labelToFeelingMap.put("smile", "Happy");

        // Scene mappings related to feelings
        labelToFeelingMap.put("flower", "Romantic");
        labelToFeelingMap.put("tree", "Relaxed");
        labelToFeelingMap.put("sunset", "Peaceful");
        labelToFeelingMap.put("cloud", "Tired");
        labelToFeelingMap.put("animal", "Excited");
        labelToFeelingMap.put("water", "Calm");

        return labelToFeelingMap;
    }
}
