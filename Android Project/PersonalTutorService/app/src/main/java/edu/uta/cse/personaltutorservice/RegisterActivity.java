package edu.uta.cse.personaltutorservice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends ActionBarActivity {

    Spinner userTypeSpinner;
    EditText firstNameEditText,lastNameEditText,emailEditText,passwordEditText, confirmPasswordEditText ;
    EditText addressLine1Text, addressLine2Text, cityEditText, stateEditText, zipEditText, phoneEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);
        firstNameEditText = (EditText)findViewById(R.id.txtFirstName);
        lastNameEditText = (EditText)findViewById(R.id.txtLastName);
        emailEditText = (EditText)findViewById(R.id.txtEmail);
        passwordEditText = (EditText)findViewById(R.id.txtPassword);
        confirmPasswordEditText = (EditText)findViewById(R.id.txtConfirmPassword);
        addressLine1Text = (EditText)findViewById(R.id.txtAddressLine1);
        addressLine2Text = (EditText)findViewById(R.id.txtAddressLine2);
        cityEditText = (EditText)findViewById(R.id.txtCity);
        stateEditText = (EditText)findViewById(R.id.txtState);
        zipEditText = (EditText)findViewById(R.id.txtZipCode);
        phoneEditText = (EditText)findViewById(R.id.txtZipCode);


    }

}
