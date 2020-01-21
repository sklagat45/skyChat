package com.sklagat46.skychat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.sklagat46.skychat.views.UserProfile;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {
    private View view;

    private File file;
    private String iname;
    @BindView(R.id.ETUsername)
    EditText user_name;
    @BindView(R.id.ETUserEmail)
    EditText user_email;
    @BindView(R.id.ETPassword)
    EditText user_password;
    @BindView(R.id.BtnSignup)
    Button btnregister;

    public static Activity activity;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseUserProfile;
    private CharSequence email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        FirebaseAuth auth;
//        //addListenerOnButton();
        auth = FirebaseAuth.getInstance();
        databaseUserProfile = FirebaseDatabase.getInstance().getReference("userProfile");
//
//        //get firebase instance
        firebaseUser = auth.getCurrentUser();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),SignInActivity.class);
                        startActivity(startIntent);
                finish();
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserProfile();
            }
        });

    }
    private void addUserProfile() {
        String username = user_name.getText().toString().trim();
        String userEmail = user_email.getText().toString().trim();
        String Userpassword = user_password.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(Userpassword)) {

            Toast.makeText(getApplicationContext(), "Please enter all the details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Userpassword.length() < 6) {

            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (user_email.getText().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            user_email.setError(getString(R.string.error_invalid_email));
            return;
        }else {

            String id = databaseUserProfile.push().getKey();
            UserProfile userProfile = new UserProfile(id, username, userEmail, Userpassword);
            databaseUserProfile.child(id).setValue(userProfile);
            Toast.makeText(getApplicationContext(), "user added", Toast.LENGTH_SHORT).show();
        }
        Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(userEmail, Userpassword)
                .addOnCompleteListener(StartActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser = auth.getCurrentUser();

                        Toast.makeText(StartActivity.this, "account created successfully:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(StartActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            startActivity(new Intent(StartActivity.this, com.sklagat46.skychat.ui.SignInActivity.class
                            ));
                            finish();
                        }
                    }
                    //create user

                });
        }

        @Override
        protected void onResume() {
            super.onResume();


}
}
