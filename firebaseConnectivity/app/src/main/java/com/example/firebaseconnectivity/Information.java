package com.example.firebaseconnectivity;

import java.util.jar.Attributes;

import javax.xml.namespace.QName;

public class Information {

    private String email;
    private String name;

    public Information(){

    }

    public Information(String email , String Name){
        this.email = email;
        this.name = Name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
