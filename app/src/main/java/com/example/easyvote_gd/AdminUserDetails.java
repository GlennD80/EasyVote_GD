package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminUserDetails extends AppCompatActivity {

    private Button logoutAdmin;
    private Button voteAdmin;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        logoutAdmin = (Button) findViewById(R.id.logOutAdmin);

        //admin user logout btn
        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminUserDetails.this, MainActivity.class));
            }
        });

        //get voter user firebase reference by uid
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.adminNameDB);
        final TextView emailTextView = (TextView) findViewById(R.id.adminEmailDB);

        //display admin user details populated from firebase
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                //if admin voter details are not null display name and email
                if(userProfile != null) {
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;

                    //set the admin user name and email values
                    fullNameTextView.setText(" " + fullName);
                    emailTextView.setText(" " + email);
                }
            }

            //error message if fail to get admin details
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminUserDetails.this, "Error Getting User Details", Toast.LENGTH_LONG).show();
            }
        });

        //navigate to next activity
        voteAdmin = (Button) findViewById(R.id.adminVoteDet);

        //navigate to next activity
        voteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminUserDetails.this, AdminMainMenu.class));
            }
        });


    }
}