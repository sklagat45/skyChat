package com.sklagat46.employ.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sklagat46.employ.R;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText phoneNumber;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button)findViewById(R.id.BtLogin);
        phoneNumber = (EditText)findViewById(R.id.ETPhone);
        signUp = (TextView)findViewById(R.id.TVSignUp);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                //pass information to next activity
                startActivity(startIntent);

                finish();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                //pass information to next activity
                startActivity(startIntent);

                finish();
            }
        });


    }
}
