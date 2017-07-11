package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public  String bID;
    public  String name;
    public  String type;
    public  String address;
    public  String prov;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Contact(String bID, String name, String type, String address, String prov){
        this.bID = bID;
        this.name = name;
        this.type = type;
        this.address = address;
        this.prov = prov;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("bID", bID);
        result.put("name", name);
        result.put("type", type);
        result.put("address", address);
        result.put("prov", prov);

        return result;
    }
}
