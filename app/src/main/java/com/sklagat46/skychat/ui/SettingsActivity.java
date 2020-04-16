package com.sklagat46.skychat.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sklagat46.skychat.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static butterknife.ButterKnife.bind;

public class SettingsActivity extends AppCompatActivity {
    DatabaseReference databaseUserProfile;
    FirebaseUser mCurrentUser;

    @BindView(R.id.profile_image)
    CircleImageView user_img;
    @BindView(R.id.setUserNameTV)
    TextView user_name;
    @BindView(R.id.statusET)
    TextView user_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        bind(this);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();

        databaseUserProfile = FirebaseDatabase.getInstance().getReference().child("userProfile").child(current_uid);
    databaseUserProfile.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String name =dataSnapshot.child("username").getValue().toString();
            String image =dataSnapshot.child("image").getValue().toString();
            String status =dataSnapshot.child("status").getValue().toString();
            String thumb_img =dataSnapshot.child("thump_image").getValue().toString();
            user_name.setText(name);
            user_status.setText(status);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }
}
