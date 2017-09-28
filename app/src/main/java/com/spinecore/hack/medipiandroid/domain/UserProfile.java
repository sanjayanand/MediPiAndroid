package com.spinecore.hack.medipiandroid.domain;

/**
 * Created by Sanjay on 27/09/2017.
 */

public class UserProfile {
    String id;
    String userName;
    String nhsNumber;
    String dobAsString;

    public UserProfile(String id,String userName,String nhsNumber,String dobAsString){
        this.id = id;
        this.userName = userName;
        this.nhsNumber= nhsNumber;
        this.dobAsString= dobAsString;
    }
    public String toString(){
        return String.format("%s (%s) - DOB : %s", this.userName, this.nhsNumber
                            ,this.dobAsString);

    }

}