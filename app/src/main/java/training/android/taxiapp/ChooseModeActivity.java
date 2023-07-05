package training.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ChooseModeActivity extends AppCompatActivity {
    private Button driverButton, passengerButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);

        auth = FirebaseAuth.getInstance();
        driverButton = findViewById(R.id.driverButton);
        passengerButton = findViewById(R.id.passengerButton);

        driverButton.setOnClickListener(view -> startActivity(new Intent(ChooseModeActivity.this, DriverSingInActivity.class)));

        passengerButton.setOnClickListener(view -> startActivity(new Intent(ChooseModeActivity.this, PassengerSingInActivity.class)));
    }
}