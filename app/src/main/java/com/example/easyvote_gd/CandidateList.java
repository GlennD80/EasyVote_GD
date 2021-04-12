package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CandidateList extends AppCompatActivity {

    ListView listView;
    Candidate candidate;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    DatabaseReference reference;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //list view for
        listView = (ListView) findViewById(R.id.listView1);
        candidate = new Candidate();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.candidate_info, R.id.candidateInfo, list);

        //firebase ref for candidates in profiles table
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Profiles");

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };


        reference.addListenerForSingleValueEvent(event);

        //snapshot of candidate and list of candidates details
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //for loop to get all the candidates in profiles table
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //get candidate values and add to list
                    candidate = ds.getValue(Candidate.class);
                    list.add(candidate.getName().toString() + " " + "\n" + candidate.getParty().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








    }
}