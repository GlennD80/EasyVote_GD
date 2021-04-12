package com.example.easyvote_gd;

public class User {

    public String fullName, age, address, email;
    boolean admin, voted;

    public User(){
    }

    public User(String address, String age, String email, String fullName){
        this.address = address;
        this.email = email;
        this.age = age;
        this.fullName = fullName;
        this.admin = false;
        this.voted = false;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email = email; }

    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public boolean isVoted() {
        return voted;
    }
    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}


