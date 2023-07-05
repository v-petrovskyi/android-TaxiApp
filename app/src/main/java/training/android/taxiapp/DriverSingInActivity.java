package training.android.taxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DriverSingInActivity extends AppCompatActivity {

    private final String TAG = "DriverSingInActivity";
    private TextInputLayout textInputEmail, textInputName, textInputPassword, textInputConfirmPassword;
    private Button loginSingUpButton;
    private TextView toggleLoginSingUpTextView;
    private boolean isLoginModeActive;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sing_in);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, DriverMapsActivity.class));
        }

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        loginSingUpButton = findViewById(R.id.loginSingUpButton);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);

        loginSingUpButton.setOnClickListener(view -> loginSingUpUser(view));
        toggleLoginSingUpTextView.setOnClickListener(view -> toggleLoginSingUp(view));


    }

    private void toggleLoginSingUp(View view) {
        if (isLoginModeActive) {
            isLoginModeActive = false;
            loginSingUpButton.setText(R.string.sing_up);
            toggleLoginSingUpTextView.setText(R.string.tap_to_log_in);
            textInputConfirmPassword.setVisibility(View.VISIBLE);
            textInputName.setVisibility(View.VISIBLE);
        } else {
            isLoginModeActive = true;
            loginSingUpButton.setText(R.string.log_in);
            toggleLoginSingUpTextView.setText(R.string.tap_to_sing_up);
            textInputConfirmPassword.setVisibility(View.GONE);
            textInputName.setVisibility(View.GONE);
        }
    }

    private void loginSingUpUser(View view) {
        if (isLoginModeActive) {
            mAuth.signInWithEmailAndPassword(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(DriverSingInActivity.this, DriverMapsActivity.class));
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(DriverSingInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
        } else {
            if (!validateEmail() | !validateName() | !validatePassword()) {
                return;
            }
            mAuth.createUserWithEmailAndPassword(textInputEmail.getEditText().getText().toString().trim(), textInputPassword.getEditText().getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(DriverSingInActivity.this, DriverMapsActivity.class));
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(DriverSingInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }
                        }
                    });
        }
    }

    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Please input your email");
            return false;
        } else {
            textInputEmail.setError("");
            return true;
        }
    }

    private boolean validateName() {
        String nameInput = textInputName.getEditText().getText().toString().trim();
        if (nameInput.isEmpty()) {
            textInputName.setError("Please input your name");
            return false;
        } else if (nameInput.length() > 15) {
            textInputName.setError("Name is too long");
            return false;
        } else {
            textInputName.setError("");
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        String confirmPasswordInput = textInputConfirmPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("Please input your password");
            textInputConfirmPassword.setError("");
            return false;
        } else if (passwordInput.length() < 6) {
            textInputPassword.setError("Password must be at least 6 symbols");
            textInputConfirmPassword.setError("");
            return false;
        } else if (!passwordInput.equals(confirmPasswordInput)) {
            textInputPassword.setError("");
            textInputConfirmPassword.setError("Password have to match");
            return false;
        } else {
            textInputPassword.setError("");
            textInputConfirmPassword.setError("");
            return true;
        }
    }
}