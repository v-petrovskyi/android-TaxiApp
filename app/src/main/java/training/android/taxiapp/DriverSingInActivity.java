package training.android.taxiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class DriverSingInActivity extends AppCompatActivity {

    private TextInputLayout textInputEmail, textInputName, textInputPassword, textInputConfirmPassword;
    private Button loginSingUpButton;

    private TextView toggleLoginSingUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sing_in);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputName = findViewById(R.id.textInputName);
        textInputPassword = findViewById(R.id.textInputPassword);
        textInputConfirmPassword = findViewById(R.id.textInputConfirmPassword);
        loginSingUpButton = findViewById(R.id.loginSingUpButton);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);

        loginSingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSingUpUser(view);
            }
        });
        toggleLoginSingUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void loginSingUpUser(View view) {
        if (!validateEmail() | !validateName() | !validatePassword()) {
            return;
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