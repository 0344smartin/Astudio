package com.example.videoapplication;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private Button loopButton;
    private boolean isLooping = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        videoView = findViewById(R.id.videoView);
        loopButton = findViewById(R.id.loop);


        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mivideo);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnCompletionListener(mp -> {
            if (isLooping) {
                videoView.start();
            }
        });
    }

    public void toggleLoop(View v) {
        isLooping = !isLooping;
        if (isLooping) {
            loopButton.setText("Desactivar reproducción en bucle");
        } else {
            loopButton.setText("Activar reproducción en bucle");
        }
    }
}
