package edu.uta.cse.personaltutorservice;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends ActionBarActivity {
    public static String hostname = "http://personaltutor.uta.ngrok.io/PersonalTutorServiceWebService/PTSWebService/";
    public static String checkmethod = "CheckAvailability/";
    public static String registermethod = "Register";
    public static String address_hostname = "http://api.matchbox.io/address/";
    public static String address_addr_1 = "?addr_1=";
    public static String address_addr_2 = "?addr_2=";
    public static String address_city_state_zip = "&city_state_zip=";
    public String address_validate_request = "";
    public String registerResult = "";
    Spinner userTypeSpinner;
    EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    TextView spinnerErrorText;
    EditText addressLine1Text, addressLine2Text, cityEditText, zipEditText, phoneEditText;
    AutoCompleteTextView stateEditText;
    Button registerButton;
    String firstName, lastName, email, password, userType, addressLine1, addressLine2, city, state, zipcode, phone, lattitude, longitude;
    boolean isuserNameAvailable, isAddressValidated;
    String registerRequestJson;
    ProgressBar progressBar;

    public void emailOnFocusChange(boolean hasFocus){
        if (!hasFocus) {

            if (emailEditText.getText().length() > 0 && emailEditText.getText().toString() != "" && isValidEmail(emailEditText.getText().toString())) {
                email = emailEditText.getText().toString();
                CheckAvailabilityAsyncTask task = new CheckAvailabilityAsyncTask();
                task.execute();

            } else {
                Toast.makeText(RegisterActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String[] states = getResources().getStringArray(R.array.statearray);
        lattitude = "NA";
        longitude = "NA";
        isAddressValidated = false;
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerButton = (Button) findViewById(R.id.SignUpButton);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);
        firstNameEditText = (EditText) findViewById(R.id.txtFirstName);
        lastNameEditText = (EditText) findViewById(R.id.txtLastName);
        emailEditText = (EditText) findViewById(R.id.txtEmail);
       // spinnerErrorText = (TextView)findViewById(R.id.spinnerErrorText);
        progressBar = (ProgressBar)findViewById(R.id.loginProgressBar);
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                emailOnFocusChange(hasFocus);
            }
        });
        passwordEditText = (EditText) findViewById(R.id.txtPassword);
        confirmPasswordEditText = (EditText) findViewById(R.id.txtConfirmPassword);
        userTypeSpinner = (Spinner) findViewById(R.id.userTypeSpinner);

        addressLine1Text = (EditText) findViewById(R.id.txtAddressLine1);
        addressLine2Text = (EditText) findViewById(R.id.txtAddressLine2);
        cityEditText = (EditText) findViewById(R.id.txtCity);
        stateEditText = (AutoCompleteTextView) findViewById(R.id.txtState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, states);
        stateEditText.setAdapter(adapter);
        zipEditText = (EditText) findViewById(R.id.txtZipCode);

        phoneEditText = (EditText) findViewById(R.id.txtPhoneNumber);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstNameEditText.getText().length() == 0 || firstNameEditText.getText().toString().equals("")) {
                    firstNameEditText.setError("First Name Field is Required");
                } else {
                    firstNameEditText.setError(null);
                    firstName = firstNameEditText.getText().toString();

                }

                if (lastNameEditText.getText().length() == 0 || lastNameEditText.getText().toString().equals("")) {
                    lastNameEditText.setError("Last Name Field is Required");
                } else {
                    lastNameEditText.setError(null);
                    lastName = lastNameEditText.getText().toString();

                }
                if (emailEditText.getText().length() == 0 || emailEditText.getText().toString().equals("")) {
                    emailEditText.setError("Email Field is Required");
                } else if (!RegisterActivity.isValidEmail(emailEditText.getText().toString())) {
                    emailEditText.setError("Please enter a valid Email");
                } else {
                    emailEditText.setError(null);
                    email = emailEditText.getText().toString();
                    emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            emailOnFocusChange(hasFocus);
                        }
                    });
                }

                if (passwordEditText.getText().length() == 0 || passwordEditText.getText().toString().equals("")) {
                    passwordEditText.setError("Password Field is Required");
                } else {
                    passwordEditText.setError(null);
                }

                if (confirmPasswordEditText.getText().length() == 0 || confirmPasswordEditText.getText().toString().equals("")) {
                    confirmPasswordEditText.setError("Password Field is Required");
                } else if (!passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
                    confirmPasswordEditText.setError("Passwords do not Match");
                } else {
                    confirmPasswordEditText.setError(null);
                    password = LoginActivity.md5(passwordEditText.getText().toString());
                }
                View view = userTypeSpinner.getSelectedView();
                TextView textView = null;
                if(view!=null && view instanceof  TextView){
                      textView = (TextView) view;
                    if(userTypeSpinner.getSelectedItemPosition()==0){

                        textView.setError("Please Select UserType");
                    }
                    else{
                        textView.setError(null);
                        userType =  Integer.toString(userTypeSpinner.getSelectedItemPosition());
                    }
                }
                if (addressLine1Text.getText().length() == 0 || addressLine1Text.getText().toString().equals("")) {
                    addressLine1Text.setError("Address Line 1 is Required");
                }  else {
                    addressLine1Text.setError(null);
                    addressLine1 = addressLine1Text.getText().toString();
                }
                addressLine2 = "";
                if(addressLine2Text.getText().length()>0){
                    addressLine2 = addressLine2Text.getText().toString();
                }

                if(cityEditText.getText().length()==0 ||cityEditText.getText().toString().equals("") ){
                    cityEditText.setError("City Field is Required");
                }
                else{
                    cityEditText.setError(null);
                    city = cityEditText.getText().toString();
                }

                if(stateEditText.getText().length()==0 ||stateEditText.getText().toString().equals("") ){
                    stateEditText.setError("State Field is Required");
                }
                else{
                    stateEditText.setError(null);
                    state = stateEditText.getText().toString();
                }

                if(zipEditText.getText().length()==0 ||zipEditText.getText().toString().equals("") ){
                    zipEditText.setError("ZipCode Field is Required");
                }
                else{
                    zipEditText.setError(null);
                    zipcode = zipEditText.getText().toString();
                }
                if(phoneEditText.getText().length()==0 ||phoneEditText.getText().toString().equals("") ){
                    phoneEditText.setError("Phone Number Field is Required");
                }
                else{
                    phoneEditText.setError(null);
                    phone = phoneEditText.getText().toString();
                }

                if(firstNameEditText.getError()==null && lastNameEditText.getError()==null && emailEditText.getError()==null && passwordEditText.getError()==null && confirmPasswordEditText.getError()==null && !userType.equals("0") && addressLine1Text.getError()==null && cityEditText.getError()==null && stateEditText.getError()==null && phoneEditText.getError()==null){
                    //validate the address
                    ValidateAddressAsyncTask validateTask = new ValidateAddressAsyncTask();
                    validateTask.execute();
                    //register the address
                    RegisterAsyncTask registerTask = new RegisterAsyncTask();
                    registerTask.execute();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Some Field is buggy", Toast.LENGTH_SHORT).show();
                }




            }


        });


    }

    public boolean validCellPhone(String number) {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean checkAvailability(String username) {
        boolean isavailable = false;
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + checkmethod + username;
            HttpGet getRequest = new HttpGet(url);
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            Log.w("PTS-Android", httpResponse.getStatusLine().toString());
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                if (result.equals("YES")) {
                    isavailable = false;
                } else
                    isavailable = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return isavailable;
    }

    public String validateAddressWithGoogleApi() {
        String result = "";
        final Address validateAddr = new Address();
        validateAddr.setAddressLine1(addressLine1);
        validateAddr.setAddressLine2(addressLine2);
        validateAddr.setCity(city);
        validateAddr.setState(state);
        validateAddr.setZipCode(zipcode);

        List<Address> la = new ArrayList<Address>();

        try {
            String path = "http://maps.google.com/maps/api/geocode/json?address=";
            path += validateAddr.getAddressLine1() + ',' + validateAddr.getAddressLine2() + ',' + validateAddr.getCity() + ',' + validateAddr.getState() + ',' + validateAddr.getZipCode();
            path = path.replaceAll(" ", "");
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                int count = 0;
                List<String> va = new ArrayList<String>();
                JSONObject addr = new JSONObject(sb.toString());
                Log.w("PTS-Android", "re:" + addr);

                JSONArray resultsArray = (JSONArray) addr.get("results");
                Log.w("PTS-Android", "re:" + addr.getString("status"));
                if (addr.getString("status").equals("OK")) {
                    Log.w("PTS-Android", "num:" + resultsArray.length());
                    lattitude = resultsArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    longitude = resultsArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    isAddressValidated = true;
                }
            }
        } catch (Exception e) {
            isAddressValidated = false;
            e.printStackTrace();
        }


        return result;
    }

    @Deprecated
    public String validateAddress() {
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet getRequest = new HttpGet(address_validate_request);
            getRequest.setHeader("Cookie", "connect.sid=s%3Apxju73iJEdw8pbqqc9IYk8_HWt3owKfS.fmbhZBZR2ktoIKlAW5GHJPUYH2dMd3tb3KjzOPcRkqA");
            getRequest.setHeader("apikey", "62b245edf9b70fb5a2f88c824ddb6013");
            HttpResponse httpResponse = httpclient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
                JSONObject resultJson = new JSONObject(result);
                try {
                    lattitude = resultJson.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getString("lat");
                    longitude = resultJson.getJSONObject("location").getString("longitude");
                    if (lattitude.equals("0") || longitude.equals("0"))
                        isAddressValidated = false;
                    else
                        isAddressValidated = true;
                } catch (Exception ex) {
                    isAddressValidated = false;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }


        return result;
    }

    private class ValidateAddressAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            //  address_validate_request = address_hostname +address_addr_1 +Uri.encode(addressLine1 + " "+addressLine2 )+ address_city_state_zip+ Uri.encode(city+" "+state+" "+zipcode);
            // Log.w("PTS-Android",address_validate_request);

        }

        @Override
        protected Void doInBackground(Void... params) {
          //  validateAddress();
            validateAddressWithGoogleApi();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(isAddressValidated==false){
                addressLine1Text.setError("Invalid Address");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private class CheckAvailabilityAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
            emailEditText.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            isuserNameAvailable = checkAvailability(email);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            if (isuserNameAvailable) {
                emailEditText.setError(null);
                emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.tick, 0);
            } else {

                emailEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.cross, 0);
                emailEditText.setError("User Name not available");
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.animate();
        }
    }

    private void generateRegisterJson() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        user.setEmail(email);
        user.setPassword(password);

        Address address = new Address();
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipcode);
        address.setLattitude(lattitude);
        address.setLongitude(longitude);

        user.setAddress(address);
        user.setPhoneNumber(phone);

        registerRequestJson = User.toJsonString(user);

    }

    public String registerUser() {
        String result = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            String url = hostname + registermethod;
            HttpPost postRequest = new HttpPost(url);
            postRequest.addHeader("Content-Type", "application/json");
            StringEntity postentity = new StringEntity(registerRequestJson, "UTF-8");
            postentity.setContentType("application/json");
            postRequest.setEntity(postentity);

            HttpResponse httpResponse = httpclient.execute(postRequest);

            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                Log.w("PTS-Android", "Entity : " + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return result;
    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //  super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            generateRegisterJson();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            if (registerResult.equals("YES")) {
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed, Please Try again Later", Toast.LENGTH_SHORT).show();
            }
            onBackPressed();
        }

        @Override
        protected Void doInBackground(Void... params) {
            registerResult = registerUser();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.animate();
        }
    }

}
