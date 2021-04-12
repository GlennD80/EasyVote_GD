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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        logout = (Button) findViewById(R.id.candidLogout);

        //logout button for voter btn
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ConfirmVote.this, MainActivity.class));
            }
        });


        /**
         * if voter has voted disable confirm button for all candidates
         *
         * verify from firebase bool value
         */

        //firebase ref voter users uid
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference1 = FirebaseDatabase.getInstance().getReference("Users");
        userID1 = user.getUid();

        //get voter ref by uid
        reference1.child(userID1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("voted").exists()) {

                    //if voter has voter already by checking bool (true) disable confirm btn
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

        //firebase ref for profiles table
        referenceDB = FirebaseDatabase.getInstance().getReference().child("Profiles");

        selectedCandidate = findViewById(R.id.candidateName);

        //candidate name and uid details
        name = extras.getString("name");
        uid = extras.getString("uid");

        //display candidate name
        selectedCandidate.setText("The candidate you have choosen is " + "\n" + "\n" + name);

        /**
         * confirm candidate vote and increment vote count in firebase by 1
         *
         * disable/invisible confirm vote btn when voter had voted - update bool in firebase
         */
        confirmVote = (Button) findViewById(R.id.confirmVote);

        confirmVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference candidateCount = FirebaseDatabase.getInstance().getReference("Profiles").child(uid).child("count");

                candidateCount.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot profile) {

                        //get current value in fb and increment vote count by 1
                        long voteCounts = (long) profile.getValue();
                        candidateCount.setValue(voteCounts + 1);

                        //get firebase for voter uid and set voter bool to true so voter cannot vote again
                        DatabaseReference voteUpdate = FirebaseDatabase.getInstance().getReference("Users").child(userID1);

                        voteUpdate.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                //set the firebase value for voted true for voter
                                dataSnapshot.getRef().child("voted").setValue(true);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        //disable confirm vote btn after vote selection
                        confirmVote.setVisibility(View.INVISIBLE);

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
