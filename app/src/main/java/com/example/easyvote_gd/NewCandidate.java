package com.example.easyvote_gd;

import android.widget.ImageView;

public class NewCandidate {

    String newCandidateName;
    String newCandidateParty;
    String newCandidateLocation;
    //ImageView newProfilePic;

    String name;
    String party;
    String location;
    //ImageView profilePic;

    //public NewCandidate(String name, String party, String location, String profilePic)
    public NewCandidate(String name, String party, String location) {
        this.name = name;
        this.party = party;
        this.location = location;
        //this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

 /*  public ImageView getProfilePic() { return profilePic; }

    public void setProfilePic(ImageView profilePic) { this.profilePic = profilePic; }*/
}
