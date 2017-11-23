package com.coopmart.urbanfarming.urbanfarming.Activity;

/**
 * Created by sake on 08/06/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coopmart.urbanfarming.urbanfarming.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.as_input_name) EditText fullname;
    @BindView(R.id.as_input_email) EditText emailText;
    @BindView(R.id.as_input_mobile) EditText mobileText;
    @BindView(R.id.as_input_password_1) EditText passwordText1;
    @BindView(R.id.as_input_password_2) EditText passwordText2;
    @BindView(R.id.as_trigger_signup) Button signupButton;
    @BindView(R.id.as_trigger_login) TextView loginLink;

    @OnClick(R.id.as_trigger_signup)
    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(com.coopmart.urbanfarming.urbanfarming.Activity.SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Mohon Tungga");
        progressDialog.show();

        String name = fullname.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText1.getText().toString();
        String reEnterPassword = passwordText2.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSubmitSuccess or onSubmitFailed
                        // depending on success
                        onSignupSuccess();
                        // onSubmitFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @OnClick(R.id.as_trigger_login)
    public void login() {
        // Finish the registration screen and return to the Login activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    public void onSignupSuccess() {
        signupButton.setEnabled(true);

        Intent data = new Intent();
        data.putExtra("email", emailText.getText().toString());
        data.putExtra("passsword", passwordText1.getText().toString());

        setResult(RESULT_OK, data);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = fullname.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password1 = passwordText1.getText().toString();
        String password2 = passwordText2.getText().toString();

        if (firstname.isEmpty() || firstname.length() < 3) {
            fullname.setError("at least 3 characters");
            valid = false;
        } else {
            fullname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password1.isEmpty() || password1.length() < 4 || password1.length() > 10) {
            passwordText1.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText1.setError(null);
        }

        if (password2.isEmpty() || password2.length() < 4 || password2.length() > 10 || !(password2.equals(password1))) {
            passwordText2.setError("Password Do not match");
            valid = false;
        } else {
            passwordText2.setError(null);
        }

        return valid;
    }
}