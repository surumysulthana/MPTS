package com.example.movement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar
//        getSupportActionBar().hide();

//         Hide the status bar and navigation bar
//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        VideoView videoView = findViewById(R.id.loadingVideoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.worng);

        videoView.setVideoURI(videoUri);
        videoView.start();

        // Simulate loading for 5 seconds (adjust as needed)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity or navigate to the next screen
                // For example:
                Intent intent = new Intent(MainActivity.this, Auth_home.class);
                startActivity(intent);
                finish(); // finish the loading activity
            }
        }, 5000); // 5000 milliseconds (5 seconds)
    }
}
