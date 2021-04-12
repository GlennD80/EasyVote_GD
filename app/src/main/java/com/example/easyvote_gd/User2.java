package com.example.easyvote_gd;

public class User2 {

    public String newFullName, newAddress, newAge;

    public User2(){
    }

    public User2(String fullName, String address, String age, String email){
        this.newFullName = fullName;
        this.newAddress = address;
        this.newAge = age;
        //this.email = email;
    }

    public String getNewFullName() {
        return newFullName;
    }
    public void setNewFullName(String newFullName) {
        this.newFullName = newFullName;
    }
    public String getNewAddress() {
        return newAddress;
    }
    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
    public String getNewAge() {
        return newAge;
    }
    public void setNewAge(String newAge) {
        this.newAge = newAge;
    }
}


