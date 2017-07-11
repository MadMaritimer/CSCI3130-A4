package com.acme.a3csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Ben Parker
 * Class that powers the MainActivity
 * Displays the list of Businesses currently in the Database
 * Contains a button that opens the AddBusiness activity
 * Tapping any ListItem opens the DetailViewActivity for that entry, allows update/delete
 */
public class MainActivity extends Activity {


    private ListView contactListView;
    private FirebaseListAdapter<Contact> firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the app wide shared variables
        MyApplicationData appData = (MyApplicationData)getApplication();

        //Set-up Firebase
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("contacts");

        //Get the reference to the UI contents
        contactListView = (ListView) findViewById(R.id.listView);

        //Set up the List View
       firebaseAdapter = new FirebaseListAdapter<Contact>(this, Contact.class,
                android.R.layout.simple_list_item_1, appData.firebaseReference) {
            @Override
            protected void populateView(View v, Contact model, int position) {
                TextView contactName = (TextView)v.findViewById(android.R.id.text1);
                contactName.setText(model.name);
            }
        };
        contactListView.setAdapter(firebaseAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // onItemClick method is called everytime a user clicks an item on the list
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact person = firebaseAdapter.getItem(position);
                showDetailView(person);
            }
        });
    }

    /**
     * Opens the CreateContactActivity
     * passes the current list size into the intent so unique bID can be made
     * @param v the current view
     */
    public void createContactButton(View v)
    {
        Intent intent=new Intent(this, CreateContactAcitivity.class);
        int entryCount = firebaseAdapter.getCount();
        intent.putExtra("index", entryCount);
        startActivity(intent);
    }

    /**
     * Opens the DetailViewActivity for the selected <code>Contact</code> entry
     * @param business the selected <code>Contact</code> object containing a business' information
     */
    private void showDetailView(Contact business)
    {
        Intent intent = new Intent(this, DetailViewActivity.class);
        intent.putExtra("Contact", business);
        startActivity(intent);
    }



}
