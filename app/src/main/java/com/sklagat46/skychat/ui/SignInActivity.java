package com.sklagat46.skychat.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sklagat46.skychat.R;
import com.sklagat46.skychat.views.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;

import static butterknife.ButterKnife.bind;

public class SignInActivity extends AppCompatActivity {
    private View view;
    FirebaseAuth mAuth;
    DatabaseReference databaseUserProfile;

    private File file;
    private String iname;
    @BindView(R.id.ETUserName)
    EditText user_name;
    @BindView(R.id.ETPassword)
    EditText user_password;
    @BindView(R.id.BtnSignIn)
    Button btnSignIn;
    @BindView(R.id.BtnRegister)
    Button btnRegister;
    protected static Activity activity;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bind(this);
        mAuth = FirebaseAuth.getInstance();
        databaseUserProfile = FirebaseDatabase.getInstance().getReference("userProfile");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(startIntent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = user_name.getText().toString();
                pass = user_password.getText().toString();
//                    Login(user_name.getText().toString(), user_password.getText().toString());
                if(user.equals("")){
                    user_name.setError("can't be blank");
                }
                else if(pass.equals("")){
                    user_password.setError("can't be blank");
                }
                else{
                    String url = "https://skychat-a8d46.firebaseio.com/skyChat/userProfile.json";
                    final ProgressDialog pd = new ProgressDialog(SignInActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(SignInActivity.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(user)){
                                        Toast.makeText(SignInActivity.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)){
                                        UserProfile.txtusername = user;
                                        UserProfile.txtpassword = pass;
                                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(SignInActivity.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });RequestQueue rQueue = Volley.newRequestQueue(SignInActivity.this);
                    rQueue.add(request);
                }

            }
        });

//                Intent startIntent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(startIntent);
//                finish();
            }}
