package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * @author Ben Parker
 * Creates a new <code>Contact</code> instance and pushes it to Firebase when Submit button pressed
 */
public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, addressField;
    private Spinner bTypeField, provField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);
        bTypeField = (Spinner) findViewById(R.id.bType);
        provField = (Spinner) findViewById(R.id.prov);

        bTypeField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean userChange = false;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0 && userChange){
                    Toast.makeText(getApplicationContext(), "Please Select Business Type", Toast.LENGTH_LONG).show();
                }
                userChange = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // Toast.makeText(getApplicationContext(), "Please Select Business Type", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Controls the submitInfoButton
     * creates a new <code>Contact</code> with the information of the new business
     * then pushes it to Firebase
     * @param v the current view
     */
    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        Bundle bundle = getIntent().getExtras();
        int index = bundle.getInt("index");
        String bID = String.format("%09d", index);
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        String bType = bTypeField.getSelectedItem().toString();
        String prov = provField.getSelectedItem().toString();
        Contact business = new Contact(bID, name, bType, address, prov);
        appState.firebaseReference.child(bID).setValue(business);
        finish();

    }
}
