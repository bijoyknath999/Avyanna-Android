package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.EnumMap;

public class ForgetPassword extends AppCompatActivity {

    private TextInputLayout EmailLayout;
    private TextInputEditText Email;
    private MaterialButton ResetButton;
    private String email;
    private FirebaseAuth mAuth;
    private LinearLayout loadingView, noDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.forget_password_email);
        EmailLayout = findViewById(R.id.forget_password_email_layout);
        ResetButton = findViewById(R.id.forget_password_reset_password);

        //init Loader
        loadingView = findViewById(R.id.loadingView);
        noDataView = findViewById(R.id.noDataView);


        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = Email.getText().toString();
                if (!email.isEmpty())
                {
                    EmailLayout.setError(null);
                    showLoader();
                    ResetPassword();
                }
                else
                {
                    if (email.isEmpty())
                    {
                        EmailLayout.setError("Please enter your email!!");
                    }
                }
            }
        });

    }

    private void ResetPassword() {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    hideLoader();
                    Toast.makeText(ForgetPassword.this,"Please Check your email inbox or spam box!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgetPassword.this, UserSelection.class));
                }

                else
                {
                    hideLoader();
                    String error = task.getException().getMessage();
                    Toast.makeText(ForgetPassword.this,"Error: "+error,Toast.LENGTH_SHORT).show();
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
}