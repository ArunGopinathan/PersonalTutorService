package edu.uta.cse.personaltutorservice;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class RegisterActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
