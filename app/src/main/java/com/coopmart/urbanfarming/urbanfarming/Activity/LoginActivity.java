package com.coopmart.urbanfarming.urbanfarming.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coopmart.urbanfarming.urbanfarming.Method.Navigator;
import com.coopmart.urbanfarming.urbanfarming.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.al_input_email) EditText emailText;
    @BindView(R.id.al_input_password) EditText passwordText;
    @BindView(R.id.al_trigger_login) Button loginButton;
    @BindView(R.id.al_trigger_signup) TextView signupLink;

    @OnClick(R.id.al_trigger_login)
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Mohon Tunggu");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @OnClick(R.id.al_trigger_signup)
    public void gotoSignup() {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, Navigator.REQUEST_SIGNUP);
        //finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void login(String email, String password) {
        emailText.setText(email);
        passwordText.setSelected(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Register Success");
                login(data.getStringExtra("email"), data.getStringExtra("password"));
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        //TODO: Assign a chached data to store login cookie

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("flag", Navigator.FROM_LOGIN_ACTIVITY);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
