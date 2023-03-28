package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText Email, Password, ConfirmPassword;
    private TextInputLayout EmailLayout, PasswordLayout, ConfirmPasswordLayout;
    private MaterialButton SignUpButton;
    private TextView PrivacyPolicy, SignIn;
    private CheckBox checkBox;
    private LinearLayout loadingView, noDataView;
    private FirebaseAuth mAuth;
    private String email, password, confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Email = findViewById(R.id.sign_up_email);
        Password = findViewById(R.id.sign_up_password);
        ConfirmPassword = findViewById(R.id.sign_up_confirm_password);
        SignUpButton = findViewById(R.id.sign_up_sign_up);
        SignIn = findViewById(R.id.sign_up_sign_in);
        checkBox = findViewById(R.id.sign_up_check_checkbox);
        EmailLayout = findViewById(R.id.sign_up_email_layout);
        PasswordLayout = findViewById(R.id.sign_up_password_layout);
        ConfirmPasswordLayout = findViewById(R.id.sign_up_confirm_password_layout);


        //init Loader
        loadingView = findViewById(R.id.loadingView);
        noDataView = findViewById(R.id.noDataView);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSIgnIn = new Intent(SignUpActivity.this,UserSelection.class);
                startActivity(intentSIgnIn);
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked())
                {
                    email = Email.getText().toString();
                    password = Password.getText().toString();
                    confirmpassword = ConfirmPassword.getText().toString();

                    if (!email.isEmpty() && !password.isEmpty() && !confirmpassword.isEmpty())
                    {
                        if (password.trim().length() < 6) {
                            PasswordLayout.setError("Password length less than 6");
                            EmailLayout.setError(null);
                            ConfirmPasswordLayout.setError(null);
                        }
                        else if (!password.equals(confirmpassword))
                        {
                            ConfirmPasswordLayout.setError("Password not match");
                            PasswordLayout.setError(null);
                            EmailLayout.setError(null);

                        }
                        else
                        {
                            showLoader();
                            CreateAccount();
                            PasswordLayout.setError(null);
                            EmailLayout.setError(null);
                            ConfirmPasswordLayout.setError(null);
                        }
                    }
                    else
                    {
                        if (email.isEmpty())
                        {
                            EmailLayout.setError("Please enter your email!!");
                            PasswordLayout.setError(null);
                            ConfirmPasswordLayout.setError(null);
                        }
                        else if (password.isEmpty())
                        {
                            PasswordLayout.setError("Please enter your password");
                            EmailLayout.setError(null);
                            ConfirmPasswordLayout.setError(null);
                        }
                        else if (confirmpassword.isEmpty())
                        {
                            ConfirmPasswordLayout.setError("Please enter your confirm password");
                            PasswordLayout.setError(null);
                            EmailLayout.setError(null);
                        }
                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Please read and accept privacy policy.",Toast.LENGTH_LONG).show();
                    PasswordLayout.setError(null);
                    EmailLayout.setError(null);
                    ConfirmPasswordLayout.setError(null);
                }
            }
        });

    }

    private void SendEmailVerification()
    {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        SendUserToSignIn();
                        mAuth.signOut();
                        Toast.makeText(SignUpActivity.this, "We've sent you a verification mail. Please check your spam box!!",Toast.LENGTH_LONG).show();
                        hideLoader();
                    }
                    else
                    {
                        hideLoader();
                        String error = task.getException().getMessage();
                        Toast.makeText(SignUpActivity.this,"Error: "+error,Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
            });
        }
    }

    private void SendUserToSignIn()
    {
        Intent SignEmailIntent = new Intent(SignUpActivity.this, UserSelection.class);
        SignEmailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(SignEmailIntent);
        finish();
    }

    private void CreateAccount()
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful())

                        {
                            SendEmailVerification();
                        }

                        else
                        {
                            hideLoader();
                            String message = task.getException().getMessage();
                            Toast.makeText(SignUpActivity.this, "Error Occured:" + message, Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    protected void onStart() {

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}