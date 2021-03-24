package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConfirmVote extends AppCompatActivity {

    public String name;
    public String uid;
    Context context;
    TextView selectedCandidate;
    private DatabaseReference referenceDB;
    private Button logout;
    private Button confirmVote;

    private DatabaseReference reference1;
    private String userID1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_vote);

        //logout for voter btn
        logout = (Button) findViewById(R.id.candidLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ConfirmVote.this, MainActivity.class));
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference1 = FirebaseDatabase.getInstance().getReference("Users");
        userID1 = user.getUid();

        reference1.child(userID1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("voted").exists()) {
                    Boolean voted = snapshot.child("voted").getValue().toString().equals("true");
                    if(voted) {
                        confirmVote.setVisibility(View.INVISIBLE);
                        Toast.makeText(ConfirmVote.this, "User has already voted", Toast.LENGTH_LONG).show();
                    } else {
                        confirmVote.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get values from candidate details - uid and name
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        referenceDB = FirebaseDatabase.getInstance().getReference().child("Profiles");

        selectedCandidate = findViewById(R.id.candidateName);

        name = extras.getString("name");
        uid = extras.getString("uid");
        selectedCandidate.setText("The candidate you have choosen is " + "\n" + "\n" + name);

        /**
         * confirm candidate vote and increment vote count in firebase by 1
         */
        confirmVote = (Button) findViewById(R.id.confirmVote);

        confirmVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference candidates = FirebaseDatabase.getInstance().getReference("Profiles").child(uid).child("count");

                candidates.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot profile) {

                        //get current value in fb and increment vote count by 1
                        long voteCounts = (long) profile.getValue();
                        candidates.setValue(voteCounts + 1);
                        Toast.makeText(ConfirmVote.this, "You have recorded your vote", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}
