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

public class VoterProfileActivity extends AppCompatActivity {

    private Button logoutBtn;
    private Button voteBtn;
    private FirebaseUser voterDetails;
    private DatabaseReference voter_FirebaseReference;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutBtn = (Button) findViewById(R.id.signOut);

        //logout option for voter
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(VoterProfileActivity.this, MainActivity.class));
            }
        });

        //firebase reference for voter from Users table
        voterDetails = FirebaseAuth.getInstance().getCurrentUser();
        voter_FirebaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = voterDetails.getUid();

        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName_DB);
        final TextView emailTextView = (TextView) findViewById(R.id.email_DB);
        final TextView addressTextView = (TextView) findViewById(R.id.const_DB);
        final TextView ageTextView = (TextView) findViewById(R.id.age_DB);

        /**
         * get voter details from firebase using reference and display in textview
         */
        voter_FirebaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String fullName_Voter = userProfile.fullName;
                    String email_Voter = userProfile.email;
                    String address_Voter = userProfile.address;
                    String age_Voter = userProfile.age;

                    fullNameTextView.setText(" " + fullName_Voter);
                    emailTextView.setText(" " + email_Voter);
                    ageTextView.setText(" " + age_Voter);
                    addressTextView.setText(" " + address_Voter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VoterProfileActivity.this, "Error Getting User Details", Toast.LENGTH_LONG).show();
            }
        });

        voteBtn = (Button) findViewById(R.id.buttonVoteDetails);

        //button for vote option 
        voteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoterProfileActivity.this, Vote_CandidateList.class));
            }
        });

    }
}