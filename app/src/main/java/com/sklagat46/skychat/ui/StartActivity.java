package com.sklagat46.skychat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sklagat46.skychat.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class StartActivity extends AppCompatActivity {
    Button btnRegister;
    FirebaseAuth mAuth;
    DatabaseReference databaseUserProfile;
    AVLoadingIndicatorView avi;
    @BindView(R.id.ETUsername)
    EditText user_name;
    @BindView( R.id.BtnSignIn)
    Button sign_in;
    @BindView(R.id.ETUserEmail)
    EditText user_email;
    @BindView(R.id.ETPassword)
    EditText user_password;
    @BindView(R.id.BtnSignUp)
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        bind(this);
        avi=findViewById(R.id.avi);
        mAuth = FirebaseAuth.getInstance();

        sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(startIntent);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    register(user_email.getText().toString(), user_password.getText().toString(), user_name.getText().toString());
                }
            }
        });

    }
    private void register(final String email, String password, final String username) {
        avi.show();
        avi.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    if (!task.isSuccessful()) {
                        //Log.e("REGISTER_EX",task.getException().getMessage());
                        avi.hide();
                        Toast.makeText(StartActivity.this,
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        createNewUser(email, username ,mAuth.getCurrentUser().getUid());
                    }
                } catch (Exception e) {
                    avi.hide();
                    Log.e("REGISTER_EX", e.getMessage(), e);
                }
                //hideProgressDialog();
            }
        });
    }
    private void createNewUser(String email, final String username, String uid) {
        databaseUserProfile = FirebaseDatabase.getInstance().getReference().child("skyChat").child("userProfile").child(uid);
        HashMap<String, String> userMap= new HashMap<>();
        userMap.put("username",username);
        userMap.put("status","hey there, am using SkyChat");
        userMap.put("image","default");
        userMap.put("thump_image","default");
        databaseUserProfile.setValue(userMap);
        databaseUserProfile.child("email").setValue(email)
//        databaseUserProfile.child("username").setValue(username);
//        databaseUserProfile.child("password").setValue(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {


            @Override
            public void onComplete(@NonNull Task<Void> task) {
                avi.hide();
                Intent intent=new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("username",username);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean validate() {
        boolean isValid;
        if (user_name.getText().toString().equals("")){
            user_name.setError("Username is required");
            isValid = false;
        } else if (user_email.getText().toString().equals("")) {
            user_email.setError("Email is required");
            isValid = false;
        } else if (!isEmailValid(user_email.getText().toString())) {
            user_email.setError("Enter a valid email");
            isValid = false;
        } else if (user_password.getText().toString().equals("")) {
            user_password.setError("Password is required");
            isValid = false;
        }else if (user_password.getText().toString().length()<6){
            user_password.setError("Password must not be less than 6 characters");
            isValid=false;
        }
        else {
            isValid = true;
        }
        return isValid;
    }
    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}