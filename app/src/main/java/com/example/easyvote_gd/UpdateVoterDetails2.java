package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateVoterDetails2 extends AppCompatActivity {

    private AutoCompleteTextView search_edit_text;
    private RecyclerView recyclerView;
    private Button updateVoterDetailsBtn;

    ArrayList<String> fullNameList;
    ArrayList<String> addressList;
    ArrayList<String> ageList;
    ArrayList<String> emailList;
    ArrayList<String> uid;
    SearchAdapter searchAdapter;

    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_voter_details2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        search_edit_text = (AutoCompleteTextView) findViewById(R.id.searchList);
        recyclerView = (RecyclerView) findViewById(R.id.result_list);
        //updateVoterDetailsBtn = (Button) findViewById(R.id.updateVoterDetailsBtn);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        fullNameList = new ArrayList<>();
        addressList = new ArrayList<>();
        ageList = new ArrayList<>();
        emailList = new ArrayList<>();
        uid = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    setAdapter(s.toString());
                } else {
                    fullNameList.clear();
                    addressList.clear();
                    ageList.clear();
                    emailList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    private void setAdapter(final String searchedString) {

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fullNameList.clear();
                addressList.clear();
                ageList.clear();
                emailList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String fullName = snapshot.child("fullName").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String age = snapshot.child("age").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    if(fullName.toLowerCase().contains(searchedString.toLowerCase())){
                        fullNameList.add(fullName);
                        addressList.add(address);
                        ageList.add(age);
                        emailList.add(email);
                        counter++;
                    } else if (email.toLowerCase().contains(searchedString.toLowerCase())){
                        fullNameList.add(fullName);
                        addressList.add(address);
                        ageList.add(age);
                        emailList.add(email);
                        counter++;
                    }

                    if(counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(UpdateVoterDetails2.this, fullNameList, addressList, ageList, emailList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}







