package com.dev.avyanna.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.avyanna.R;
import com.dev.avyanna.utils.AppUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText Email, Password;
    private MaterialButton SignInButton;
    private TextView ForgetPassword, SignUp;
    private RelativeLayout SignInGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private LinearLayout loadingView, noDataView;
    private String email, password;
    private TextInputLayout EmailLayout, PasswordLayout;
    private Boolean emailchecker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Email = findViewById(R.id.log_in_email);
        Password = findViewById(R.id.log_in_password);
        SignInButton = findViewById(R.id.log_in_sign_in);
        ForgetPassword = findViewById(R.id.log_in_forget_password);
        SignInGoogle = findViewById(R.id.log_in_google);
        SignUp = findViewById(R.id.log_in_sign_up);
        EmailLayout = findViewById(R.id.log_in_email_layout);
        PasswordLayout = findViewById(R.id.log_in_password_layout);


        //init Loader
        loadingView = findViewById(R.id.loadingView);
        noDataView = findViewById(R.id.noDataView);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();*/

        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




        SignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoader();
                signIn();
            }
        });

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = Email.getText().toString();
                password = Password.getText().toString();
                if (!email.isEmpty() && !password.isEmpty())
                {
                    if (password.trim().length() < 6) {
                        PasswordLayout.setError("Password length less than 6");
                        EmailLayout.setError(null);
                    }
                    else
                    {
                        showLoader();
                        SignInEmail();
                        EmailLayout.setError(null);
                        PasswordLayout.setError(null);
                    }
                }
                else
                    {
                        if (email.isEmpty())
                        {
                            EmailLayout.setError("Please enter your email!!");
                            PasswordLayout.setError(null);
                        }
                        else if (password.isEmpty())
                        {
                            PasswordLayout.setError("Please enter your password");
                            EmailLayout.setError(null);
                        }
                    }
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIntent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signIntent);
            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForgetPass = new Intent(LoginActivity.this, com.dev.avyanna.activity.ForgetPassword.class);
                startActivity(intentForgetPass);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                hideLoader();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            hideLoader();
                            SendUserToMainActivity();
                            Toast.makeText(LoginActivity.this, "Loged in Successfully", Toast.LENGTH_SHORT).show();                            // Sign in success, update UI with the signed-in user's information
                        }
                        else {
                            hideLoader();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Sorry authentication failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void SignInEmail(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)

                    {
                        if (task.isSuccessful())
                        {
                            VerifyEmail();
                        }
                        else
                        {

                            String message = task.getException().getMessage();
                            Toast.makeText(LoginActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                            hideLoader();

                        }
                    }
                });
    }


    private void VerifyEmail()

    {
        FirebaseUser user = mAuth.getCurrentUser();
        emailchecker = user.isEmailVerified();

        if (emailchecker)
        {
            hideLoader();
            SendUserToMainActivity();
            Toast.makeText(LoginActivity.this, "Loged in Successfully", Toast.LENGTH_SHORT).show();
        }

        else
        {
            hideLoader();
            Toast.makeText(this,"Please Verify your email first!!",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    private void SendUserToMainActivity()

    {

        Intent MainIntent = new Intent(LoginActivity.this, MainActivity.class);
        MainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(MainIntent);
        finish();
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