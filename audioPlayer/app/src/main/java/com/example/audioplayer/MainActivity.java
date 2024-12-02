package com.example.audioplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mp = MediaPlayer.create(this,R.raw.miaudio);
        b=findViewById(R.id.b_loop);
    }
    public void empezarServicio(View v){
        Intent intent = new Intent(MainActivity.this, AudioPlayer.class);
        startService(intent); // Inicia el servicio
    }
    public void acabarServicio(View v) {
        Intent intent = new Intent(MainActivity.this, AudioPlayer.class);
        stopService(intent); // Detiene el servicio
    }

    public void play(View v){
        if (mp.isPlaying()){
            return;
        }
        mp.start();
    }
    public void pause(View v){
        if (!mp.isPlaying()){
            return;
        }
        mp.pause();
    }
    public void resume(View v){
        if (mp.isPlaying()){
            return;
        }
        mp.start();
    }
    public void stop(View v){
        if (!mp.isPlaying()){
            return;
        }
        mp.stop();
    }
    public void loop(View v){
        if (mp.isLooping()){
            mp.setLooping(false);
            b.setText(R.string.enable_track_loop);
            return;
        }
        mp.setLooping(true);
        b.setText(R.string.disable_track_loop);

    }
}