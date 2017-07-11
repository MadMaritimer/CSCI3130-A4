package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.Map;

public class DetailViewActivity extends Activity {

    private EditText nameField, addressField;
    private Spinner bTypeField, provField;
    private MyApplicationData appState;
    Contact receivedPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.address);
        bTypeField = (Spinner) findViewById(R.id.bType);
        provField = (Spinner) findViewById(R.id.prov);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            addressField.setText(receivedPersonInfo.address);
            switch (receivedPersonInfo.type) {
                case "Fisher":
                    bTypeField.setSelection(1, false);
                    break;
                case "Distributor":
                    bTypeField.setSelection(2, false);
                    break;
                case "Processor":
                    bTypeField.setSelection(3, false);
                    break;
                case "Fish Monger":
                    bTypeField.setSelection(4, false);
                    break;
                default:
                    bTypeField.setSelection(0, false);
            }
            switch (receivedPersonInfo.prov){
                case "AB":
                    provField.setSelection(1, false);
                    break;
                case "BC":
                    provField.setSelection(2, false);
                    break;
                case "MB":
                    provField.setSelection(3, false);
                    break;
                case "NB":
                    provField.setSelection(4, false);
                    break;
                case "NL":
                    provField.setSelection(5, false);
                    break;
                case "NS":
                    provField.setSelection(6, false);
                    break;
                case "NT":
                    provField.setSelection(7, false);
                    break;
                case "NU":
                    provField.setSelection(8, false);
                    break;
                case "ON":
                    provField.setSelection(9, false);
                    break;
                case "PE":
                    provField.setSelection(10, false);
                    break;
                case "QC":
                    provField.setSelection(11, false);
                    break;
                case "SK":
                    provField.setSelection(12, false);
                    break;
                case "YT":
                    provField.setSelection(13, false);
                    break;
                default:
                    provField.setSelection(0,false);
            }

        }
    }

    public void updateContact(View v){
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo.name = nameField.getText().toString();
        receivedPersonInfo.address = addressField.getText().toString();
        receivedPersonInfo.prov = provField.getSelectedItem().toString();
        receivedPersonInfo.type = bTypeField.getSelectedItem().toString();
        Map<String, Object> updates = receivedPersonInfo.toMap();
        appState.firebaseReference.child(receivedPersonInfo.bID).updateChildren(updates);
        finish();


    }

    public void eraseContact(View v)
    {
        appState = ((MyApplicationData) getApplicationContext());
        appState.firebaseReference.child(receivedPersonInfo.bID).removeValue();
        finish();
    }
}
