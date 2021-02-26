package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VoterList extends AppCompatActivity {

    ListView listView;
    //ListView listdata;
    User user;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    DatabaseReference reference;
    FirebaseDatabase database;

    private AutoCompleteTextView txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_list);

        txtSearch = (AutoCompleteTextView) findViewById(R.id.searchList);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView1);
        user = new User();
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //populateSearch(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        reference.addListenerForSingleValueEvent(event);

        reference.addValueEventListener(new ValueEventListener() {
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

            }
        });
    }

/*    private void populateSearch(DataSnapshot snapshot) {
        //ArrayList<String> names = new ArrayList<>();

        if (snapshot.exists()) {
            for (DataSnapshot ds : snapshot.getChildren()) {
                String fullName = ds.child("Users").getValue(String.class);
                list.add(fullName);
            }

            adapter = new ArrayAdapter<String>(this, R.layout.user_info, R.id.userInfo, list);
            txtSearch.setAdapter(adapter);

            txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fullName = txtSearch.getText().toString();
                    searchUser(fullName);
                }
            });

        } else {

        }

    }

    private void searchUser(String fullName) {

        Query query = reference.orderByChild("fullName").equalTo(fullName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()) {

                    //ArrayList<String> listusers = new ArrayList<>();

                    for(DataSnapshot ds: snapshot.getChildren()) {
                        //User user = new User(ds.child("fullName").getValue(String.class),ds.child("email").getValue(String.class));
                        //list.add(user.getFullName() + "\n" + user.getEmail());

                        user = ds.getValue(User.class);
                        list.add(user.getFullName().toString() + " " + "\n" + user.getEmail().toString());


                    }

                    adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.user_info, R.id.userInfo, list);
                    listView.setAdapter(adapter);

                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

/*    class User {
        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public User() {
        }

        public String getFullName() { return fullName; }
        public String getEmail() { return email; }

        public String fullName;
        public String email;*/
    //}

//}
//}