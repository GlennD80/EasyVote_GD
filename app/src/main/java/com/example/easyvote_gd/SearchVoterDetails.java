package com.example.easyvote_gd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchVoterDetails extends AppCompatActivity {

    public static SearchVoterDetails getInstance;
    private  SearchVoterDetails instance;

    private AutoCompleteTextView search_edit_text;
    private RecyclerView recyclerView;
    private Button updateVoterDetailsBtn;

    //lists of voter details
    ArrayList<String> uidList;
    ArrayList<String> fullNameList;
    ArrayList<String> addressList;
    ArrayList<String> ageList;
    ArrayList<String> emailList;
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

        //firebase ref
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //layout of recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        /**
         * search for voter
         */
        fullNameList = new ArrayList<>();
        addressList = new ArrayList<>();
        ageList = new ArrayList<>();
        emailList = new ArrayList<>();
        uidList = new ArrayList<>();

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
                    uidList.clear();
                    recyclerView.removeAllViews();
                }
            }
        });
    }

    /**
     * search for a voter in firebase
     * @param searchedString
     */
    private void setAdapter(final String searchedString) {

        //get firebase ref for the Users table
        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clear the list
                fullNameList.clear();
                addressList.clear();
                ageList.clear();
                emailList.clear();
                uidList.clear();
                recyclerView.removeAllViews();

                int counter = 0;

                //snapshot of data for fullname, address, age and email.
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String fullName = snapshot.child("fullName").getValue(String.class);
                    String address = snapshot.child("address").getValue(String.class);
                    String age = snapshot.child("age").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);

                    //snapshot value for voters uid.
                    String uid = snapshot.getKey();

                    //search list based on voter fullname
                    if(fullName.toLowerCase().contains(searchedString.toLowerCase())){
                        fullNameList.add(fullName);
                        addressList.add(address);
                        ageList.add(age);
                        emailList.add(email);
                        uidList.add(uid);
                        counter++;

                        //search list based on voter email
                    } else if (email.toLowerCase().contains(searchedString.toLowerCase())){
                        fullNameList.add(fullName);
                        addressList.add(address);
                        ageList.add(age);
                        emailList.add(email);
                        uidList.add(uid);
                        counter++;
                    }

                    //only add 15 to list
                    if(counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(SearchVoterDetails.this, fullNameList, addressList, ageList, emailList, uidList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}







