package com.example.easyvote_gd;

public class User {

    public String fullName, age, email, constituency;

    public User(){
    }

    public User(String fullName, String age, String email, String constituency){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.constituency = constituency;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public void setEmail(String email) {
        this.email = email;
    }
    public String getConstituency() {
        return constituency;
    }
    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }
}


