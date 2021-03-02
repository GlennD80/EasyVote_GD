package com.example.easyvote_gd;

public class NewCandidate {

    String newCandidateName;
    String newCandidateParty;
    String newCandidateLocation;

    String name;
    String party;
    String location;

    public NewCandidate(String name, String party, String location) {
        this.name = name;
        this.party = party;
        this.location = location;
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
}
