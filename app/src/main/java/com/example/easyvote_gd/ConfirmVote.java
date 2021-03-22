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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_vote);

        logout = (Button) findViewById(R.id.candidLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ConfirmVote.this, MainActivity.class));
            }
        });

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        referenceDB = FirebaseDatabase.getInstance().getReference().child("Profiles");

        selectedCandidate = findViewById(R.id.candidateName);

        name = extras.getString("name");
        uid = extras.getString("uid");
        selectedCandidate.setText("The candidate you have choosen is " + "\n" + "\n" + name);

        confirmVote = (Button) findViewById(R.id.confirmVote);

        confirmVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmVote.this, "You have recorded your vote", Toast.LENGTH_SHORT).show();

                DatabaseReference candidates = FirebaseDatabase.getInstance().getReference("Profiles").child(uid).child("count");

                candidates.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot profile) {

                        long voteCounts = (long) profile.getValue();
                        candidates.setValue(voteCounts + 1);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}
