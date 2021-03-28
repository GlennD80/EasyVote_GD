package com.example.easyvote_gd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateVoterDetails extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView txtSearch;

    private TextView fullNameUpd;
    private TextView addressUpd;
    private TextView emailUpd;
    private TextView ageUpd;

    private TextView newFullNameUpd;
    private TextView newAddressUpd;
    private TextView newAgeUpd;

    public String fullName;
    public String address;
    public String age;
    public String email;
    public String uid;

    public Button updateDet;
    private Button deleteVoterBtn;

    DatabaseReference reference;
    FirebaseDatabase mFire;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_voter_details);

        mAuth = FirebaseAuth.getInstance();

        //back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameUpd = (TextView) findViewById(R.id.fullNameUpdate);
        addressUpd = (TextView) findViewById(R.id.addressUpdate);
        ageUpd = (TextView) findViewById(R.id.ageUpdate);
        emailUpd = (TextView) findViewById(R.id.emailUpdate);
        updateDet = (Button) findViewById(R.id.updateDet);

        newFullNameUpd = (EditText) findViewById(R.id.enterFullNameUpdate);
        newAddressUpd = (EditText) findViewById(R.id.enterAddressUpdate);
        newAgeUpd = (EditText) findViewById(R.id.enterAgeUpdate);

        deleteVoterBtn = (Button) findViewById(R.id.deleteVoter);

        //get voter bundle details from search option from firebase
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        uid = extras.getString("uid");

        fullName = extras.getString("name");
        fullNameUpd.setText(" Name: " + fullName);

        address = extras.getString("address");
        addressUpd.setText(" Address: " + address);

        age = extras.getString("age");
        ageUpd.setText(" Age: " + age);

        email = extras.getString("email");
        emailUpd.setText(" Email: " + email);

       updateDet.setOnClickListener(this);

        deleteVoterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference deleteVoter = ref.child("Users").child(uid);

                deleteVoter.removeValue();
                Toast.makeText(UpdateVoterDetails.this, "Voter Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * update current voter details with new details
     */
    public void updateUser() {

        String newFullName = newFullNameUpd.getText().toString().trim();
        String newAddress = newAddressUpd.getText().toString().trim();
        String newAge = newAgeUpd.getText().toString().trim();

        if(newFullName.isEmpty()){
            newFullNameUpd.setError("Full Name is Required");
            newFullNameUpd.requestFocus();
            return;
        }

        if(newAddress.isEmpty()){
            newAddressUpd.setError("Address is Required");
            newAddressUpd.requestFocus();
            return;
        }

        if(newAge.isEmpty()){
            newAgeUpd.setError("Age is Required");
            newAgeUpd.requestFocus();
            return;
        }
    }

    private boolean isNameChanged() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newName = ref.child("Users").child(uid);

        String newFullName = newFullNameUpd.getText().toString().trim();

        if(!fullName.equals(newFullName)){

            newName.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("fullName").setValue(newFullName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return true;
        }
        return false;
    }

    private boolean isAddressChanged() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newAddressDB = ref.child("Users").child(uid);

        String newAddress = newAddressUpd.getText().toString().trim();

        if(!address.equals(newAddress)) {

            newAddressDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("address").setValue(newAddress);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
        return false;
    }

    private boolean isAgeChanged() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newAgeDB = ref.child("Users").child(uid);

        String newAge = newAgeUpd.getText().toString().trim();

        if(!age.equals(newAge)) {

            newAgeDB.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("age").setValue(newAge);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
        return false;
    }

    public void onClick (View view) {
        updateUser();
        if(isNameChanged() || isAddressChanged() || isAgeChanged()) {
            Toast.makeText(UpdateVoterDetails.this, "Details have been updated", Toast.LENGTH_SHORT).show();
        } else {
           Toast.makeText(UpdateVoterDetails.this, "Error updating details", Toast.LENGTH_SHORT).show();
        }
    }
}
