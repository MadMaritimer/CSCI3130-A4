package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben Parker
 * Class that defines how the data will be stored in the
 * Firebase database. This is converted to a JSON format
 * <code>bID</code> unique 9 digit number that identifies the business, required
 * <code>name</code> the name of the business, 2-48 chars, inclusive, required
 * <code>type</code> the primary role performed by the business, required
 * <code>address</code> the physical address of the business, max 50 chars, optional
 * <code>prov</code> the abbreviation of the province of the business, optional
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

    @Override
    public String toString() {
        return bID+name+type+address+prov;
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
