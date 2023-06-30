package training.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;


public class SplashScreenActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, ChooseModeActivity.class));
            finish(); // Optional: Close the splash activity if needed
        }, 2000); // Specify the delay time in milliseconds (e.g., 2000 for 2 seconds)

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}