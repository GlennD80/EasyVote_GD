package com.example.easyvote_gd;

public class Profile {

    public Profile() {
    }

    private String uid;
    private String name;
    private String party;
    private String location;
    private String profilePic;
    private String voteBtn;

    public Profile(String uid, String name, String party, String location, String profilePic, String voteBtn) {
        this.uid = uid;
        this.name = name;
        this.party = party;
        this.location = location;
        this.profilePic = profilePic;
        this.voteBtn = voteBtn;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }
    public String getName() {return name;}
    public void setName(String name) { this.name = name; }
    public String getParty() { return party; }
    public void setParty(String party) { this.party = party; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }
    public String getVoteBtn() { return voteBtn; }
    public void setVoteBtn(String voteBtn) { this.voteBtn = voteBtn; }
}
