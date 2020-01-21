package com.sklagat46.skychat.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.sklagat46.skychat.R;

import java.io.File;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class SignInActivity extends AppCompatActivity {
    private View view;

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
    private FirebaseAuth firebaseAuth;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bind(this);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

    }
}
