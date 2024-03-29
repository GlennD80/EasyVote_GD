package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VoterList extends AppCompatActivity {

    ListView listView;
    User user;
    Button exportUserList;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    DatabaseReference votersFirebaseRef;
    FirebaseDatabase database;

    private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_list);

        txtSearch = (AutoCompleteTextView) findViewById(R.id.searchList);
        //exportUserList = (Button) findViewById(R.id.exportList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //list of voter details
        listView = (ListView) findViewById(R.id.listView1);
        user = new User();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);

        //firebase ref for voters
        database = FirebaseDatabase.getInstance();
        votersFirebaseRef = database.getReference("Users");

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

        /**
         * get voters details and add to list
         */
        votersFirebaseRef.addListenerForSingleValueEvent(event);

        votersFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    user = ds.getValue(User.class);
                    list.add(user.getFullName().toString() + " " + "\n" + user.getEmail().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(VoterList.this, "Database Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void export (View view) {
    }
}
