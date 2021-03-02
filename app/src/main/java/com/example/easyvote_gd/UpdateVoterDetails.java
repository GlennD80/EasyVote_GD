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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public Button updateDet;

    DatabaseReference reference;
    FirebaseDatabase mFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_voter_details);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullNameUpd = (TextView) findViewById(R.id.fullNameUpdate);
        addressUpd = (TextView) findViewById(R.id.addressUpdate);
        ageUpd = (TextView) findViewById(R.id.ageUpdate);
        emailUpd = (TextView) findViewById(R.id.emailUpdate);
        updateDet = (Button) findViewById(R.id.updateDet);

        newFullNameUpd = (EditText) findViewById(R.id.enterFullNameUpdate);
        newAddressUpd = (EditText) findViewById(R.id.enterAddressUpdate);
        newAgeUpd = (EditText) findViewById(R.id.enterAgeUpdate);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fullName = extras.getString("name");
        fullNameUpd.setText(" Name: " + fullName);

        address = extras.getString("address");
        addressUpd.setText(" Address: " + address);

        age = extras.getString("age");
        ageUpd.setText(" Age: " + age);

        email = extras.getString("email");
        emailUpd.setText(" Email: " + email);

       updateDet.setOnClickListener(this);
    }

    private void updateUser() {

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

    //public String newFullName, newAddress, newAge;

    private boolean isNameChanged() {
        if(!newFullNameUpd.getText().toString().trim().equals(fullName)) {
            return true;
        }
        return false;
    }

    private boolean isAddressChanged() {
        if(!newAddressUpd.getText().toString().trim().equals(address)) {
            return true;
        }
        return false;
    }

    private boolean isAgeChanged() {
        if(!newAgeUpd.getText().toString().trim().equals(age)) {
            return true;
        }
        return false;
    }


    public void onClick (View view) {
        updateUser();
        if(isNameChanged() || isAddressChanged() || isAgeChanged()) {
            //reference.child("Users").child(email).child("name").setValue(newFullNameUpd.getText().toString().trim());
            //reference.child(email).child("address").setValue(newAddressUpd.getText().toString().trim());
            //reference.child(email).child("age").setValue(newAgeUpd.getText().toString().trim());

            //FirebaseDatabase.getInstance().getReference("Users").setValue(newFullNameUpd.getText().toString().trim());

        Toast.makeText(UpdateVoterDetails.this, "Details have been updated", Toast.LENGTH_SHORT).show();
        } else {
           Toast.makeText(UpdateVoterDetails.this, "Error updating details", Toast.LENGTH_SHORT).show();
        }

    }
}
