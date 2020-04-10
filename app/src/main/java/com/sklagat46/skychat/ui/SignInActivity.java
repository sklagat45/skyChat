package com.sklagat46.skychat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sklagat46.skychat.R;

import java.io.File;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class SignInActivity extends AppCompatActivity {
    private View view;
    FirebaseAuth mAuth;
    DatabaseReference databaseUserProfile;

    private File file;
    private String iname;
    @BindView(R.id.ETUserEmail)
    EditText user_email;
    @BindView(R.id.ETPassword)
    EditText user_password;
    @BindView(R.id.BtnSignIn)
    Button btnSignIn;
    @BindView(R.id.BtnRegister)
    Button btnRegister;
    protected static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bind(this);
        mAuth = FirebaseAuth.getInstance();
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleProgressBar.setVisibility(View.VISIBLE);
                Intent startIntent = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleProgressBar.setVisibility(View.VISIBLE);
                String email = user_email.getEditableText().toString();
                String password = user_password.getEditableText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {
                    simpleProgressBar.setVisibility(View.VISIBLE);
                    simpleProgressBar.setVisibility(View.GONE);
                    simpleProgressBar.setIndeterminate(false);
                    logInUser(email, password);
                }else{
                    Toast.makeText(SignInActivity.this, "All entries must be filled.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        private void logInUser(String email, String password) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                databaseUserProfile = FirebaseDatabase.getInstance().getReference("userProfile");
                                if (user != null) {
                                    // Name, email address, and profile photo Url
                                    String username = user.getDisplayName();
                                    Intent mainIntent =new Intent(SignInActivity.this,MainActivity.class);
                                    mainIntent.putExtra("username",username);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }

    }



