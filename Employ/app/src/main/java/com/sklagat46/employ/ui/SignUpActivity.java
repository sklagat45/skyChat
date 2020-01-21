package com.sklagat46.employ.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sklagat46.employ.R;

public class SignUpActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText username, residence, phoneNumber, userEmail;
    private Button createBtn,aboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        createBtn = (Button)findViewById(R.id.BtnCreate);
        username = (EditText) findViewById(R.id.ETFullName);
        residence = (EditText) findViewById(R.id.ETLocation);
        phoneNumber = (EditText) findViewById(R.id.ETPhone);
        userEmail = (EditText) findViewById(R.id.ETUserEmail);
        aboutBtn = (Button) findViewById(R.id.BtnAbout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //start about activity
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), AboutActivity.class);
                //pass information to next activity
                startActivity(startIntent);

                finish();
            }
        });

            //start login activity
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(startIntent);

                finish();
            }
        });


    }
}
