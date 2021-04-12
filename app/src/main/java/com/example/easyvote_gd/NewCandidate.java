package com.example.easyvote_gd;

import android.widget.ImageView;

public class NewCandidate {

    String name;
    String party;
    String location;
    String count;
    String profilePic;

    public NewCandidate(String name, String party, String location, String profilePic) {
        this.name = name;
        this.party = party;
        this.location = location;
        this.count = "0";
        this.profilePic = profilePic;
    }

    public String getCount() { return count; }
    public void setCount(String count) { this.count = count; }
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
    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
}
