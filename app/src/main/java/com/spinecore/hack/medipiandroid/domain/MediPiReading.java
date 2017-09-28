package com.spinecore.hack.medipiandroid.domain;

/**
 * Created by Sanjay on 28/09/2017.
 */

public class MediPiReading {
    String displayName;
    String reading;

    public MediPiReading(String displayName,String reading){
        this.displayName = displayName;
        this.reading = reading;
    }
    public String getDisplayName(){
        return displayName;
    }
    public String getReading(){
        return reading;
    }

}
